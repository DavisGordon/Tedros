/**
 * 
 */
package org.tedros.tools.ai.setting;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.tedros.ai.TFunctionHelper;
import org.tedros.ai.TerosService;
import org.tedros.ai.model.Message;
import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.api.presenter.view.ITView;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.control.PopOver;
import org.tedros.core.control.PopOver.ArrowLocation;
import org.tedros.core.repository.TRepository;
import org.tedros.fx.control.TButton;
import org.tedros.fx.control.TLabel;
import org.tedros.fx.form.TSetting;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.ai.model.MsgMV;
import org.tedros.tools.ai.model.TerosMV;
import org.tedros.tools.module.ai.model.AiChatMV;
import org.tedros.tools.module.ai.settings.AiChatUtil;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.WeakListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


/**
 * @author Davis Gordon
 *
 */
public class TerosSetting extends TSetting {

	private AiChatUtil util;
    private TRepository repo;
    private boolean scrollFlag = false;
    private TerosService teros;

	/**
	 * @param descriptor
	 */
	public TerosSetting(ITComponentDescriptor descriptor) {
		super(descriptor);
		util = new AiChatUtil();
		repo = new TRepository();
		teros = new TerosService(TerosService.token);
		teros.createFunctionExecutor(
			TFunctionHelper.createListViewFunction(),
			TFunctionHelper.createCallViewFunction());
	}

	@Override
	public void run() {
		
		listenReceivedMsg();
		listenSendButton();
		listenClearButton();
		
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
			TerosMV mv = getModelView();
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
			Node control = super.getControl("prompt");
			TButton btn = (TButton) e.getSource();
			//btn.setDisable(true);
		    
			TerosMV mv = getModelView(); 

			String prompt = mv.getPrompt().getValue();
			if(StringUtils.isBlank(prompt)) {
				PopOver pov = new PopOver();
				pov.setAutoHide(true);
				pov.setArrowLocation(ArrowLocation.BOTTOM_CENTER);
				pov.setContentNode(new TLabel(TLanguage.getInstance()
						.getString(ToolsKey.MESSAGE_AI_PROMPT_REQUIRED)));
				pov.show(control);
				//btn.setDisable(false);
				return;
			}
			
			final ObservableList<MsgMV> msgs = mv.getMessages();
			String msg = mv.getPrompt().getValue();
			try {
				msgs.add(new MsgMV(new Message(msg, TedrosContext.getLoggedUser().getName())));
				Platform.runLater(()->{	
					String resp = teros.call(msg);
					msgs.add(new MsgMV(new Message(resp, "Teros")));
				});
			} catch (Exception ex) {
				ex.printStackTrace();
				//btn.setDisable(false);
			}
		};
		repo.add("ev0", ev0);
		TButton sendBtn = (TButton) getDescriptor()
				.getFieldDescriptor("sendBtn").getComponent();
		sendBtn.setOnAction(new WeakEventHandler<>(ev0));
	}

	/**
	 * @param mv
	 * @return
	 */
	private void listenReceivedMsg() {
		TerosMV mv = getModelView();
		// Listen new messages to show
		final ObservableList<MsgMV> msgs = mv.getMessages();
		ListChangeListener<MsgMV> chl0 = ch0 ->{
			if(ch0.next() && ch0.wasAdded()) {
				ch0.getAddedSubList().forEach(m->{
					Message e = m.getModel();
					showMsg(e.getUser(), e.getContent());
				});
			}
		};
		repo.add("chl0", chl0);
		msgs.addListener(new WeakListChangeListener<MsgMV>(chl0));
	}

	/**
	 * @param m
	 */
	private void showMsg(String user, String txt){
		scrollFlag = true;
		StackPane p1 = util.buildMsgPane(user, txt, new Date());
		GridPane.setVgrow(p1, Priority.ALWAYS);
		VBox gp = super.getLayout("messages");
		gp.getChildren().add(p1);
	}
	
	@Override
	public void dispose() {
		repo.clear();
		repo = null;
		teros = null;
		TerosMV mv = getModelView();
		mv.getMessages().clear();
		//mv.getMessagesLoaded().setValue(false);
	}

}
