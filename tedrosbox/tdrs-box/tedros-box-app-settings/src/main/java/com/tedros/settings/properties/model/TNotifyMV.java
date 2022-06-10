/**
 * 
 */
package com.tedros.settings.properties.model;

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
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.TEditEntityModal;
import com.tedros.fxapi.annotation.control.TFileField;
import com.tedros.fxapi.annotation.control.THTMLEditor;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewType;
import com.tedros.fxapi.annotation.control.TShowField;
import com.tedros.fxapi.annotation.control.TShowField.TField;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TDetailForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.layout.TVBox;
import com.tedros.fxapi.annotation.layout.TVGrow;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.builder.DateTimeFormatBuilder;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.domain.TFileExtension;
import com.tedros.fxapi.domain.TFileModelType;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.property.TSimpleFileProperty;
import com.tedros.util.TDateUtil;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TEjbService(serviceName = TNotifyController.JNDI_NAME, model=TNotify.class)
@TListViewPresenter(presenter=@TPresenter(decorator = @TDecorator(viewTitle="#{view.notify}",
buildModesRadioButton=false),
	behavior=@TBehavior(saveOnlyChangedModels=false)))
@TSecurity(id=DomainApp.NOTIFY_FORM_ID,
appName="#{app.settings}", 
moduleName="#{module.propertie}", 
viewName="#{view.notify}",
allowedAccesses={	TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT,  
   					TAuthorizationType.NEW, TAuthorizationType.SAVE, TAuthorizationType.DELETE})
public class TNotifyMV extends TEntityModelView<TNotify> {

	@TTabPane(tabs = { 
		@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"text", "file"})), text = "#{label.main.data}"),
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"eventLog"})), text = "#{label.event.log}")
		})
	private SimpleLongProperty id;
	

	@THBox(pane=@TPane(children={"refCode", "content"}), spacing=10, fillHeight=true,
			hgrow=@THGrow(priority={@TPriority(field="refCode", priority=Priority.SOMETIMES), 
					@TPriority(field="content", priority=Priority.ALWAYS)}))
	private SimpleStringProperty text;
	
	@TLabel(text="#{label.ref.code}")
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
	
	@TLabel(text="#{label.subject}")
	@TTextField(maxLength=120, 
	node=@TNode(requestFocus=true, parse = true),
	required=true)
	private SimpleStringProperty subject;
	
	@TLabel(text="#{label.send.to}")
	@TTextField(maxLength=400, required=true)
	private SimpleStringProperty to;
	
	@TLabel(text="#{label.action}")
	@TComboBoxField(firstItemTex="#{label.select}",
	items=ActionsBuilder.class)
	private SimpleObjectProperty<TAction> action;
	
	@TLabel(text="#{label.schedule.date.time}")
	@TDatePickerField(dateFormat=DateTimeFormatBuilder.class)
	private SimpleObjectProperty<Date> scheduleTime;
	
	@TLabel(text="#{label.state}")
	@TShowField()
	private SimpleObjectProperty<TState> state;

	@TLabel(text="#{label.date.processed}")
	@TShowField(fields= {@TField(pattern=TDateUtil.DDMMYYYY_HHMM)})
	private SimpleObjectProperty<Date> processedTime;
	
	@THTMLEditor(control=@TControl( maxHeight=500, parse = true))
	private SimpleStringProperty content;
	
	@TLabel(text="#{label.attach.file}")
	@TFileField(propertyValueType=TFileModelType.ENTITY, preLoadFileBytes=true,
	extensions= {TFileExtension.ALL_FILES}, showFilePath=true)
	@TVBox(	pane=@TPane(children={"summary","file"}), spacing=10, fillWidth=true,
	vgrow=@TVGrow(priority={@TPriority(field="summary", priority=Priority.ALWAYS), 
						@TPriority(field="file", priority=Priority.ALWAYS)}))
	@TModelViewType(modelClass=TFileEntity.class)
	private TSimpleFileProperty<TFileEntity> file;
	
	@TLabel(text="#{label.event.log}")
	@TEditEntityModal(height=60, modelClass = TNotifyLog.class, modelViewClass=TNotifyLogMV.class)
	@TModelViewType(modelClass = TNotifyLog.class, modelViewClass=TNotifyLogMV.class)
	private ITObservableList<TNotifyLogMV> eventLog;
	

	public TNotifyMV(TNotify entity) {
		super(entity);
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
	public ITObservableList<TNotifyLogMV> getEventLog() {
		return eventLog;
	}

	/**
	 * @param eventLog the eventLog to set
	 */
	public void setEventLog(ITObservableList<TNotifyLogMV> eventLog) {
		this.eventLog = eventLog;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return subject;
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
	
}
