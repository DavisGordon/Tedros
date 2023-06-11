/**
 * 
 */
package org.tedros.tools.module.preferences.model;

import org.tedros.common.model.TMimeType;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.controller.TMimeTypeController;
import org.tedros.core.domain.DomainApp;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.reader.TFormReaderHtml;
import org.tedros.fx.annotation.reader.TReaderHtml;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.tools.ToolsKey;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */

@TFormReaderHtml
@TForm(name = "", showBreadcrumBar=false, scroll=false)
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
	
	@TReaderHtml
	@TLabel(text=TUsualKey.MIMETYPE_EXT)
	@TTextField(maxLength=10, 
	node=@TNode(requestFocus=true, parse = true),
	required=true)
	private SimpleStringProperty extension;
	
	@TReaderHtml
	@TLabel(text=TUsualKey.MIMETYPE_TYPE)
	@TTextField(maxLength=500, required=true)
	private SimpleStringProperty type;
	
	@TReaderHtml
	@TLabel(text=TUsualKey.DESCRIPTION)
	@TTextAreaField(maxLength=500, wrapText=true, prefRowCount=4)
	private SimpleStringProperty description;

	public TMimeTypeMV(TMimeType entity) {
		super(entity);
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
	public SimpleStringProperty toStringProperty() {
		return extension;
	}

}
