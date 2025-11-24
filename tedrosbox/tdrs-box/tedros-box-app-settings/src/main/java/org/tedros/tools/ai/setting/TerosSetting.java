/**
 * 
 */
package org.tedros.tools.ai.setting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.tedros.ai.TFunctionHelper;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.service.AiServiceProvider;
import org.tedros.ai.service.AiTerosServiceFactory;
import org.tedros.ai.service.IAiTerosService;
import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.control.TMessageProgressIndicator;
import org.tedros.core.message.TMessageType;
import org.tedros.core.repository.TRepository;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.control.TButton;
import org.tedros.fx.form.TSetting;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.ai.model.TerosMV;
import org.tedros.tools.module.ai.settings.AiChatUtil;

import javafx.collections.ListChangeListener;
import javafx.collections.WeakListChangeListener;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


/**
 * @author Davis Gordon
 *
 */
public class TerosSetting extends TSetting {

	private static final String TEROS_NAME = "Teros";	
	protected static IAiTerosService TEROS;
	
	private AiChatUtil util;
    private TRepository repo;
    private boolean scrollFlag = false;
    
    private TMessageProgressIndicator progressIndicator;
    
	/**
	 * @param descriptor
	 */
	public TerosSetting(ITComponentDescriptor descriptor) {
		super(descriptor);
		util = new AiChatUtil();
		repo = new TRepository();
		
		String apiKey = TedrosContext.getAiApiKey();
		String aiModel = TedrosContext.getAiModel();
		AiServiceProvider aiProvider = TedrosContext.getAiServiceProvider();
		String aiSystemPrompt = TedrosContext.getAiSystemPrompt();
		
		if(StringUtils.isNotBlank(apiKey) && StringUtils.isNotBlank(aiModel) 
				&& StringUtils.isNotBlank(aiSystemPrompt) && aiProvider!=null) 
		{
			TEROS = AiTerosServiceFactory.create(apiKey, aiModel, aiSystemPrompt, aiProvider);
		}
		
		TedrosContext.aiServiceProviderProperty().addListener((a,o,n)->{
				String key = TedrosContext.getAiApiKey();
				String model = TedrosContext.getAiModel();	
				String systemPrompt = TedrosContext.getAiSystemPrompt();
				if(StringUtils.isNotBlank(key) && StringUtils.isNotBlank(model) && n!=null) {
					TEROS = AiTerosServiceFactory.create(key, model, systemPrompt, n);
					resetAction();
				}
				
			});
		
		TedrosContext.aiApiKeyProperty().addListener((a,o,n)->{
				AiServiceProvider provider = TedrosContext.getAiServiceProvider();
				String model = TedrosContext.getAiModel();		
				String systemPrompt = TedrosContext.getAiSystemPrompt();		
				if(provider!=null && StringUtils.isNotBlank(model) && StringUtils.isNotBlank(n)) {
					TEROS = AiTerosServiceFactory.create(n, model, systemPrompt, provider);
					resetAction();
				}
			});
		
		TedrosContext.aiModelProperty().addListener((a,o,n)->{
			if(TEROS!=null)
				TEROS.setAiModel(n);
			});
		
		TedrosContext.aiSystemPromptProperty().addListener((a,o,n)->{
			String key = TedrosContext.getAiApiKey();
			String model = TedrosContext.getAiModel();	
			AiServiceProvider provider = TedrosContext.getAiServiceProvider();
			if(StringUtils.isNotBlank(key) && StringUtils.isNotBlank(model) && StringUtils.isNotBlank(n) && provider!=null) {
				TEROS = AiTerosServiceFactory.create(key, model, n, provider);
				resetAction();
			}
			});
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void run() {
		if(TedrosContext.getArtificialIntelligenceEnabled()) {
			
			this.progressIndicator = new TMessageProgressIndicator();
			
			super.getForm().gettPresenter().getView().settProgressIndicator(progressIndicator);
			
			if(TEROS!=null) {
				
				TFunction[] arr = new TFunction[] {
						TFunctionHelper.listAllViewPathFunction(),
						TFunctionHelper.listAllAppsFunction(),
						TFunctionHelper.getViewInfoFunction(),
						TFunctionHelper.callUpViewFunction(),
						TFunctionHelper.getModelBeingEditedFunction(),
						TFunctionHelper.getViewModelFunction(),
						TFunctionHelper.getPreferencesFunction(),
						TFunctionHelper.getCreateFileFunction()};
				
				arr = ArrayUtils.addAll(arr, TFunctionHelper.getAppsFunction());
				
				TEROS.createFunctionExecutor(arr);
				
				ListChangeListener<String> reasoningMsgListener =  c->{
					while(c.next()) {
						if(c.wasAdded()) {
							for(String msg : c.getAddedSubList()) {
								//show reasoning messages
								this.progressIndicator.addMessage(util.buildMsgPane(TEROS_NAME, msg, new Date(), 420, true));
							}
						}
					}
				};
				repo.add("reasoningMsgListener", reasoningMsgListener);
				TEROS.reasoningsMessageProperty().addListener(new WeakListChangeListener<>(reasoningMsgListener));
				
			}else {
				
				List<String> messages = new ArrayList<>();
				
				if(StringUtils.isBlank(TedrosContext.getAiApiKey()))
					messages.add(TLanguage.getInstance().getString(ToolsKey.MESSAGE_AI_KEY_REQUIRED));
				
				if(StringUtils.isBlank(TedrosContext.getAiModel()))
					messages.add(TLanguage.getInstance().getString(ToolsKey.MESSAGE_AI_MODEL_REQUIRED));
				
				if(StringUtils.isBlank(TedrosContext.getAiSystemPrompt()))
					messages.add(TLanguage.getInstance().getString(ToolsKey.MESSAGE_AI_PROMPT_REQUIRED));
				
				super.getForm().gettPresenter().getView()
				.tShowModal(new TMessageBox(messages, TMessageType.WARNING), false);
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
		listenResetButton();
		
		TextArea text = super.getControl("prompt");
		text.setPromptText("[Enter] + [Shift] = "+TLanguage.getInstance().getString(TUsualKey.SEND));
		text.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER && event.isShiftDown()) {
                sendAction();
                event.consume();
            }
        });
		
		// auto scroll
		ScrollPane sp = (ScrollPane) super.getLayout("title"); 
		
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

	private void listenResetButton() {
		// Send event
		EventHandler<ActionEvent> ev0 = e -> {
			resetAction();
		};
		repo.add("resetEvent", ev0);
		TButton resetBtn = (TButton) super.getDescriptor()
				.getFieldDescriptor("resetBtn").getComponent();
		resetBtn.setOnAction(new WeakEventHandler<>(ev0));
	}
	
	private void listenSendButton() {
		// Send event
		EventHandler<ActionEvent> ev0 = e -> {
			sendAction();
		};
		repo.add("ev0", ev0);
		TButton sendBtn = (TButton) getDescriptor()
				.getFieldDescriptor("sendBtn").getComponent();
		sendBtn.setOnAction(new WeakEventHandler<>(ev0));
	}

	private void sendAction() {
		TerosMV mv = getModelView(); 

		String prompt = mv.getPrompt().getValue();
		if(StringUtils.isBlank(prompt))
			return;
		
		try {
			showMsg(TedrosContext.getLoggedUser().getName(), prompt);
			mv.getPrompt().setValue(null);
			TerosProcess p = new TerosProcess();
			p.stateProperty().addListener((a,o,n)->{
				if(n.equals(State.SUCCEEDED))
					showMsg(TEROS_NAME, p.getValue());
			});
			super.getForm().gettPresenter().getView()
			.gettProgressIndicator().bind(p.runningProperty());
			p.setPrompt(prompt);
			p.startProcess();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * @param m
	 */
	private void resetAction(){
		scrollFlag = true;
		VBox gp = super.getLayout("messages");
		gp.getChildren().clear();
		//TEROS.clearMessages();
	}

	/**
	 * @param m
	 */
	private void showMsg(String user, String txt){
		scrollFlag = true;
		StackPane p1 = util.buildMsgPane(user, txt, new Date(), 520, false);
		GridPane.setVgrow(p1, Priority.ALWAYS);
		VBox gp = super.getLayout("messages");
		gp.getChildren().add(p1);
	}
	
	@Override
	public void dispose() {
		repo.clear();
		repo = null;
		TEROS = null;
		TerosMV mv = getModelView();
		mv.getMessages().clear();
	}

}
