/**
 * 
 */
package com.tedros.settings.security.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.core.security.model.TAuthorization;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.reader.TReader;
import com.tedros.fxapi.presenter.model.TEntityModelView;

/**
 * @author Davis Gordon
 *
 */
public final class TAuthorizationTableView extends TEntityModelView<TAuthorization> {
	
	private SimpleLongProperty id;
	
	@TReader
	@TLabel(text="#{label.securityId}")
	private SimpleStringProperty securityId;
	
	@TReader
	@TLabel(text="#{label.appName}")
	private SimpleStringProperty appName;
	
	@TReader
	@TLabel(text="#{label.moduleName}")
	private SimpleStringProperty moduleName;
	
	@TReader
	@TLabel(text="#{label.viewName}")
	private SimpleStringProperty viewName;
	
	private SimpleStringProperty type;
	
	@TReader
	@TLabel(text="#{label.permission}")
	private SimpleStringProperty typeDescription;
	
	@TReader(label=@TLabel(text="#{label.enabled}"))
	private SimpleBooleanProperty enabled;

	public TAuthorizationTableView(TAuthorization entity) {
		super(entity);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
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
		return type;
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

	public SimpleBooleanProperty getEnabled() {
		return enabled;
	}
	
	public SimpleBooleanProperty enabledProperty() {
		return enabled;
	}


	public void setEnabled(SimpleBooleanProperty enabled) {
		this.enabled = enabled;
	}
	
	public void setEnabled(Boolean enabled) {
		this.enabled.setValue(enabled);
	}

	public SimpleStringProperty getSecurityId() {
		return securityId;
	}

	public void setSecurityId(SimpleStringProperty securityId) {
		this.securityId = securityId;
	}

	public SimpleStringProperty getType() {
		return type;
	}

	public void setType(SimpleStringProperty type) {
		this.type = type;
	}

	public SimpleStringProperty getTypeDescription() {
		return typeDescription;
	}

	public void setTypeDescription(SimpleStringProperty typeDescription) {
		this.typeDescription = typeDescription;
	}

	public SimpleStringProperty getViewName() {
		return viewName;
	}

	public void setViewName(SimpleStringProperty viewName) {
		this.viewName = viewName;
	}

}
