/**
 * 
 */
package org.tedros.tools.module.ai.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.core.ai.model.TAiCreateImage;
import org.tedros.core.repository.TRepository;
import org.tedros.fx.control.TButton;
import org.tedros.fx.form.TSetting;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.tools.module.ai.process.TAiCreateImageProcess;

import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * @author Davis Gordon
 *
 */
public class CreateImageSetting extends TSetting {


    private TRepository repo;
    private boolean scrollFlag = false;
	
	/**
	 * @param descriptor
	 */
	public CreateImageSetting(ITComponentDescriptor descriptor) {
		super(descriptor);
		this.repo = new TRepository();
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.form.TSetting#run()
	 */
	@Override
	public void run() {
		this.listenSendButton();
		this.listenClearButton();
		TabPane tp = super.getLayout("title");
		// Tab title
		Tab t = tp.getTabs().get(0);
		//t.textProperty().bind(mv.getTitle());
		
		// auto scroll
		ScrollPane sp = (ScrollPane) t.getContent();
		sp.setVvalue(1.0);
		sp.vvalueProperty().addListener((a,o,n)->{
			if(scrollFlag) {
				sp.setVvalue(sp.getVmax());
				if(n.doubleValue()==sp.getVmax())
					scrollFlag = false;
			}
		});
		
	}
	

	/**
	 * @param mv
	 */
	private void listenClearButton() {
		// Clear event
		EventHandler<ActionEvent> ev1 = e->{
			CreateImageMV mv = getModelView();
			mv.getPrompt().setValue(null);
		};
		repo.add("ev1", ev1);
		TButton clearBtn = (TButton) getDescriptor()
				.getFieldDescriptor("clearBtn").getComponent();
		clearBtn.setOnAction(new WeakEventHandler<>(ev1));
	}

	/**
	 * @param mv
	 * @param msgs
	 */
	private void listenSendButton() {
		// Send event
		EventHandler<ActionEvent> ev0 = e -> {
			TButton btn = (TButton) e.getSource();
			btn.setDisable(true);

			CreateImageMV mv = getModelView();
			TAiCreateImage m = mv.getEntity();
			
			TAiCreateImageProcess prc = new TAiCreateImageProcess();
			prc.stateProperty().addListener((a,o,n)->{
				if(n.equals(State.SUCCEEDED)) {
					List<TResult<TAiCreateImage>> l = prc.getValue();
					VBox p = super.getLayout("id");
					TResult<TAiCreateImage> res = l.get(0);
					if(res.getState().equals(TState.SUCCESS)) {
						TAiCreateImage c = res.getValue();
						mv.reload(c);
						c.getData().forEach(i->{
							InputStream bis = new ByteArrayInputStream(i.getImage().getByte().getBytes());
							Image im = new Image(bis);
							ImageView iv = new ImageView();
							iv.setImage(im);
							p.getChildren().add(iv);
						});
					}
					btn.setDisable(false);
				}
			});
			prc.createImage(m);
			prc.startProcess();
			
		};
		repo.add("ev0", ev0);
		TButton sendBtn = (TButton) getDescriptor()
				.getFieldDescriptor("sendBtn").getComponent();
		sendBtn.setOnAction(new WeakEventHandler<>(ev0));
	}


	/* (non-Javadoc)
	 * @see org.tedros.api.form.ITSetting#dispose()
	 */
	@Override
	public void dispose() {
		this.repo.clear();
	}
}
