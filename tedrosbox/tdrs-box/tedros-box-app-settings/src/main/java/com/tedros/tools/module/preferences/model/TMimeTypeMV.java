/**
 * 
 */
package com.tedros.tools.module.preferences.model;

import com.tedros.common.model.TMimeType;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.domain.DomainApp;
import com.tedros.core.ejb.controller.TMimeTypeController;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.tools.util.ToolsKey;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */

@TFormReaderHtml
@TForm(name = "", showBreadcrumBar=true, scroll=false)
@TEjbService(serviceName = TMimeTypeController.JNDI_NAME, model=TMimeType.class)
@TListViewPresenter(
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=ToolsKey.VIEW_MIMETYPE, buildImportButton=true),
	behavior=@TBehavior(importModelViewClass=TMimeTypeImportMV.class, runNewActionAfterSave=true)))
@TSecurity(	id=DomainApp.MIMETYPE_FORM_ID,  
	appName=ToolsKey.APP_TOOLS, 
	moduleName=ToolsKey.MODULE_PREFERENCES, 
	viewName=ToolsKey.VIEW_MIMETYPE,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})

public class TMimeTypeMV extends TEntityModelView<TMimeType> {
	
	private SimpleLongProperty id;
	
	@TReaderHtml
	@TLabel(text=ToolsKey.MIMETYPE_EXT)
	@TTextField(maxLength=10, 
	node=@TNode(requestFocus=true, parse = true),
	required=true)
	private SimpleStringProperty extension;
	
	@TReaderHtml
	@TLabel(text=ToolsKey.MIMETYPE_TYPE)
	@TTextField(maxLength=500, required=true)
	private SimpleStringProperty type;
	
	@TReaderHtml
	@TLabel(text=ToolsKey.DESCRIPTION)
	@TTextAreaField(maxLength=500, wrapText=true, prefRowCount=4)
	private SimpleStringProperty description;

	public TMimeTypeMV(TMimeType entity) {
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
	 * @return the extension
	 */
	public SimpleStringProperty getExtension() {
		return extension;
	}

	/**
	 * @param extension the extension to set
	 */
	public void setExtension(SimpleStringProperty extension) {
		this.extension = extension;
	}

	/**
	 * @return the type
	 */
	public SimpleStringProperty getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(SimpleStringProperty type) {
		this.type = type;
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

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return extension;
	}

}
