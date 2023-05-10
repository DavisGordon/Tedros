/**
 * 
 */
package org.tedros.tools.module.ai.model;

import org.tedros.common.model.TFileEntity;
import org.tedros.core.ai.model.TAiCreateImage;
import org.tedros.core.ai.model.TRequestEvent;
import org.tedros.core.ai.model.TResponseFormat;
import org.tedros.core.ai.model.image.TImageSize;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.controller.TAiCreateImageController;
import org.tedros.core.domain.DomainApp;
import org.tedros.core.security.model.TUser;
import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
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
import org.tedros.fx.annotation.control.TValidator;
import org.tedros.fx.annotation.form.TDetailForm;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.form.TSetting;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.layout.TToolBar;
import org.tedros.fx.annotation.layout.TVBox;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.scene.control.TLabeled;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.annotation.view.TPaginator;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.control.tablecell.TMediumDateTimeCallback;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.ai.converter.TImageSizeConverter;
import org.tedros.tools.module.ai.settings.CreateImageSetting;
import org.tedros.tools.module.ai.validator.CreateImageValidator;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TSetting(CreateImageSetting.class)
@TForm(name = "", showBreadcrumBar=false)
@TEjbService(serviceName = TAiCreateImageController.JNDI_NAME, model=TAiCreateImage.class)
@TListViewPresenter(listViewMinWidth=300,
	paginator=@TPaginator(entityClass = TAiCreateImage.class, 
	serviceName = TAiCreateImageController.JNDI_NAME, show=true),
	presenter=@TPresenter(
			decorator = @TDecorator(viewTitle=ToolsKey.VIEW_AI_CREATE_IMAGE),
			behavior=@TBehavior(saveAllModels=false)
	))
@TSecurity(id=DomainApp.CR_IMAGE_TEROS_FORM_ID,
appName=ToolsKey.APP_TOOLS, moduleName=ToolsKey.MODULE_AI, viewName=ToolsKey.VIEW_AI_CREATE_IMAGE,
allowedAccesses={	TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT,  
	   				TAuthorizationType.NEW, TAuthorizationType.SAVE, TAuthorizationType.DELETE})
public class CreateImageMV extends TEntityModelView<TAiCreateImage> {


	@TTabPane(tabs = { 
		@TTab(closable=false, text = ToolsKey.VIEW_AI_CREATE_IMAGE, 
				content = @TContent(detailForm=@TDetailForm(
						fields={"images"})),
			scroll=true),
		@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"events"})),
			text = TUsualKey.EVENT_LOG)
	}, region=@TRegion(minHeight=380, maxHeight=380, parse = true))
	private SimpleStringProperty title;
	
	@TValidator(validatorClass = CreateImageValidator.class)
	@TVBox(fillWidth=true, spacing=20, alignment=Pos.TOP_CENTER)
	@TModelViewType(modelClass = TFileEntity.class)
	private ITObservableList<TFileEntity> images;

	@TLabel(text=TUsualKey.SIZE)
	@THorizontalRadioGroup(spacing=10, radioButtons = { 
			@TRadioButton(text = "256 x 256", userData = "256x256"), 
			@TRadioButton(text = "512 x 512", userData = "512x512"), 
			@TRadioButton(text = "1024 x 1024", userData = "1024x1024"), 
		}, converter=@TConverter(parse = true, type = TImageSizeConverter.class))
	@THBox(pane=@TPane(children={"size", "quantity"}), spacing=10, fillHeight=false,
	hgrow=@THGrow(priority={@TPriority(field="quantity", priority=Priority.NEVER), 
			@TPriority(field="size", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<TImageSize> size;
	
	@TLabel(text=TUsualKey.QUANTITY)
	@TNumberSpinnerField(maxValue = 10, minValue=1)
	private SimpleIntegerProperty quantity;
	
	@TTextAreaField(wrapText=true, prefRowCount=2, textInputControl=
			@TTextInputControl(parse = true, 
			promptText=ToolsKey.PROMPT_AI_CREATE_IMAGE))
	private SimpleStringProperty prompt;
	
	@TToolBar(items = {"sendBtn", "clearBtn" }, orientation=Orientation.HORIZONTAL)
	@TButtonField(labeled = @TLabeled(parse = true, text=TFxKey.BUTTON_SEND))
	private SimpleStringProperty sendBtn;

	@TButtonField(labeled = @TLabeled(parse = true, text=TFxKey.BUTTON_CLEAN))
	private SimpleStringProperty clearBtn;
	
	@TTableView(columns = { 
		@TTableColumn(text = TUsualKey.DATE_INSERT, cellValue="insertDate", 
				cellFactory=@TCellFactory(parse = true, 
				callBack=@TCallbackFactory(parse=true, value=TMediumDateTimeCallback.class))), 
		@TTableColumn(text = TUsualKey.PROMPT, cellValue="prompt"),
		@TTableColumn(text = TUsualKey.EVENT_LOG, cellValue="log")
	})
	@TModelViewType(modelClass = TRequestEvent.class, modelViewClass=EventMV.class)
	private ITObservableList<EventMV> events;
	
	public CreateImageMV(TAiCreateImage m) {
		super(m);
		m.setFormat(TResponseFormat.BASE64);
		if(m.isNew()) {
			if(m.getUser()==null) {
				TUser u = TedrosContext.getLoggedUser();
				m.setUser(u.getName());
				m.setUserId(u.getId());
			}
		}
		super.formatToString("%s", title);
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

	/**
	 * @return the images
	 */
	public ITObservableList<TFileEntity> getImages() {
		return images;
	}

	/**
	 * @param images the images to set
	 */
	public void setImages(ITObservableList<TFileEntity> images) {
		this.images = images;
	}

}
