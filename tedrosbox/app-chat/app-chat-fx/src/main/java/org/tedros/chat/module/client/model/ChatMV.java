/**
 * 
 */
package org.tedros.chat.module.client.model;

import org.tedros.chat.CHATKey;
import org.tedros.chat.domain.DomainApp;
import org.tedros.chat.ejb.controller.IChatController;
import org.tedros.chat.ejb.controller.IChatUserController;
import org.tedros.chat.entity.Chat;
import org.tedros.chat.entity.ChatMessage;
import org.tedros.chat.entity.ChatUser;
import org.tedros.chat.module.client.behaviour.ChatBehaviour;
import org.tedros.chat.module.client.behaviour.ChatListViewCallback;
import org.tedros.chat.module.client.decorator.ChatDecorator;
import org.tedros.chat.module.client.setting.ChatClient;
import org.tedros.chat.module.client.setting.ChatFormSetting;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TFxKey;
import org.tedros.fx.annotation.control.TButtonField;
import org.tedros.fx.annotation.control.TFileField;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TPickListField;
import org.tedros.fx.annotation.control.TProcess;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.form.TSetting;
import org.tedros.fx.annotation.layout.TGridPane;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.layout.TToolBar;
import org.tedros.fx.annotation.layout.TVBox;
import org.tedros.fx.annotation.layout.TVGrow;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.scene.control.TLabeled;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.domain.TFileModelType;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.fx.property.TSimpleFileProperty;
import org.tedros.server.model.ITFileModel;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.WeakListChangeListener;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TSetting(ChatFormSetting.class)
@TForm(header = "", showBreadcrumBar=false, scroll=false)
@TEjbService(serviceName = IChatController.JNDI_NAME, model=Chat.class)
@TListViewPresenter(listViewMaxWidth=400, listViewMinWidth=400,
	presenter=@TPresenter(
		decorator = @TDecorator(type=ChatDecorator.class, 
			viewTitle=CHATKey.VIEW_CLIENT_MESSAGES, cancelButtonText=TFxKey.BUTTON_BACK,
			buildSaveButton=false, buildModesRadioButton=false, buildCollapseButton=false),
		behavior=@TBehavior(type=ChatBehaviour.class, listViewCallBack=ChatListViewCallback.class, 
			runNewActionAfterSave=false, saveAllModels=false)))
@TSecurity(	id=DomainApp.CHAT_FORM_ID, appName=CHATKey.APP_CHAT, 
	moduleName=CHATKey.MODULE_MESSAGES, viewName=CHATKey.VIEW_CLIENT_MESSAGES,
	allowedAccesses= {TAuthorizationType.VIEW_ACCESS, TAuthorizationType.SAVE, 
		TAuthorizationType.EDIT, TAuthorizationType.READ,  TAuthorizationType.DELETE, 
		TAuthorizationType.NEW, TAuthorizationType.SEARCH})
public class ChatMV extends TEntityModelView<Chat> {

	private SimpleLongProperty id;

	private SimpleLongProperty totalMessages;
	private SimpleLongProperty totalSentMessages;
	private SimpleLongProperty totalReceivedMessages;
	private SimpleLongProperty totalViewedMessages;
	private SimpleLongProperty totalUnreadMessages;
	private SimpleBooleanProperty messagesLoaded;
	
	@TVBox(pane=@TPane(children= {"owner", "message", "sendFile"}), 
			vgrow=@TVGrow(priority= {
					@TPriority(field="owner", priority=Priority.ALWAYS), 
					@TPriority(field="message", priority=Priority.NEVER), 
					@TPriority(field="sendFile", priority=Priority.NEVER)
					}), fillWidth=true, spacing=10)
	private SimpleStringProperty title;
	
	@TTabPane(tabs = { 
		@TTab(text = CHATKey.RECIPIENTS, scroll=false, fields = { "participants" }), 
		@TTab(text = "Chat", scroll=true, fields = { "messages" })}, 
		region=@TRegion(minHeight=300, maxHeight=350, minWidth=700, maxWidth=700, parse = true))
	private SimpleObjectProperty<ChatUser> owner;
	
	@TGridPane(vgap=12, hgap=12)
	@TGenericType(model = ChatUser.class, modelView=ChatUserMV.class)
	private ITObservableList<ChatMessage> messages; 
	
	@TPickListField(
		process=@TProcess(service = IChatUserController.JNDI_NAME,
		query=@TQuery(entity=ChatUser.class), modelView=ChatUserMV.class))
	@TGenericType(model = ChatUser.class, modelView=ChatUserMV.class)
	private ITObservableList<ChatUserMV> participants;
	
	@TTextAreaField(prefRowCount=4, wrapText=true, control= @TControl(maxHeight=70, parse = true))
	private SimpleStringProperty message;
	
	@THBox(pane=@TPane(children= {"sendFile", "sendBtn"}), 
			hgrow=@THGrow(priority= {
					@TPriority(field="sendFile", priority=Priority.ALWAYS), 
					@TPriority(field="sendBtn", priority=Priority.NEVER)
					}))
	@TFileField(propertyValueType=TFileModelType.MODEL)
	private TSimpleFileProperty<ITFileModel> sendFile;
	
	@TToolBar(items = { "sendBtn", "clearBtn" })
	@TButtonField(labeled = @TLabeled(parse = true, text=CHATKey.SEND))
	private SimpleStringProperty sendBtn;

	@TButtonField(labeled = @TLabeled(parse = true, text=CHATKey.CLEAR))
	private SimpleStringProperty clearBtn;
	
	
	public ChatMV(Chat entity) {
		super(entity);
		super.formatToString("%s", title);
		this.messagesLoaded.setValue(false);
		ListChangeListener<ChatMessage> lcl = c ->{
			if(c.next())
				this.countTotalUnreadMessages();
		};
		super.getListenerRepository().add("messagesCountLCL", lcl);
		this.messages.addListener(new WeakListChangeListener<>(lcl));
	}

	/**
	 * @return the messages
	 */
	public ITObservableList<ChatMessage> getMessages() {
		return messages;
	}

	/**
	 * @param messages the messages to set
	 */
	public void setMessages(ITObservableList<ChatMessage> messages) {
		this.messages = messages;
	}

	/**
	 * @return the owner
	 */
	public SimpleObjectProperty<ChatUser> getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(SimpleObjectProperty<ChatUser> owner) {
		this.owner = owner;
	}

	/**
	 * @return the title
	 */
	public SimpleStringProperty getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(SimpleStringProperty title) {
		this.title = title;
	}

	/**
	 * @return the participants
	 */
	public ITObservableList<ChatUserMV> getParticipants() {
		return participants;
	}

	/**
	 * @param participants the participants to set
	 */
	public void setParticipants(ITObservableList<ChatUserMV> participants) {
		this.participants = participants;
	}

	/**
	 * @return the message
	 */
	public SimpleStringProperty getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(SimpleStringProperty message) {
		this.message = message;
	}

	/**
	 * @return the sendBtn
	 */
	public SimpleStringProperty getSendBtn() {
		return sendBtn;
	}

	/**
	 * @param sendBtn the sendBtn to set
	 */
	public void setSendBtn(SimpleStringProperty sendBtn) {
		this.sendBtn = sendBtn;
	}

	/**
	 * @return the clearBtn
	 */
	public SimpleStringProperty getClearBtn() {
		return clearBtn;
	}

	/**
	 * @param clearBtn the clearBtn to set
	 */
	public void setClearBtn(SimpleStringProperty clearBtn) {
		this.clearBtn = clearBtn;
	}

	/**
	 * @return the id
	 */
	public SimpleLongProperty getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	/**
	 * @return the sendFile
	 */
	public TSimpleFileProperty<ITFileModel> getSendFile() {
		return sendFile;
	}

	/**
	 * @param sendFile the sendFile to set
	 */
	public void setSendFile(TSimpleFileProperty<ITFileModel> sendFile) {
		this.sendFile = sendFile;
	}
	
	@Override
	public boolean equals(Object obj) {

		if(obj instanceof ChatMV)
			return getModel().equals(((ChatMV)obj).getModel());
		
		return false;
	}
	
	@Override
	public boolean isChanged() {
		return false;
	}
	
	public long countTotalUnreadMessages() {
		if(messages!=null && !messages.isEmpty()) {
			ChatUser user = ChatClient.getInstance().getOwner();
			this.totalMessages.setValue(messages.size());
			this.totalSentMessages.setValue( messages.parallelStream()
					.filter(p->p.getFrom().equals(user))
					.count());
			this.totalReceivedMessages.setValue( messages.parallelStream()
					.filter(p->p.wasReceived(user) && !p.getFrom().equals(user))
					.count());
			this.totalViewedMessages.setValue( messages.parallelStream()
					.filter(p->p.wasViewed(user) && !p.getFrom().equals(user))
					.count());
		}
		long total =  this.totalMessages.getValue() 
				- (this.totalSentMessages.getValue() 
				+ this.totalViewedMessages.getValue());
		this.totalUnreadMessages.setValue(total);
		return total;
	}
	
	public void increaseTotalMessages() {
		this.totalMessages.setValue(this.totalMessages.getValue()+1);
	}
	
	public void increaseViewedMessages() {
		this.totalViewedMessages.setValue(this.totalViewedMessages.getValue()+1);
	}

	/**
	 * @return the totalSentMessages
	 */
	public SimpleLongProperty getTotalSentMessages() {
		return totalSentMessages;
	}

	/**
	 * @param totalSentMessages the totalSentMessages to set
	 */
	public void setTotalSentMessages(SimpleLongProperty totalSentMessages) {
		this.totalSentMessages = totalSentMessages;
	}

	/**
	 * @return the totalReceivedMessages
	 */
	public SimpleLongProperty getTotalReceivedMessages() {
		return totalReceivedMessages;
	}

	/**
	 * @param totalReceivedMessages the totalReceivedMessages to set
	 */
	public void setTotalReceivedMessages(SimpleLongProperty totalReceivedMessages) {
		this.totalReceivedMessages = totalReceivedMessages;
	}

	/**
	 * @return the totalViewedMessages
	 */
	public SimpleLongProperty getTotalViewedMessages() {
		return totalViewedMessages;
	}

	/**
	 * @param totalViewedMessages the totalViewedMessages to set
	 */
	public void setTotalViewedMessages(SimpleLongProperty totalViewedMessages) {
		this.totalViewedMessages = totalViewedMessages;
	}

	/**
	 * @return the totalUnreadMessages
	 */
	public SimpleLongProperty getTotalUnreadMessages() {
		return totalUnreadMessages;
	}

	/**
	 * @param totalUnreadMessages the totalUnreadMessages to set
	 */
	public void setTotalUnreadMessages(SimpleLongProperty totalUnreadMessages) {
		this.totalUnreadMessages = totalUnreadMessages;
	}

	/**
	 * @return the totalMessages
	 */
	public SimpleLongProperty getTotalMessages() {
		return totalMessages;
	}

	/**
	 * @param totalMessages the totalMessages to set
	 */
	public void setTotalMessages(SimpleLongProperty totalMessages) {
		this.totalMessages = totalMessages;
	}

	/**
	 * @return the messagesLoaded
	 */
	public SimpleBooleanProperty getMessagesLoaded() {
		return messagesLoaded;
	}

	/**
	 * @param messagesLoaded the messagesLoaded to set
	 */
	public void setMessagesLoaded(SimpleBooleanProperty messagesLoaded) {
		this.messagesLoaded = messagesLoaded;
	}
	
	public Boolean isMessagesLoaded() {
		return messagesLoaded.getValue();
	}

}
