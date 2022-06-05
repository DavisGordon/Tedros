/**
 * 
 */
package com.tedros.settings.properties.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.core.TLanguage;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.domain.DomainApp;
import com.tedros.core.ejb.controller.TAuthorizationController;
import com.tedros.core.security.model.TAuthorization;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TShowField;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.domain.TLabelPosition;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.settings.properties.action.TAuthorizationLoadAction;
import com.tedros.settings.properties.behavior.TAuthorizationBehavior;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(name = "#{security.authorization.form.name}", showBreadcrumBar=true)
@TEjbService(serviceName = TAuthorizationController.JNDI_NAME, model=TAuthorization.class)
@TListViewPresenter(listViewMinWidth=500,
	paginator=@TPaginator(entityClass = TAuthorization.class, 
	serviceName = TAuthorizationController.JNDI_NAME, show=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="#{view.authorization}", 
		 buildDeleteButton=false, buildCollapseButton=false, newButtonText="#{label.load}"),
	behavior=@TBehavior(type=TAuthorizationBehavior.class, action=TAuthorizationLoadAction.class)
	))
@TSecurity(	id=DomainApp.AUTHORIZATION_FORM_ID, 
	appName="#{app.settings}", 
	moduleName="#{module.propertie}", 
	viewName="#{view.authorization}",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
			TAuthorizationType.READ, TAuthorizationType.SAVE, TAuthorizationType.NEW})
public final class TAuthorizationMV extends TEntityModelView<TAuthorization> {
	
	private SimpleLongProperty id;
	
	private SimpleStringProperty displayText;
	
	@TReaderHtml
	@TLabel(text="#{label.securityId}")
	@THBox(	pane=@TPane(children={"securityId","appName","moduleName","viewName"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="securityId", priority=Priority.NEVER), 
		   		@TPriority(field="appName", priority=Priority.NEVER),
		   		@TPriority(field="moduleName", priority=Priority.ALWAYS), 
			   		@TPriority(field="viewName", priority=Priority.ALWAYS)}))
	@TShowField
	private SimpleStringProperty securityId;
	
	@TReaderHtml
	@TLabel(text="#{label.appName}")
	@TShowField
	private SimpleStringProperty appName;
	
	@TReaderHtml
	@TLabel(text="#{label.moduleName}")
	@TShowField
	private SimpleStringProperty moduleName;
	
	@TReaderHtml
	@TLabel(text="#{label.viewName}")
	@TShowField
	private SimpleStringProperty viewName;
	
	@TReaderHtml
	@TLabel(text="#{label.type}")
	@TShowField
	@THBox(	pane=@TPane(children={"type","typeDescription"}), spacing=10, fillHeight=true,
			hgrow=@THGrow(priority={@TPriority(field="type", priority=Priority.NEVER), 
				   		@TPriority(field="typeDescription", priority=Priority.NEVER)}))
			
	private SimpleStringProperty type;
	
	@TReaderHtml
	@TLabel(text="#{label.permission}")
	@TShowField
	private SimpleStringProperty typeDescription;
	
	@TReaderHtml
	@TLabel(text="#{label.enabled}", position=TLabelPosition.LEFT)
	@THorizontalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButtonField(text="#{label.yes}", userData="S"), 
					@TRadioButtonField(text="#{label.no}", userData="N")
	})
	private SimpleStringProperty enabled;

	public TAuthorizationMV(TAuthorization entity) {
		super(entity);
		loadDisplayText(entity);
	}
		
	@Override
	public void reload(TAuthorization model) {
		super.reload(model);
		loadDisplayText(model);
	}

	/**
	 * @param model
	 */
	private void loadDisplayText(TAuthorization model) {
		if(!model.isNew()){
			TLanguage iEngine = TLanguage.getInstance(null);
			String str = (appName.getValue()==null ? "" : iEngine.getString(appName.getValue())+" / " )
					+ (moduleName.getValue()!=null ? iEngine.getString(moduleName.getValue()) +" / " : "")
					+ (viewName.getValue()!=null ? iEngine.getString(viewName.getValue()) +" / ": "")
					+ (type.getValue()!=null ? type.getValue(): "");
			displayText.setValue(str);
		}
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

	/**
	 * @return the enabled
	 */
	public SimpleStringProperty getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(SimpleStringProperty enabled) {
		this.enabled = enabled;
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
