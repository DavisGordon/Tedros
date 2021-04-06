/**
 * 
 */
package com.tedros.settings.security.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.security.model.TAuthorization;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TLabelDefaultSetting;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.presenter.TSelectionModalPresenter;
import com.tedros.fxapi.annotation.reader.TReader;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.presenter.modal.behavior.TSelectionModalBehavior;
import com.tedros.fxapi.presenter.modal.decorator.TSelectionModalDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */

@TLabelDefaultSetting(font=@TFont(size=12))
@TSelectionModalPresenter(
		paginator=@TPaginator(entityClass = TAuthorization.class, modelViewClass=TAuthorizationTableView.class, 
			serviceName = "TAuthorizationControllerRemote"),
		presenter=@TPresenter(behavior = @TBehavior(type = TSelectionModalBehavior.class), 
			decorator = @TDecorator(type=TSelectionModalDecorator.class, 
			viewTitle="#{security.authorization.form.name}")),
		tableView=@TTableView(editable=true, 
			columns = { @TTableColumn(cellValue="appName", text = "#{label.appName}", prefWidth=30, resizable=true), 
						@TTableColumn(cellValue="moduleName", text = "#{label.moduleName}", prefWidth=40, resizable=true),
						@TTableColumn(cellValue="viewName", text = "#{label.viewName}", prefWidth=40, resizable=true), 
						@TTableColumn(cellValue="typeDescription", text = "#{label.permission}",  resizable=true)}), 
		allowsMultipleSelections = true)
public final class TAuthorizationTableView extends TEntityModelView<TAuthorization> {
	
	private SimpleLongProperty id;
	
	private SimpleStringProperty displayText;
	
	@TReader
	@TLabel(text="#{label.appName}")
	@TTextField(maxLength=60, required = false, 
	textInputControl=@TTextInputControl(promptText="#{label.appName}", parse = true), 
	control=@TControl(tooltip="#{label.appName}", parse = true))
	private SimpleStringProperty appName;
	
	@TReader
	@TLabel(text="#{label.moduleName}")
	@TTextField(maxLength=60, required = false, 
	textInputControl=@TTextInputControl(promptText="#{label.moduleName}", parse = true), 
	control=@TControl(tooltip="#{label.moduleName}", parse = true))
	private SimpleStringProperty moduleName;
	
	@TReader
	@TLabel(text="#{label.viewName}")
	@TTextField(maxLength=60, required = false, 
	textInputControl=@TTextInputControl(promptText="#{label.viewName}", parse = true), 
	control=@TControl(tooltip="#{label.viewName}", parse = true))
	private SimpleStringProperty viewName;
	
	private SimpleStringProperty type;
	

	public TAuthorizationTableView(TAuthorization entity) {
		super(entity);
		loadDisplayText(model);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	public void reload(TAuthorization model) {
		super.reload(model);
		loadDisplayText(model);
	}

	/**
	 * @param model
	 */
	private void loadDisplayText(TAuthorization model) {
		if(!model.isNew()){
			TInternationalizationEngine iEngine = TInternationalizationEngine.getInstance(null);
			String str = (appName.getValue()==null ? "" : iEngine.getString(appName.getValue())+" / " )
					+ (moduleName.getValue()!=null ? iEngine.getString(moduleName.getValue()) +" / " : "")
					+ (viewName.getValue()!=null ? iEngine.getString(viewName.getValue()) +" / ": "")
					+ (type.getValue()!=null ? type.getValue(): "");
			displayText.setValue(str);
		}
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

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.model.TModelView#getDisplayProperty()
	 */
	@Override
	public SimpleStringProperty getDisplayProperty() {
		return this.displayText;
	}


	public SimpleStringProperty getAppName() {
		return appName;
	}


	public void setAppName(SimpleStringProperty appName) {
		this.appName = appName;
	}


	public SimpleStringProperty getModuleName() {
		return moduleName;
	}


	public void setModuleName(SimpleStringProperty moduleName) {
		this.moduleName = moduleName;
	}


	public SimpleStringProperty getType() {
		return type;
	}

	public void setType(SimpleStringProperty type) {
		this.type = type;
	}

	public SimpleStringProperty getViewName() {
		return viewName;
	}

	public void setViewName(SimpleStringProperty viewName) {
		this.viewName = viewName;
	}

	/**
	 * @return the displayText
	 */
	public SimpleStringProperty getDisplayText() {
		return displayText;
	}

	/**
	 * @param displayText the displayText to set
	 */
	public void setDisplayText(SimpleStringProperty displayText) {
		this.displayText = displayText;
	}

}
