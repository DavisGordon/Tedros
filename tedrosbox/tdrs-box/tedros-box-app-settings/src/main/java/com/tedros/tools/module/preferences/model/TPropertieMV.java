/**
 * 
 */
package com.tedros.tools.module.preferences.model;

import com.tedros.common.model.TFileEntity;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.domain.DomainApp;
import com.tedros.core.ejb.controller.TPropertieController;
import com.tedros.core.setting.model.TPropertie;
import com.tedros.fxapi.annotation.control.TFileField;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewType;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.layout.TVBox;
import com.tedros.fxapi.annotation.layout.TVGrow;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.domain.TFileExtension;
import com.tedros.fxapi.domain.TFileModelType;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TMasterCrudViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TMasterCrudViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.property.TSimpleFileProperty;
import com.tedros.tools.ToolsKey;
import com.tedros.tools.module.preferences.action.ReloadPropertiesAction;

import javafx.beans.property.SimpleLongProperty;
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
	private SimpleLongProperty id;
	
	@TReaderHtml
	@TLabel(text=ToolsKey.NAME)
	@TTextField(maxLength=40, required=true)
	@TVBox(	pane=@TPane(children={"name","key"}), spacing=10, fillWidth=true,
	vgrow=@TVGrow(priority={@TPriority(field="name", priority=Priority.ALWAYS), 
						@TPriority(field="key", priority=Priority.ALWAYS)}))
	private SimpleStringProperty name;
	
	@TReaderHtml
	@TLabel(text=ToolsKey.KEY)
	@TTextField(maxLength=25, required=true)
	private SimpleStringProperty key;
	
	@TReaderHtml
	@TLabel(text=ToolsKey.DESCRIPTION)
	@TTextAreaField(maxLength=500, wrapText=true, prefRowCount=2)
	@TVBox(	pane=@TPane(children={"description", "value"}), spacing=10, fillWidth=true,
	vgrow=@TVGrow(priority={@TPriority(field="value", priority=Priority.ALWAYS), 
						@TPriority(field="description", priority=Priority.ALWAYS)}))
	private SimpleStringProperty description;
	
	@TReaderHtml
	@TLabel(text=ToolsKey.VALUE)
	@TTextAreaField(wrapText=true, prefRowCount=2)
	private SimpleStringProperty value;
	
	@TLabel(text=ToolsKey.FILE)
	@TFileField(propertyValueType=TFileModelType.ENTITY, preLoadFileBytes=true,
	extensions= {TFileExtension.ALL_FILES}, showFilePath=true, showImage=true)
	@TModelViewType(modelClass=TFileEntity.class)
	private TSimpleFileProperty<TFileEntity> file;
	
	
	public TPropertieMV(TPropertie entity) {
		super(entity);
	}


	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.model.TModelView#setId(javafx.beans.property.SimpleLongProperty)
	 */
	@Override
	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.model.TModelView#getId()
	 */
	@Override
	public SimpleLongProperty getId() {
		return id;
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
	public SimpleStringProperty getDisplayProperty() {
		return name;
	}

}
