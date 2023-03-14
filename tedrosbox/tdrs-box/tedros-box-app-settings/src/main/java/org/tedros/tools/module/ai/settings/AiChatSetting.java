/**
 * 
 */
package org.tedros.tools.module.ai.settings;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.core.TLanguage;
import org.tedros.core.ai.model.TAiChatCompletion;
import org.tedros.core.ai.model.TAiChatMessage;
import org.tedros.core.ai.model.TOpenAiError;
import org.tedros.core.ai.model.TRequestEvent;
import org.tedros.core.ai.model.TRequestType;
import org.tedros.core.ai.model.completion.chat.TChatResult;
import org.tedros.core.ai.model.completion.chat.TChatRole;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.repository.TRepository;
import org.tedros.fx.control.TButton;
import org.tedros.fx.form.TSetting;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.ai.model.AiChatMV;
import org.tedros.tools.module.ai.model.AiChatMessageMV;
import org.tedros.tools.module.ai.model.EventMV;

import javafx.animation.FadeTransition;
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
import javafx.util.Duration;


/**
 * @author Davis Gordon
 *
 */
public class AiChatSetting extends TSetting {

	private AiChatUtil util;
    private TRepository repo;
    private boolean scrollFlag = false;

	/**
	 * @param descriptor
	 */
	public AiChatSetting(ITComponentDescriptor descriptor) {
		super(descriptor);
		util = new AiChatUtil();
		repo = new TRepository();
	}

	@Override
	public void run() {
		
		listenReceivedMsg();
		listenSendButton();
		listenClearButton();
		
		AiChatMV mv = super.getModelView();
		final ObservableList<AiChatMessageMV> msgs = mv.getMessages();
		
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
		
		try {
			List<TAiChatMessage> lst0 = util.findMessages(TedrosContext.getLoggedUser()
					.getAccessToken(),
					mv.getEntity().getId());
			Collections.sort(lst0);
			lst0.forEach(msg->{
				msgs.add(new AiChatMessageMV(msg));
			});
			
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * @param mv
	 */
	private void listenClearButton() {
		// Clear event
		EventHandler<ActionEvent> ev1 = e->{
			AiChatMV mv = getModelView();
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
			btn.setDisable(true);
			FadeTransition ft = new FadeTransition(Duration.millis(2000), control );
		    ft.setFromValue(1.0);
		    ft.setToValue(0.3);
		    ft.setCycleCount(FadeTransition.INDEFINITE);
		    ft.setAutoReverse(true);
		    ft.play();
		    
			AiChatMV mv = getModelView(); 
			
			if(StringUtils.isBlank(mv.getTitle().getValue())) {
				String n = mv.getPrompt().getValue();
				mv.getTitle().setValue(n.length()>40 ? n.substring(0, 36)+"..." : n);
			}
			
			final ObservableList<AiChatMessageMV> msgs = mv.getMessages();
			
			if(msgs.isEmpty()) {
				TAiChatMessage m = new TAiChatMessage();
				m.setContent(TLanguage.getInstance()
						.getFormatedString(ToolsKey.AI_WELCOME, TedrosContext.getLoggedUser().getName()));
				m.setInsertDate(new Date());
				m.setRole(TChatRole.SYSTEM);
				m.setChat(mv.getModel());
				
				msgs.add(new AiChatMessageMV(m));
			}
			
			String msg = mv.getPrompt().getValue();
			 try {
		            TAiChatMessage m = new TAiChatMessage();
					m.setContent(msg);
					m.setInsertDate(new Date());
					m.setRole(TChatRole.USER);
					m.setChat(mv.getModel());
					
					msgs.add(new AiChatMessageMV(m));
					mv.getPrompt().setValue(null);

					Platform.runLater(()->{
						try {
							TChatResult res = util.chat(TedrosContext.getLoggedUser()
									.getAccessToken(), msgs);
							if(res.getChoices()!=null)
								res.getChoices().forEach(c->{
									TAiChatMessage m1 = new TAiChatMessage();
									m1.setContent(c.getMessage().getContent());
									m1.setInsertDate(new Date());
									m1.setRole(TChatRole.ASSISTANT);
									m1.setChat(mv.getModel());
									msgs.add(new AiChatMessageMV(m1));
								});
							else {
								TOpenAiError error = res.getErrorType();
								
								TAiChatMessage m1 = new TAiChatMessage();
								m1.setInsertDate(new Date());
								m1.setRole(TChatRole.ASSISTANT);
								m1.setChat(mv.getModel());
								if(error!=null) {
									m1.setContent(TLanguage.getInstance().getString(error.getMsgKey()));
								}else 
								if(StringUtils.containsIgnoreCase(res.getLog(), "timeout")) { 
									m1.setContent(TLanguage.getInstance().getString(ToolsKey.MESSAGE_AI_TIMEOUT));
								}else
									m1.setContent(TLanguage.getInstance().getString(ToolsKey.ERROR_AI_LOG));
							
								msgs.add(new AiChatMessageMV(m1));
							}
							
							TAiChatCompletion cc = mv.getEntity();
							TRequestEvent ev = TRequestEvent.build(TRequestType.CHAT, res.getLog(), 
									res.getUsage(), cc.getModel().value(), 
									cc.getTemperature(), cc.getMaxTokens(), null);
							mv.getEvents().add(new EventMV(ev));
						} catch (Exception e1) {
							e1.printStackTrace();
							TAiChatMessage m1 = new TAiChatMessage();
							m1.setInsertDate(new Date());
							m1.setRole(TChatRole.ASSISTANT);
							m1.setChat(mv.getModel());
							msgs.add(new AiChatMessageMV(m1));
							m1.setContent(TLanguage.getInstance().getString(ToolsKey.ERROR_AI_LOG));
						
							TAiChatCompletion cc = mv.getEntity();
							TRequestEvent ev = TRequestEvent.build(TRequestType.CHAT, e1.getMessage(), 
									null, cc.getModel().value(), 
									cc.getTemperature(), cc.getMaxTokens(), null);
							mv.getEvents().add(new EventMV(ev));
						}finally {
							ft.stop();
							control.setOpacity(100);
							btn.setDisable(false);
						}
					});
		        } catch (Exception ex) {
		            ex.printStackTrace();

					TAiChatMessage m1 = new TAiChatMessage();
					m1.setInsertDate(new Date());
					m1.setRole(TChatRole.ASSISTANT);
					m1.setChat(mv.getModel());
					m1.setContent(TLanguage.getInstance().getString(ToolsKey.ERROR_AI_LOG));
					msgs.add(new AiChatMessageMV(m1));
				
					TAiChatCompletion cc = mv.getEntity();
					TRequestEvent ev = TRequestEvent.build(TRequestType.CHAT, ex.getMessage(), 
							null, cc.getModel().value(), 
							cc.getTemperature(), cc.getMaxTokens(), null);
					mv.getEvents().add(new EventMV(ev));
		            ft.stop();
					control.setOpacity(100);
					btn.setDisable(false);
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
		AiChatMV mv = getModelView();
		// Listen new messages to show
		final ObservableList<AiChatMessageMV> msgs = mv.getMessages();
		ListChangeListener<AiChatMessageMV> chl0 = ch0 ->{
			if(ch0.next() && ch0.wasAdded()) {
				ch0.getAddedSubList().forEach(m->{
					TAiChatMessage e = m.getEntity();
					if(!e.getRole().equals(TChatRole.SYSTEM))
						showMsg(e);
				});
			}
		};
		repo.add("chl0", chl0);
		msgs.addListener(new WeakListChangeListener<AiChatMessageMV>(chl0));
	}

	/**
	 * @param m
	 */
	private void showMsg(TAiChatMessage m){
		scrollFlag = true;
		StackPane p1 = util.buildTextPane(m);
		GridPane.setVgrow(p1, Priority.ALWAYS);
		VBox gp = super.getLayout("messages");
		gp.getChildren().add(p1);
	}
	
	@Override
	public void dispose() {
		repo.clear();
		repo = null;
		AiChatMV mv = getModelView();
		mv.getMessages().clear();
		//mv.getMessagesLoaded().setValue(false);
	}

}
