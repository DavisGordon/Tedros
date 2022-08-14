/**
 * 
 */
package org.tedros.tools.module.user.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.tedros.core.TLanguage;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.controller.TAuthorizationController;
import org.tedros.core.domain.DomainApp;
import org.tedros.core.security.model.TAuthorization;
import org.tedros.fx.annotation.control.THorizontalRadioGroup;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TRadioButton;
import org.tedros.fx.annotation.control.TShowField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.reader.TFormReaderHtml;
import org.tedros.fx.annotation.reader.TReaderHtml;
import org.tedros.fx.annotation.view.TPaginator;
import org.tedros.fx.domain.TLabelPosition;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.user.action.TAuthorizationLoadAction;
import org.tedros.tools.module.user.behaviour.TAuthorizationBehavior;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(name = ToolsKey.SECURITY_AUTHORIZATION_FORM_NAME, showBreadcrumBar=true)
@TEjbService(serviceName = TAuthorizationController.JNDI_NAME, model=TAuthorization.class)
@TListViewPresenter(listViewMinWidth=500,
	paginator=@TPaginator(entityClass = TAuthorization.class, 
	serviceName = TAuthorizationController.JNDI_NAME, show=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=ToolsKey.VIEW_AUTHORIZATION, 
		 buildDeleteButton=false, buildCollapseButton=false, newButtonText=ToolsKey.LOAD),
	behavior=@TBehavior(type=TAuthorizationBehavior.class, action=TAuthorizationLoadAction.class)
	))
@TSecurity(	id=DomainApp.AUTHORIZATION_FORM_ID, 
	appName=ToolsKey.APP_TOOLS, 
	moduleName=ToolsKey.MODULE_USER, 
	viewName=ToolsKey.VIEW_AUTHORIZATION,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
			TAuthorizationType.READ, TAuthorizationType.SAVE, TAuthorizationType.NEW})
public final class TAuthorizationMV extends TEntityModelView<TAuthorization> {
	
	private SimpleLongProperty id;
	
	private SimpleStringProperty displayText;
	
	@TReaderHtml
	@TLabel(text=ToolsKey.SECURITYID)
	@THBox(	pane=@TPane(children={"securityId","appName","moduleName","viewName"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="securityId", priority=Priority.NEVER), 
		   		@TPriority(field="appName", priority=Priority.NEVER),
		   		@TPriority(field="moduleName", priority=Priority.ALWAYS), 
			   		@TPriority(field="viewName", priority=Priority.ALWAYS)}))
	@TShowField
	private SimpleStringProperty securityId;
	
	@TReaderHtml
	@TLabel(text=ToolsKey.APPNAME)
	@TShowField
	private SimpleStringProperty appName;
	
	@TReaderHtml
	@TLabel(text=ToolsKey.MODULENAME)
	@TShowField
	private SimpleStringProperty moduleName;
	
	@TReaderHtml
	@TLabel(text=ToolsKey.VIEWNAME)
	@TShowField
	private SimpleStringProperty viewName;
	
	@TReaderHtml
	@TLabel(text=ToolsKey.TYPE)
	@TShowField
	@THBox(	pane=@TPane(children={"type","typeDescription"}), spacing=10, fillHeight=true,
			hgrow=@THGrow(priority={@TPriority(field="type", priority=Priority.NEVER), 
				   		@TPriority(field="typeDescription", priority=Priority.NEVER)}))
			
	private SimpleStringProperty type;
	
	@TReaderHtml
	@TLabel(text=ToolsKey.PERMISSION)
	@TShowField
	private SimpleStringProperty typeDescription;
	
	@TReaderHtml
	@TLabel(text=ToolsKey.ENABLED, position=TLabelPosition.LEFT)
	@THorizontalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButton(text=ToolsKey.YES, userData="S"), 
					@TRadioButton(text=ToolsKey.NO, userData="N")
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
