/**
 * 
 */
package org.tedros.tools.module.preferences.model;

import org.tedros.common.model.TFileEntity;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.controller.TPropertieController;
import org.tedros.core.domain.DomainApp;
import org.tedros.core.setting.model.TPropertie;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TFileField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TModelViewType;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.layout.TVBox;
import org.tedros.fx.annotation.layout.TVGrow;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.reader.TFormReaderHtml;
import org.tedros.fx.annotation.reader.TReaderHtml;
import org.tedros.fx.domain.TFileExtension;
import org.tedros.fx.domain.TFileModelType;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.entity.behavior.TMasterCrudViewBehavior;
import org.tedros.fx.presenter.entity.decorator.TMasterCrudViewDecorator;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.fx.property.TSimpleFileProperty;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.preferences.action.ReloadPropertiesAction;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(name="",  editCssId="")
@TEjbService(serviceName = TPropertieController.JNDI_NAME, model=TPropertie.class)
@TPresenter(type=TDynaPresenter.class, 
			decorator=@TDecorator(type = TMasterCrudViewDecorator.class, 
			viewTitle=ToolsKey.VIEW_PROPERTIE),
			behavior=@TBehavior(type=TMasterCrudViewBehavior.class, 
			action=ReloadPropertiesAction.class))
@TSecurity(	id=DomainApp.PROPERTIE_FORM_ID, 
			appName=ToolsKey.APP_TOOLS, 
			moduleName=ToolsKey.MODULE_PREFERENCES, 
			viewName=ToolsKey.VIEW_PROPERTIE,
			allowedAccesses={	TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
			   					TAuthorizationType.NEW, TAuthorizationType.SAVE, TAuthorizationType.DELETE})
public class TPropertieMV extends TEntityModelView<TPropertie> {

	@THBox(	pane=@TPane(children={"name","description"}), spacing=10, fillHeight=true,
			hgrow=@THGrow(priority={@TPriority(field="name", priority=Priority.ALWAYS), 
								@TPriority(field="description", priority=Priority.ALWAYS)}))
	private SimpleStringProperty header;
	
	@TReaderHtml
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=40, required=true)
	@TVBox(	pane=@TPane(children={"name","key"}), spacing=10, fillWidth=true,
	vgrow=@TVGrow(priority={@TPriority(field="name", priority=Priority.ALWAYS), 
						@TPriority(field="key", priority=Priority.ALWAYS)}))
	private SimpleStringProperty name;
	
	@TReaderHtml
	@TLabel(text=TUsualKey.KEY)
	@TTextField(maxLength=25, required=true)
	private SimpleStringProperty key;
	
	@TReaderHtml
	@TLabel(text=TUsualKey.DESCRIPTION)
	@TTextAreaField(maxLength=500, wrapText=true, prefRowCount=2)
	@TVBox(	pane=@TPane(children={"description", "value"}), spacing=10, fillWidth=true,
	vgrow=@TVGrow(priority={@TPriority(field="value", priority=Priority.ALWAYS), 
						@TPriority(field="description", priority=Priority.ALWAYS)}))
	private SimpleStringProperty description;
	
	@TReaderHtml
	@TLabel(text=TUsualKey.VALUE)
	@TTextAreaField(wrapText=true, prefRowCount=2)
	private SimpleStringProperty value;
	
	@TLabel(text=TUsualKey.FILE)
	@TFileField(propertyValueType=TFileModelType.ENTITY, preLoadFileBytes=true,
	extensions= {TFileExtension.ALL_FILES}, showFilePath=true, showImage=true)
	@TModelViewType(modelClass=TFileEntity.class)
	private TSimpleFileProperty<TFileEntity> file;
	
	
	public TPropertieMV(TPropertie entity) {
		super(entity);
	}

	/**
	 * @return the name
	 */
	public SimpleStringProperty getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	/**
	 * @return the key
	 */
	public SimpleStringProperty getKey() {
		return key;
	}
	
	/**
	 * @param key the key to set
	 */
	public void setKey(SimpleStringProperty key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public SimpleStringProperty getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(SimpleStringProperty value) {
		this.value = value;
	}

	/**
	 * @return the description
	 */
	public SimpleStringProperty getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(SimpleStringProperty description) {
		this.description = description;
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

	@Override
	public SimpleStringProperty toStringProperty() {
		return name;
	}

	/**
	 * @return the header
	 */
	public SimpleStringProperty getHeader() {
		return header;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(SimpleStringProperty header) {
		this.header = header;
	}

}
