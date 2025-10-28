/**
 * 
 */
package org.tedros.tools.module.ai.settings;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.api.presenter.view.ITView;
import org.tedros.common.model.TFileEntity;
import org.tedros.core.TLanguage;
import org.tedros.core.ai.model.TAiCreateImage;
import org.tedros.core.control.PopOver;
import org.tedros.core.control.PopOver.ArrowLocation;
import org.tedros.core.repository.TRepository;
import org.tedros.fx.control.TButton;
import org.tedros.fx.control.TLabel;
import org.tedros.fx.form.TSetting;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.ai.model.CreateImageMV;
import org.tedros.tools.module.ai.model.EventMV;
import org.tedros.tools.module.ai.process.TAiCreateImageProcess;
import org.tedros.util.TFileUtil;
import org.tedros.util.TedrosFolder;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.WeakListChangeListener;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * @author Davis Gordon
 *
 */
public class CreateImageSetting extends TSetting {


    private TRepository repo;
	
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
		this.listenImages();

		CreateImageMV mv = getModelView();
		TAiCreateImage c = mv.getEntity();
		if(c.getData()!=null)
			c.getData().forEach(i->{
				mv.getImages().add(i.getImage());
			});
	}
	
	private void listenImages() {
		
		ListChangeListener<TFileEntity> chl = ch -> {
			VBox p = super.getLayout("images");
			ch.next();
			ch.getAddedSubList().forEach(i->{
				Image im;
				ImageView iv;
				try {
					InputStream bis = new ByteArrayInputStream(i.getByte().getBytes());
					im = new Image(bis);
					bis.close();
					iv = new ImageView();
					iv.setCursor(Cursor.HAND);
					iv.setOnMouseClicked(ev->{
						if(ev.getClickCount()==2) {
							try {
								ByteArrayInputStream bis2 = new ByteArrayInputStream(i.getByte().getBytes());
								BufferedImage image = ImageIO.read(bis2);
								bis2.close();
								// write the image to a file
								File outputfile = new File(TedrosFolder.EXPORT_FOLDER.getFullPath()+i.getFileName());
								ImageIO.write(image, "png", outputfile);
								TFileUtil.open(outputfile);
							} catch (IOException e) {
								e.printStackTrace();
							}
					
						}
					});
					iv.setImage(im);
					p.getChildren().add(0,iv);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			});
		};
		repo.add("imgChl", chl);
		CreateImageMV mv = getModelView();
		ObservableList<TFileEntity> images = mv.getImages();
		images.addListener(new WeakListChangeListener<>(chl));
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
	@SuppressWarnings("rawtypes")
	private void listenSendButton() {
		final ITView view = super.getDescriptor().getForm().gettPresenter().getView();
		// Send event
		EventHandler<ActionEvent> ev0 = e -> {
			TButton btn = (TButton) e.getSource();
			btn.setDisable(true);

			CreateImageMV mv = getModelView();
			String prompt = mv.getPrompt().getValue();
			if(StringUtils.isBlank(prompt)) {
				Node input = super.getControl("prompt");
				PopOver pov = new PopOver();
				pov.setAutoHide(true);
				pov.setArrowLocation(ArrowLocation.TOP_CENTER);
				pov.setContentNode(new TLabel(TLanguage.getInstance()
						.getString(ToolsKey.MESSAGE_AI_PROMPT_REQUIRED)));
				pov.show(input);
				btn.setDisable(false);
				return;
			}
			
			if(StringUtils.isBlank(mv.getTitle().getValue())) {
				mv.getTitle().setValue(prompt.length()>40 
						? prompt.substring(0, 35)+"..." 
								: prompt);
			}
			
			TAiCreateImage m = mv.getEntity();
			
			TAiCreateImageProcess prc = new TAiCreateImageProcess();
			prc.stateProperty().addListener((a,o,n)->{
				if(n.equals(State.SUCCEEDED)) {
					List<TResult<TAiCreateImage>> l = prc.getValue();
					TResult<TAiCreateImage> res = l.get(0);
					if(res.getState().equals(TState.SUCCESS)) {
						TAiCreateImage c = res.getValue();
						c.getData().forEach(i->{
							mv.getImages().add(i.getImage());
							mv.getEntity().addImage(i.getImage());
						});
						c.getEvents().forEach(ev->{
							mv.getEvents().add(new EventMV(ev));
						});
					}
					btn.setDisable(false);
				}
			});
			view.gettProgressIndicator().bind(prc.runningProperty());
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
