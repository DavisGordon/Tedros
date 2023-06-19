/**
 * 
 */
package org.tedros.tools.module.notify.model;

import java.text.DateFormat;
import java.util.Date;

import org.tedros.common.model.TFileEntity;
import org.tedros.core.TLanguage;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.controller.TNotifyController;
import org.tedros.core.domain.DomainApp;
import org.tedros.core.notify.model.TAction;
import org.tedros.core.notify.model.TNotify;
import org.tedros.core.notify.model.TNotifyLog;
import org.tedros.core.notify.model.TState;
import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TCallbackFactory;
import org.tedros.fx.annotation.control.TCellFactory;
import org.tedros.fx.annotation.control.TConverter;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TFileField;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.THTMLEditor;
import org.tedros.fx.annotation.control.TIntegratedLinkField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TRadio;
import org.tedros.fx.annotation.control.TShowField;
import org.tedros.fx.annotation.control.TShowField.TField;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.control.TVRadioGroup;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.form.TSetting;
import org.tedros.fx.annotation.layout.TFieldInset;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.layout.TVBox;
import org.tedros.fx.annotation.layout.TVBox.TMargin;
import org.tedros.fx.annotation.layout.TVGrow;
import org.tedros.fx.annotation.page.TPage;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TOrder;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.query.TTemporal;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.scene.control.TInsets;
import org.tedros.fx.builder.DateTimeFormatBuilder;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.control.tablecell.TMediumDateTimeCallback;
import org.tedros.fx.domain.TFileExtension;
import org.tedros.fx.domain.TFileModelType;
import org.tedros.fx.domain.TTimeStyle;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.fx.model.TFormatter;
import org.tedros.fx.property.TSimpleFileProperty;
import org.tedros.server.query.TCompareOp;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.notify.behaviour.TNotifyBehaviour;
import org.tedros.tools.module.notify.converter.ActionConverter;
import org.tedros.tools.module.notify.table.TNotifyLogStateCallBack;
import org.tedros.tools.module.notify.table.TNotifyLogTV;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(header = "", scroll=true)
@TSetting(TNotifyMVSetting.class)
@TEjbService(serviceName = TNotifyController.JNDI_NAME, model=TNotify.class)
@TListViewPresenter(listViewMinWidth=400, 
	page=@TPage(serviceName = TNotifyController.JNDI_NAME,  modelView=TNotifyMV.class,
	query=@TQuery(entity = TNotify.class, 		
			condition= {@TCondition(label = TUsualKey.REF_CODE, field = "refCode", operator=TCompareOp.EQUAL), 
					@TCondition(label = TUsualKey.DATE_PROCESSED, field = "processedTime", operator=TCompareOp.GREATER_EQ_THAN, temporal=TTemporal.DATE),
					@TCondition(label = TUsualKey.SUBJECT, field = "subject", operator=TCompareOp.LIKE),
					@TCondition(label = TUsualKey.SEND_TO, field = "to", operator=TCompareOp.LIKE) },
			orderBy= {@TOrder(label = TUsualKey.REF_CODE, field = "refCode"), 
				@TOrder(label = TUsualKey.DATE_PROCESSED, field = "processedTime"),
				@TOrder(label = TUsualKey.SUBJECT, field = "subject"),
				@TOrder(label = TUsualKey.SEND_TO, field = "to") }
		), showSearch=true, showOrderBy=true),
	presenter=@TPresenter(
		decorator = @TDecorator(viewTitle=ToolsKey.VIEW_NOTIFY, buildModesRadioButton=false),
		behavior=@TBehavior(type=TNotifyBehaviour.class, saveOnlyChangedModels=false, saveAllModels=false)))
@TSecurity(id=DomainApp.NOTIFY_FORM_ID, appName=ToolsKey.APP_TOOLS, 
moduleName=ToolsKey.MODULE_NOTIFY, viewName=ToolsKey.VIEW_NOTIFY,
allowedAccesses={	TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT,  
	   				TAuthorizationType.NEW, TAuthorizationType.SAVE, TAuthorizationType.DELETE})
public class TNotifyMV extends TEntityModelView<TNotify> {
	
	@TTabPane(tabs = { 
		@TTab(closable=false,fields={"text"}, scroll=true, text = TUsualKey.MAIN_DATA),
		@TTab(closable=false, fields={"file"}, scroll=true, text = TUsualKey.ATTACH_FILE),
		@TTab(closable=false, fields={"eventLog"}, text = TUsualKey.EVENT_LOG)})
	private SimpleStringProperty display;

	@THBox(pane=@TPane(children={"refCode", "content"}), spacing=10, fillHeight=true,
			hgrow=@THGrow(priority={@TPriority(field="refCode", priority=Priority.ALWAYS), 
					@TPriority(field="content", priority=Priority.ALWAYS)}))
	private SimpleStringProperty text;
	
	@TLabel(text=TUsualKey.REF_CODE)
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
	
	@TLabel(text=TUsualKey.SUBJECT)
	@TTextField(maxLength=120, 
	node=@TNode(requestFocus=true, parse = true),
	required=true)
	private SimpleStringProperty subject;
	
	@TLabel(text=TUsualKey.SEND_TO)
	@TTextField(maxLength=400, required=true)
	private SimpleStringProperty to;
	
	@TLabel(text=TUsualKey.ACTION)
	@TVRadioGroup(required=true,
		converter=@TConverter(parse = true, type = ActionConverter.class),
		radio = { @TRadio(text = TUsualKey.NONE, userData = TUsualKey.NONE ),
				@TRadio(text = TUsualKey.NONE, userData = TUsualKey.SEND ),
				@TRadio(text = TUsualKey.TO_QUEUE, userData = TUsualKey.TO_QUEUE ),
				@TRadio(text = TUsualKey.TO_SCHEDULE, userData = TUsualKey.TO_SCHEDULE ) 
		})
	private SimpleObjectProperty<TAction> action;
	
	@TLabel(text=TUsualKey.SCHEDULE_DATE_TIME)
	@TDatePickerField(dateFormat=DateTimeFormatBuilder.class)
	private SimpleObjectProperty<Date> scheduleTime;
	
	@TLabel(text=TUsualKey.STATE)
	@TShowField()
	private SimpleObjectProperty<TState> state;

	@TLabel(text=TUsualKey.DATE_PROCESSED)
	@TShowField(fields= {@TField(timeStyle=TTimeStyle.SHORT)})
	private SimpleObjectProperty<Date> processedTime;
	
	@THTMLEditor(control=@TControl( maxHeight=450, parse = true))
	@TVBox(	pane=@TPane(children={"content","integratedViewName"}), spacing=10, fillWidth=true,
	vgrow=@TVGrow(priority={@TPriority(field="content", priority=Priority.ALWAYS), 
						@TPriority(field="integratedViewName", priority=Priority.ALWAYS)}))
	private SimpleStringProperty content;
	
	@TLabel(text=TFxKey.INTEGRATED_BY)
	@TShowField()
	@THBox(pane=@TPane(children={"integratedViewName", "integratedDate", "integratedModulePath"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="integratedViewName", priority=Priority.SOMETIMES), 
			@TPriority(field="integratedDate", priority=Priority.ALWAYS), 
			@TPriority(field="integratedModulePath", priority=Priority.ALWAYS)}))
	private SimpleStringProperty integratedViewName;
	
	@TLabel(text=TFxKey.INTEGRATED_DATE)
	@TShowField(fields= {@TField(timeStyle=TTimeStyle.SHORT)})
	private SimpleObjectProperty<Date> integratedDate;
	
	@TLabel(text=TFxKey.INTEGRATED_LINK)
	@TIntegratedLinkField
	private SimpleStringProperty integratedModulePath;
	
	@TFileField(propertyValueType=TFileModelType.ENTITY, preLoadFileBytes=true,
	extensions= {TFileExtension.ALL_FILES}, showFilePath=true)
	@TVBox(	pane=@TPane(children={"file"}), spacing=10, fillWidth=true,
	margin=@TMargin(values= {@TFieldInset(field="file", insets=@TInsets(top=50))}),
	vgrow=@TVGrow(priority={@TPriority(field="file", priority=Priority.ALWAYS)}))
	@TGenericType(model=TFileEntity.class)
	private TSimpleFileProperty<TFileEntity> file;
	
	@TTableView(columns = { 
		@TTableColumn(text = TUsualKey.DATE_INSERT, cellValue="insertDate", 
				cellFactory=@TCellFactory(parse = true, 
				callBack=@TCallbackFactory(parse=true, value=TMediumDateTimeCallback.class))), 
		@TTableColumn(text = TUsualKey.STATE, cellValue="state",
				cellFactory=@TCellFactory(parse = true, 
				callBack=@TCallbackFactory(parse=true, value=TNotifyLogStateCallBack.class))),
		@TTableColumn(text = TUsualKey.DESCRIPTION, cellValue="description")
	})
	@TGenericType(model = TNotifyLog.class, modelView=TNotifyLogTV.class)
	private ITObservableList<TNotifyLogTV> eventLog;
	

	public TNotifyMV(TNotify entity) {
		super(entity);
		super.formatToString(TFormatter.create()
				.add("[%s]", refCode)
				.add(" %s", subject)
				.add(" "+TUsualKey.TO+" %s", to)
				.add(processedTime, obj -> {
					Date dt = (Date) obj;
					return " "+TUsualKey.ON+" "+DateFormat
						.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, TLanguage.getLocale())
						.format(dt);
				})
			);
	}
	
	/**
	 * @return the state
	 */
	public SimpleObjectProperty<TState> getState() {
		return state;
	}

}
