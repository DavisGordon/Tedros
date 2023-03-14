/**
 * 
 */
package org.tedros.tools.module.ai.model;

import org.tedros.core.ai.model.TAiCreateImage;
import org.tedros.core.ai.model.TRequestEvent;
import org.tedros.core.ai.model.TResponseFormat;
import org.tedros.core.ai.model.image.TImageSize;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.controller.TAiCreateImageController;
import org.tedros.core.security.model.TUser;
import org.tedros.fx.TFxKey;
import org.tedros.fx.annotation.control.TButtonField;
import org.tedros.fx.annotation.control.TCallbackFactory;
import org.tedros.fx.annotation.control.TCellFactory;
import org.tedros.fx.annotation.control.TContent;
import org.tedros.fx.annotation.control.TConverter;
import org.tedros.fx.annotation.control.THorizontalRadioGroup;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TModelViewType;
import org.tedros.fx.annotation.control.TNumberSpinnerField;
import org.tedros.fx.annotation.control.TRadioButton;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextInputControl;
import org.tedros.fx.annotation.form.TDetailForm;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.form.TSetting;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.layout.TToolBar;
import org.tedros.fx.annotation.layout.TVBox;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.scene.control.TLabeled;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.annotation.view.TPaginator;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.control.tablecell.TMediumDateTimeCallback;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.ai.TImageSizeConverter;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Orientation;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TSetting(CreateImageSetting.class)
@TForm(name = "", showBreadcrumBar=false)
@TEjbService(serviceName = TAiCreateImageController.JNDI_NAME, model=TAiCreateImage.class)
@TListViewPresenter(listViewMinWidth=500,
	paginator=@TPaginator(entityClass = TAiCreateImage.class, 
	serviceName = TAiCreateImageController.JNDI_NAME, show=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=ToolsKey.VIEW_AI_CREATE_IMAGE)
	))
public class CreateImageMV extends TEntityModelView<TAiCreateImage> {


	@TTabPane(tabs = { 
		@TTab(closable=false, text = ToolsKey.MAIN_DATA, 
				content = @TContent(detailForm=@TDetailForm(
						fields={"id"})),
			scroll=true),
		@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"events"})),
			text = ToolsKey.EVENT_LOG)
	}, region=@TRegion(minHeight=400, maxHeight=400, parse = true))
	private SimpleStringProperty title;
	
	@TVBox(fillWidth=true, spacing=20)
	private SimpleLongProperty id;

	@THorizontalRadioGroup(radioButtons = { 
			@TRadioButton(text = "256 x 256", userData = "256x256"), 
			@TRadioButton(text = "512 x 512", userData = "512x512"), 
			@TRadioButton(text = "1024 x 1024", userData = "1024x1024"), 
		}, converter=@TConverter(parse = true, type = TImageSizeConverter.class))
	@THBox(pane=@TPane(children={"size", "quantity"}), spacing=10, fillHeight=false,
	hgrow=@THGrow(priority={@TPriority(field="quantity", priority=Priority.NEVER), 
			@TPriority(field="size", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<TImageSize> size;
	
	@TLabel(text="Quantidade")
	@TNumberSpinnerField(maxValue = 10, minValue=1, 
			control=@TControl(parse = true, tooltip=ToolsKey.TEXT_MAX_TOKENS))
	private SimpleIntegerProperty quantity;
	
	@TTextAreaField(wrapText=true, prefRowCount=4, textInputControl=
			@TTextInputControl(parse = true, 
			promptText=ToolsKey.PROMPT_AI_PROMPT), 
			control=@TControl(parse = true, tooltip=ToolsKey.TEXT_PROMPT))
	private SimpleStringProperty prompt;
	
	@TToolBar(items = {"sendBtn", "ClearBtn" }, orientation=Orientation.HORIZONTAL)
	@TButtonField(labeled = @TLabeled(parse = true, text=TFxKey.BUTTON_SEND))
	private SimpleStringProperty sendBtn;

	@TButtonField(labeled = @TLabeled(parse = true, text=TFxKey.BUTTON_CLEAN))
	private SimpleStringProperty clearBtn;
	
	@TTableView(columns = { 
		@TTableColumn(text = ToolsKey.DATE_INSERT, cellValue="insertDate", 
				cellFactory=@TCellFactory(parse = true, 
				callBack=@TCallbackFactory(parse=true, value=TMediumDateTimeCallback.class))), 
		@TTableColumn(text = ToolsKey.PROMPT, cellValue="prompt"),
		@TTableColumn(text = ToolsKey.EVENT_LOG, cellValue="log")
	})
	@TModelViewType(modelClass = TRequestEvent.class, modelViewClass=EventMV.class)
	private ITObservableList<EventMV> events;
	
	public CreateImageMV(TAiCreateImage m) {
		super(m);
		m.setFormat(TResponseFormat.BASE64);
		if(m.isNew()) {
			m.setQuantity(1);
			if(m.getUser()==null) {
				TUser u = TedrosContext.getLoggedUser();
				m.setUser(u.getName());
				m.setUserId(u.getId());
			}
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
	 * @return the quantity
	 */
	public SimpleIntegerProperty getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(SimpleIntegerProperty quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the size
	 */
	public SimpleObjectProperty<TImageSize> getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(SimpleObjectProperty<TImageSize> size) {
		this.size = size;
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
