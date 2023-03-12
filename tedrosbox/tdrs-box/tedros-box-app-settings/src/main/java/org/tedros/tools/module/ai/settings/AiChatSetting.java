/**
 * 
 */
package org.tedros.tools.module.ai.settings;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.core.TLanguage;
import org.tedros.core.ai.model.TAiChatCompletion;
import org.tedros.core.ai.model.TAiChatMessage;
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
import org.tedros.tools.module.ai.model.EventMV;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.WeakListChangeListener;
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
		final ObservableList<TAiChatMessage> msgs = mv.getMessages();
		
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
			msgs.addAll(lst0);
			
			
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
			AiChatMV mv = getModelView(); 
			final ObservableList<TAiChatMessage> msgs = mv.getMessages();
			
			if(msgs.isEmpty()) {
				TAiChatMessage m = new TAiChatMessage();
				m.setContent(TLanguage.getInstance()
						.getFormatedString(ToolsKey.AI_WELCOME, TedrosContext.getLoggedUser().getName()));
				m.setInsertDate(new Date());
				m.setRole(TChatRole.SYSTEM);
				m.setChat(mv.getModel());
				
				msgs.add(m);
			}
			
			String msg = mv.getPrompt().getValue();
			 try {
		            TAiChatMessage m = new TAiChatMessage();
					m.setContent(msg);
					m.setInsertDate(new Date());
					m.setRole(TChatRole.USER);
					m.setChat(mv.getModel());
					
					msgs.add(m);
					mv.getPrompt().setValue(null);
					
					TChatResult res = util.chat(TedrosContext.getLoggedUser()
							.getAccessToken(), msgs);
					
					res.getChoices().forEach(c->{
						 TAiChatMessage m1 = new TAiChatMessage();
							m1.setContent(c.getMessage().getContent());
							m1.setInsertDate(new Date());
							m1.setRole(TChatRole.ASSISTANT);
							m1.setChat(mv.getModel());
							msgs.add(m1);
					});
					
					TAiChatCompletion cc = mv.getEntity();
					TRequestEvent ev = TRequestEvent.build(TRequestType.CHAT, res.getLog(), 
							res.getUsage(), cc.getModel().value(), 
							cc.getTemperature(), cc.getMaxTokens());
					mv.getEvents().add(new EventMV(ev));
					
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
	 * @param mv
	 * @return
	 */
	private void listenReceivedMsg() {
		AiChatMV mv = getModelView();
		// Listen new messages to show
		final ObservableList<TAiChatMessage> msgs = mv.getMessages();
		ListChangeListener<TAiChatMessage> chl0 = ch0 ->{
			if(ch0.next() && ch0.wasAdded()) {
				ch0.getAddedSubList().forEach(m->{
					if(!m.getRole().equals(TChatRole.SYSTEM))
						showMsg(m);
				});
			}
		};
		repo.add("chl0", chl0);
		msgs.addListener(new WeakListChangeListener<TAiChatMessage>(chl0));
	}

	/**
	 * @param mv
	 */
	private void saveChat(AiChatMV mv) {
		try {
			util.saveChat(TedrosContext.getLoggedUser()
					.getAccessToken(), mv.getEntity());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
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
