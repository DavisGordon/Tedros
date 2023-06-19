/**
 * 
 */
package org.tedros.tools.module.ai.model;

import org.tedros.core.ai.model.TAiChatCompletion;
import org.tedros.core.ai.model.TAiChatMessage;
import org.tedros.core.ai.model.TRequestEvent;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.controller.TAiChatCompletionController;
import org.tedros.core.domain.DomainApp;
import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TButtonField;
import org.tedros.fx.annotation.control.TCallbackFactory;
import org.tedros.fx.annotation.control.TCellFactory;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.form.TSetting;
import org.tedros.fx.annotation.layout.TToolBar;
import org.tedros.fx.annotation.layout.TVBox;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.scene.control.TLabeled;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.control.tablecell.TMediumDateTimeCallback;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.ai.action.ChatDeleteAction;
import org.tedros.tools.module.ai.action.ChatSaveAction;
import org.tedros.tools.module.ai.settings.AiChatSetting;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
@TSetting(AiChatSetting.class)
@TForm(header = ToolsKey.FORM_AI_CHAT, showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = TAiChatCompletionController.JNDI_NAME, model=TAiChatCompletion.class)
@TListViewPresenter(listViewMaxWidth=300, listViewMinWidth=300,
	presenter=@TPresenter(
		decorator = @TDecorator(
			viewTitle=ToolsKey.VIEW_AI_CHAT, buildModesRadioButton=false),
		behavior=@TBehavior(action= {ChatSaveAction.class, ChatDeleteAction.class},
			runNewActionAfterSave=false, saveAllModels=false)))
@TSecurity(id=DomainApp.CHAT_TEROS_FORM_ID,
appName=ToolsKey.APP_TOOLS, moduleName=ToolsKey.MODULE_AI, viewName=ToolsKey.VIEW_AI_CHAT,
allowedAccesses={	TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT,  
	   				TAuthorizationType.NEW, TAuthorizationType.SAVE, TAuthorizationType.DELETE})
public class AiChatMV extends TEntityModelView<TAiChatCompletion> {

	@TTabPane(tabs = { 
		@TTab(text = "Chat", scroll=true, fields = { "messages" }), 
		@TTab(text = TUsualKey.EVENT_LOG, scroll=true, fields = { "events" })}, 
		region=@TRegion(minHeight=400, maxHeight=400, parse = true))
	private SimpleStringProperty title;
	
	@TVBox(fillWidth=true, spacing=20)
	@TGenericType(model = TAiChatMessage.class, modelView=AiChatMessageMV.class)
	private ITObservableList<AiChatMessageMV> messages; 

	@TTextAreaField(prefRowCount=4, wrapText=true, 
			control= @TControl(maxHeight=70, parse = true))
	private SimpleStringProperty prompt;
	
	@TToolBar(items = { "sendBtn", "clearBtn" })
	@TButtonField(labeled = @TLabeled(parse = true, text=TFxKey.BUTTON_SEND))
	private SimpleStringProperty sendBtn;

	@TButtonField(labeled = @TLabeled(parse = true, text=TFxKey.BUTTON_CLEAN))
	private SimpleStringProperty clearBtn;
	
	@TTableView(columns = { 
		@TTableColumn(text = TUsualKey.DATE_INSERT, cellValue="insertDate", 
				cellFactory=@TCellFactory(parse = true, 
				callBack=@TCallbackFactory(parse=true, value=TMediumDateTimeCallback.class))), 
		@TTableColumn(text = TUsualKey.EVENT_LOG, cellValue="log")
	})
	@TGenericType(model = TRequestEvent.class, modelView=EventMV.class)
	private ITObservableList<EventMV> events;
	
	
	public AiChatMV(TAiChatCompletion entity) {
		super(entity);
		super.formatToString("%s", title);
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
	 * @return the messages
	 */
	public ITObservableList<AiChatMessageMV> getMessages() {
		return messages;
	}


	/**
	 * @param messages the messages to set
	 */
	public void setMessages(ITObservableList<AiChatMessageMV> messages) {
		this.messages = messages;
	}


	/**
	 * @return the prompt
	 */
	public SimpleStringProperty getPrompt() {
		return prompt;
	}


	/**
	 * @param prompt the prompt to set
	 */
	public void setPrompt(SimpleStringProperty prompt) {
		this.prompt = prompt;
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
	 * @return the events
	 */
	public ITObservableList<EventMV> getEvents() {
		return events;
	}


	/**
	 * @param events the events to set
	 */
	public void setEvents(ITObservableList<EventMV> events) {
		this.events = events;
	}

}
