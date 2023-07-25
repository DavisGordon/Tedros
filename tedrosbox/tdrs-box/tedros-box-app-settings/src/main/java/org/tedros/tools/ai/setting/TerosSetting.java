/**
 * 
 */
package org.tedros.tools.ai.setting;

import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.tedros.ai.TFunctionHelper;
import org.tedros.ai.TerosService;
import org.tedros.ai.function.TFunction;
import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.message.TMessageType;
import org.tedros.core.repository.TRepository;
import org.tedros.fx.control.TButton;
import org.tedros.fx.form.TSetting;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.ai.model.TerosMV;
import org.tedros.tools.module.ai.settings.AiChatUtil;

import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
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
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void run() {
		if(TedrosContext.getArtificialIntelligenceEnabled()) {
			String key = util.getOpenAiKey();
			if(!"".equals(key)) {
				teros = TerosService.create(key);
				TFunction[] arr = new TFunction[] {TFunctionHelper.listAllViewsFunction(),
						TFunctionHelper.callViewFunction(),
						TFunctionHelper.getModelBeingEditedFunction(),
						TFunctionHelper.getViewModelFunction(),
						TFunctionHelper.getPreferencesFunction()};
				arr = ArrayUtils.addAll(arr, TFunctionHelper.getAppsFunction());
				
				teros.createFunctionExecutor(arr);
				
			}else {
				super.getForm().gettPresenter().getView()
				.tShowModal(new TMessageBox(TLanguage.getInstance()
						.getString(ToolsKey.MESSAGE_AI_KEY_REQUIRED), TMessageType.WARNING), false);
				return;	
			}
		}else {
			super.getForm().gettPresenter().getView()
			.tShowModal(new TMessageBox(TLanguage.getInstance()
					.getString(ToolsKey.MESSAGE_AI_DISABLED), TMessageType.WARNING), false);
			return;	
		}
		
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
			TerosMV mv = getModelView(); 

			String prompt = mv.getPrompt().getValue();
			if(StringUtils.isBlank(prompt))
				return;
			
			try {
				showMsg(TedrosContext.getLoggedUser().getName(), prompt);
				
				TerosProcess p = new TerosProcess();
				p.stateProperty().addListener((a,o,n)->{
					if(n.equals(State.SUCCEEDED)) {
						showMsg("Teros", p.getValue());
						mv.getPrompt().setValue(null);
					}
				});
				super.getForm().gettPresenter().getView()
				.gettProgressIndicator().bind(p.runningProperty());
				p.setPrompt(prompt);
				p.startProcess();
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		};
		repo.add("ev0", ev0);
		TButton sendBtn = (TButton) getDescriptor()
				.getFieldDescriptor("sendBtn").getComponent();
		sendBtn.setOnAction(new WeakEventHandler<>(ev0));
	}

	/**
	 * @param m
	 */
	private void showMsg(String user, String txt){
		scrollFlag = true;
		StackPane p1 = util.buildMsgPane(user, txt, new Date(), 460, false);
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
	}

}
