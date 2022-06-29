/**
 * 
 */
package com.tedros.tools.module.notify.model;

import java.util.Date;

import com.tedros.common.model.TFileEntity;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.domain.DomainApp;
import com.tedros.core.ejb.controller.TNotifyController;
import com.tedros.core.notify.model.TAction;
import com.tedros.core.notify.model.TNotify;
import com.tedros.core.notify.model.TNotifyLog;
import com.tedros.core.notify.model.TState;
import com.tedros.fxapi.annotation.control.TCallbackFactory;
import com.tedros.fxapi.annotation.control.TCellValueFactory;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TConverter;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.TFileField;
import com.tedros.fxapi.annotation.control.THTMLEditor;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewType;
import com.tedros.fxapi.annotation.control.TRadioButton;
import com.tedros.fxapi.annotation.control.TShowField;
import com.tedros.fxapi.annotation.control.TShowField.TField;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TVerticalRadioGroup;
import com.tedros.fxapi.annotation.form.TDetailForm;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.form.TSetting;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TMargin;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.layout.TVBox;
import com.tedros.fxapi.annotation.layout.TVBoxMargin;
import com.tedros.fxapi.annotation.layout.TVGrow;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.scene.control.TInsets;
import com.tedros.fxapi.annotation.view.TOption;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.builder.DateTimeFormatBuilder;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.domain.TFileExtension;
import com.tedros.fxapi.domain.TFileModelType;
import com.tedros.fxapi.domain.TLabelKey;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.property.TSimpleFileProperty;
import com.tedros.tools.module.notify.behaviour.TNotifyBehaviour;
import com.tedros.tools.module.notify.converter.ActionConverter;
import com.tedros.tools.module.notify.table.TNotifyLogDateCallBack;
import com.tedros.tools.module.notify.table.TNotifyLogStateCallBack;
import com.tedros.tools.module.notify.table.TNotifyLogTV;
import com.tedros.tools.util.ToolsKey;
import com.tedros.util.TDateUtil;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = "", scroll=true)
@TSetting(TNotifyMVSetting.class)
@TEjbService(serviceName = TNotifyController.JNDI_NAME, model=TNotify.class)
@TListViewPresenter(listViewMinWidth=400, 
paginator=@TPaginator(entityClass = TNotify.class, 
		serviceName = TNotifyController.JNDI_NAME, 
		searchFieldName="subject", modelViewClass=TNotifyMV.class, 
		orderBy= {@TOption(text = ToolsKey.SUBJECT, value = "subject"), 
				@TOption(text = ToolsKey.SEND_TO, value = "to"), 
				@TOption(text = ToolsKey.CALLED_BY, value = "calledBy") },
		showSearchField=true,
		show=true),
		presenter=@TPresenter(decorator = @TDecorator(viewTitle=ToolsKey.VIEW_NOTIFY,
buildModesRadioButton=false),
	behavior=@TBehavior(type=TNotifyBehaviour.class, saveOnlyChangedModels=false, saveAllModels=false)))
@TSecurity(id=DomainApp.NOTIFY_FORM_ID,
appName=ToolsKey.APP_TOOLS, 
moduleName=ToolsKey.MODULE_NOTIFY, 
viewName=ToolsKey.VIEW_NOTIFY,
allowedAccesses={	TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT,  
   					TAuthorizationType.NEW, TAuthorizationType.SAVE, TAuthorizationType.DELETE})
public class TNotifyMV extends TEntityModelView<TNotify> {
	
	private SimpleStringProperty displayProperty = new SimpleStringProperty();

	@TTabPane(tabs = { 
		@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"text"})),
			scroll=true, text = ToolsKey.MAIN_DATA),
		@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"file"})),
			scroll=true, text = ToolsKey.ATTACH_FILE),
		@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"eventLog"})),
			text = ToolsKey.EVENT_LOG)
	})
	private SimpleLongProperty id;
	

	@THBox(pane=@TPane(children={"refCode", "content"}), spacing=10, fillHeight=true,
			hgrow=@THGrow(priority={@TPriority(field="refCode", priority=Priority.SOMETIMES), 
					@TPriority(field="content", priority=Priority.ALWAYS)}))
	private SimpleStringProperty text;
	
	@TLabel(text=ToolsKey.REF_CODE)
	@TShowField
	@TVBox(	pane=@TPane(children={"refCode","subject","to","action","scheduleTime","state","processedTime"}), spacing=10, fillWidth=true,
	vgrow=@TVGrow(priority={@TPriority(field="refCode", priority=Priority.ALWAYS), 
						@TPriority(field="subject", priority=Priority.ALWAYS), 
						@TPriority(field="to", priority=Priority.ALWAYS), 
						@TPriority(field="action", priority=Priority.ALWAYS), 
						@TPriority(field="scheduleTime", priority=Priority.ALWAYS), 
						@TPriority(field="state", priority=Priority.ALWAYS), 
						@TPriority(field="processedTime", priority=Priority.ALWAYS)}))
	private SimpleStringProperty refCode;
	
	@TLabel(text=ToolsKey.SUBJECT)
	@TTextField(maxLength=120, 
	node=@TNode(requestFocus=true, parse = true),
	required=true)
	private SimpleStringProperty subject;
	
	@TLabel(text=ToolsKey.SEND_TO)
	@TTextField(maxLength=400, required=true)
	private SimpleStringProperty to;
	
	@TLabel(text=ToolsKey.ACTION)
	@TVerticalRadioGroup(
		converter=@TConverter(parse = true, type = ActionConverter.class),
		radioButtons = { @TRadioButton(text = ToolsKey.NONE, userData = ToolsKey.NONE ),
				@TRadioButton(text = ToolsKey.NONE, userData = ToolsKey.SEND ),
				@TRadioButton(text = ToolsKey.TO_QUEUE, userData = ToolsKey.TO_QUEUE ),
				@TRadioButton(text = ToolsKey.TO_SCHEDULE, userData = ToolsKey.TO_SCHEDULE ) 
		})
	private SimpleObjectProperty<TAction> action;
	
	@TLabel(text=ToolsKey.SCHEDULE_DATE_TIME)
	@TDatePickerField(dateFormat=DateTimeFormatBuilder.class)
	private SimpleObjectProperty<Date> scheduleTime;
	
	@TLabel(text=ToolsKey.STATE)
	@TShowField()
	private SimpleObjectProperty<TState> state;

	@TLabel(text=ToolsKey.DATE_PROCESSED)
	@TShowField(fields= {@TField(pattern=TDateUtil.DDMMYYYY_HHMM)})
	private SimpleObjectProperty<Date> processedTime;
	
	@THTMLEditor(control=@TControl( maxHeight=450, parse = true))
	@TVBox(	pane=@TPane(children={"content","integratedViewName"}), spacing=10, fillWidth=true,
	vgrow=@TVGrow(priority={@TPriority(field="content", priority=Priority.ALWAYS), 
						@TPriority(field="integratedViewName", priority=Priority.ALWAYS)}))
	private SimpleStringProperty content;
	
	@TLabel(text=TLabelKey.INTEGRATED_BY)
	@TShowField()
	@THBox(pane=@TPane(children={"integratedViewName", "integratedDate", "integratedModulePath"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="integratedViewName", priority=Priority.SOMETIMES), 
			@TPriority(field="integratedDate", priority=Priority.ALWAYS), 
			@TPriority(field="integratedModulePath", priority=Priority.ALWAYS)}))
	private SimpleStringProperty integratedViewName;
	
	@TLabel(text=TLabelKey.INTEGRATED_DATE)
	@TShowField()
	private SimpleObjectProperty<Date> integratedDate;
	
	@TLabel(text=TLabelKey.INTEGRATED_LINK)
	@TShowField()
	private SimpleStringProperty integratedModulePath;
	
	@TFileField(propertyValueType=TFileModelType.ENTITY, preLoadFileBytes=true,
	extensions= {TFileExtension.ALL_FILES}, showFilePath=true)
	@TVBox(	pane=@TPane(children={"file"}), spacing=10, fillWidth=true,
	margin=@TVBoxMargin(margin= {@TMargin(field="file", insets=@TInsets(top=50))}),
	vgrow=@TVGrow(priority={@TPriority(field="file", priority=Priority.ALWAYS)}))
	@TModelViewType(modelClass=TFileEntity.class)
	private TSimpleFileProperty<TFileEntity> file;
	
	@TTableView(columns = { 
		@TTableColumn(text = ToolsKey.DATE_INSERT, cellValue="insertDate", prefWidth=40,
			cellValueFactory=@TCellValueFactory(parse=true,
			value=@TCallbackFactory(parse=true, value=TNotifyLogDateCallBack.class))),
		@TTableColumn(text = ToolsKey.STATE, cellValue="state",
			cellValueFactory=@TCellValueFactory(parse=true,
			value=@TCallbackFactory(parse=true, value=TNotifyLogStateCallBack.class))),
		@TTableColumn(text = ToolsKey.DESCRIPTION, cellValue="description")
	})
	@TModelViewType(modelClass = TNotifyLog.class, modelViewClass=TNotifyLogTV.class)
	private ITObservableList<TNotifyLogTV> eventLog;
	

	public TNotifyMV(TNotify entity) {
		super(entity);
		super.formatFieldsToDisplay("%s / %s / %s", subject, to, integratedViewName);
	}
	
	@Override
	public void reload(TNotify model) {
		// TODO Auto-generated method stub
		super.reload(model);
		super.formatFieldsToDisplay("%s / %s / %s", subject, to, integratedViewName);
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
	 * @return the refCode
	 */
	public SimpleStringProperty getRefCode() {
		return refCode;
	}

	/**
	 * @param refCode the refCode to set
	 */
	public void setRefCode(SimpleStringProperty refCode) {
		this.refCode = refCode;
	}

	/**
	 * @return the subject
	 */
	public SimpleStringProperty getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(SimpleStringProperty subject) {
		this.subject = subject;
	}

	/**
	 * @return the to
	 */
	public SimpleStringProperty getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(SimpleStringProperty to) {
		this.to = to;
	}

	/**
	 * @return the content
	 */
	public SimpleStringProperty getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(SimpleStringProperty content) {
		this.content = content;
	}

	/**
	 * @return the action
	 */
	public SimpleObjectProperty<TAction> getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(SimpleObjectProperty<TAction> action) {
		this.action = action;
	}

	/**
	 * @return the state
	 */
	public SimpleObjectProperty<TState> getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(SimpleObjectProperty<TState> state) {
		this.state = state;
	}

	/**
	 * @return the eventLog
	 */
	public ITObservableList<TNotifyLogTV> getEventLog() {
		return eventLog;
	}

	/**
	 * @param eventLog the eventLog to set
	 */
	public void setEventLog(ITObservableList<TNotifyLogTV> eventLog) {
		this.eventLog = eventLog;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return displayProperty;
	}

	/**
	 * @return the text
	 */
	public SimpleStringProperty getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(SimpleStringProperty text) {
		this.text = text;
	}

	/**
	 * @return the file
	 */
	public TSimpleFileProperty<TFileEntity> getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(TSimpleFileProperty<TFileEntity> file) {
		this.file = file;
	}

	/**
	 * @return the scheduleTime
	 */
	public SimpleObjectProperty<Date> getScheduleTime() {
		return scheduleTime;
	}

	/**
	 * @param scheduleTime the scheduleTime to set
	 */
	public void setScheduleTime(SimpleObjectProperty<Date> scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

	/**
	 * @return the processedTime
	 */
	public SimpleObjectProperty<Date> getProcessedTime() {
		return processedTime;
	}

	/**
	 * @param processedTime the processedTime to set
	 */
	public void setProcessedTime(SimpleObjectProperty<Date> processedTime) {
		this.processedTime = processedTime;
	}

	/**
	 * @return the integratedViewName
	 */
	public SimpleStringProperty getIntegratedViewName() {
		return integratedViewName;
	}

	/**
	 * @param integratedViewName the integratedViewName to set
	 */
	public void setIntegratedViewName(SimpleStringProperty integratedViewName) {
		this.integratedViewName = integratedViewName;
	}

	/**
	 * @return the integratedDate
	 */
	public SimpleObjectProperty<Date> getIntegratedDate() {
		return integratedDate;
	}

	/**
	 * @param integratedDate the integratedDate to set
	 */
	public void setIntegratedDate(SimpleObjectProperty<Date> integratedDate) {
		this.integratedDate = integratedDate;
	}

	/**
	 * @return the integratedModulePath
	 */
	public SimpleStringProperty getIntegratedModulePath() {
		return integratedModulePath;
	}

	/**
	 * @param integratedModulePath the integratedModulePath to set
	 */
	public void setIntegratedModulePath(SimpleStringProperty integratedModulePath) {
		this.integratedModulePath = integratedModulePath;
	}

	
}
