/**
 * 
 */
package org.tedros.tools.module.ai.model;

import org.tedros.core.ai.model.TAiCompletion;
import org.tedros.core.ai.model.TRequestEvent;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.controller.TAiCompletionController;
import org.tedros.core.domain.DomainApp;
import org.tedros.core.security.model.TUser;
import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TButtonField;
import org.tedros.fx.annotation.control.TCallbackFactory;
import org.tedros.fx.annotation.control.TCellFactory;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TNumberSpinnerField;
import org.tedros.fx.annotation.control.TSliderField;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextInputControl;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.form.TSetting;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.layout.TVBox;
import org.tedros.fx.annotation.layout.TVGrow;
import org.tedros.fx.annotation.page.TPage;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.control.TButtonBase;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.scene.control.TLabeled;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.control.tablecell.TMediumDateTimeCallback;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.ai.action.SendActionBuilder;
import org.tedros.tools.module.ai.settings.CompletionSetting;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Orientation;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TSetting(CompletionSetting.class)
@TForm(header = "", showBreadcrumBar=false)
@TEjbService(serviceName = TAiCompletionController.JNDI_NAME, model=TAiCompletion.class)
@TListViewPresenter(listViewMinWidth=300,
	page=@TPage(query=@TQuery(entity = TAiCompletion.class), 
		serviceName = TAiCompletionController.JNDI_NAME),
	presenter=@TPresenter(
		decorator = @TDecorator(viewTitle=ToolsKey.VIEW_AI_COMPLETION),
		behavior=@TBehavior(saveAllModels=false, saveOnlyChangedModels=false)
	))
@TSecurity(id=DomainApp.ASK_TEROS_FORM_ID,
appName=ToolsKey.APP_TOOLS, moduleName=ToolsKey.MODULE_AI, viewName=ToolsKey.VIEW_AI_COMPLETION,
allowedAccesses={	TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT,  
	   				TAuthorizationType.NEW, TAuthorizationType.SAVE, TAuthorizationType.DELETE})
public class CompletionMV extends TEntityModelView<TAiCompletion> {

	@TTabPane(tabs = { 
		@TTab(closable=false, text = TUsualKey.MAIN_DATA, 
			fields={"response", "prompt"}, scroll=true),
		@TTab(closable=false, fields={"events"}, text = TUsualKey.EVENT_LOG)})
	private SimpleStringProperty title;
	
	@TTextAreaField(wrapText=true, textInputControl=
			@TTextInputControl(parse = true, editable=false,
			promptText=ToolsKey.PROMPT_AI_RESPONSE))
	private SimpleStringProperty response;
	
	@TTextAreaField(wrapText=true, prefRowCount=5, textInputControl=
			@TTextInputControl(parse = true, 
			promptText=ToolsKey.PROMPT_AI_PROMPT), 
			control=@TControl(parse = true, tooltip=ToolsKey.TEXT_PROMPT))
	@THBox(pane=@TPane(children={"prompt", "temperature"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="temperature", priority=Priority.NEVER), 
			@TPriority(field="prompt", priority=Priority.ALWAYS)}))
	private SimpleStringProperty prompt;
	
	@TLabel(text=TUsualKey.CLEVERNESS)
	@TSliderField(max = 2, min = 0, blockIncrement=0.1, majorTickUnit=0.5, minorTickCount=5, 
	showTickLabels=true, showTickMarks=true, snapToTicks=true, orientation=Orientation.VERTICAL,
	control=@TControl(parse = true, tooltip=ToolsKey.TEXT_DETERMINISTIC))
	@TVBox(pane=@TPane(children={"temperature", "maxTokens"}), spacing=10, fillWidth=true,
	vgrow=@TVGrow(priority={@TPriority(field="temperature", priority=Priority.ALWAYS), 
			@TPriority(field="maxTokens", priority=Priority.NEVER)}))
	private SimpleDoubleProperty temperature;
	
	@TLabel(text=TUsualKey.MAX_TOKENS)
	@TNumberSpinnerField(maxValue = 4096, minValue=16, 
			control=@TControl(parse = true, tooltip=ToolsKey.TEXT_MAX_TOKENS))
	private SimpleIntegerProperty maxTokens;
	
	@TButtonField(buttonBase=@TButtonBase(onAction=SendActionBuilder.class),
			labeled = @TLabeled(parse = true, text=TFxKey.BUTTON_SEND))
	private SimpleStringProperty send;
	
	@TTableView(columns = { 
		@TTableColumn(text = TUsualKey.DATE_INSERT, cellValue="insertDate", 
				cellFactory=@TCellFactory(parse = true, 
				callBack=@TCallbackFactory(parse=true, value=TMediumDateTimeCallback.class))), 
		@TTableColumn(text = TUsualKey.PROMPT, cellValue="prompt"),
		@TTableColumn(text = TUsualKey.RESPONSE, cellValue="response"),
		@TTableColumn(text = TUsualKey.EVENT_LOG, cellValue="log")
	})
	@TGenericType(model = TRequestEvent.class, modelView=EventMV.class)
	private ITObservableList<EventMV> events;
	
	public CompletionMV(TAiCompletion m) {
		super(m);
		if(m.getUser()==null) {
			TUser u = TedrosContext.getLoggedUser();
			m.setUser(u.getName());
			m.setUserId(u.getId());
		}
		super.formatToString("%s", title);
		prompt.addListener((a,o,n)->{
			if(n!=null && n.length()>40) {
				title.setValue(n.substring(0, 34)+"...");
			}else 
				title.setValue(n);
		});
	}


	/**
	 * @return the response
	 */
	public SimpleStringProperty getResponse() {
		return response;
	}


	/**
	 * @param response the response to set
	 */
	public void setResponse(SimpleStringProperty response) {
		this.response = response;
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
	 * @return the temperature
	 */
	public SimpleDoubleProperty getTemperature() {
		return temperature;
	}


	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(SimpleDoubleProperty temperature) {
		this.temperature = temperature;
	}


	/**
	 * @return the maxTokens
	 */
	public SimpleIntegerProperty getMaxTokens() {
		return maxTokens;
	}


	/**
	 * @param maxTokens the maxTokens to set
	 */
	public void setMaxTokens(SimpleIntegerProperty maxTokens) {
		this.maxTokens = maxTokens;
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


	/**
	 * @return the send
	 */
	public SimpleStringProperty getSend() {
		return send;
	}


	/**
	 * @param send the send to set
	 */
	public void setSend(SimpleStringProperty send) {
		this.send = send;
	}


}
