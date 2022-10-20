/**
 * 
 */
package org.tedros.chat.module.client.model;

import org.tedros.chat.CHATKey;
import org.tedros.chat.ejb.controller.IChatController;
import org.tedros.chat.entity.Chat;
import org.tedros.chat.entity.ChatMessage;
import org.tedros.chat.entity.ChatUser;
import org.tedros.fx.annotation.control.TButtonField;
import org.tedros.fx.annotation.control.TModelViewType;
import org.tedros.fx.annotation.control.TMultipleSelectionModal;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.TBorderPane;
import org.tedros.fx.annotation.layout.TGridPane;
import org.tedros.fx.annotation.layout.TGridPane.TField;
import org.tedros.fx.annotation.layout.TToolBar;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.scene.control.TLabeled;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */

@TForm(name = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = IChatController.JNDI_NAME, model=Chat.class)
@TListViewPresenter(
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=CHATKey.VIEW_CLIENT_MESSAGES,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false)))

/*
@TPresenter(
		decorator=@TDecorator(type=TChatDecorator.class, 
		viewTitle=CHATKey.VIEW_CLIENT_MESSAGES),
		behavior=@TBehavior(type=TChatBehaviour.class))*/
/*@TSecurity(	id=DomainApp.CHAT_FORM_ID, appName=CHATKey.APP_CHAT, moduleName=CHATKey.MODULE_MESSAGES, 
			allowedAccesses= {TAuthorizationType.VIEW_ACCESS, TAuthorizationType.SEARCH})*/
public class TChatMV extends TEntityModelView<Chat> {

	
	@TBorderPane(center="messages", bottom="title")
	private SimpleObjectProperty<ChatUser> owner;
	
	@TGridPane
	@TModelViewType(modelClass = ChatUser.class, modelViewClass=ChatUserMV.class)
	private ITObservableList<ChatMessage> messages; 
	
	@TTextField
	@TGridPane(add={@TField(field = "title", columnIndex = 0,  rowIndex = 0, columnspan=2),
			@TField(field = "participants", columnIndex = 0,  rowIndex = 1),
			@TField(field = "message", columnIndex = 1,  rowIndex = 1),
			@TField(field = "sendBtn", columnIndex = 1,  rowIndex = 2)
	})
	private SimpleStringProperty title;
	
	@TMultipleSelectionModal(modelClass = ChatUser.class, modelViewClass = ChatUserMV.class)
	@TModelViewType(modelClass = ChatUser.class, modelViewClass=ChatUserMV.class)
	private ITObservableList<ChatUserMV> participants;
	
	@TTextAreaField
	private SimpleStringProperty message;
	
	@TToolBar(items = { "sendBtn", "clearBtn" })
	@TButtonField(labeled = @TLabeled(parse = true, text="Send"))
	private SimpleStringProperty sendBtn;

	@TButtonField(labeled = @TLabeled(parse = true, text="Clear"))
	private SimpleStringProperty clearBtn;
	
	public TChatMV(Chat entity) {
		super(entity);
		super.formatToString("%s", title);
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

}
