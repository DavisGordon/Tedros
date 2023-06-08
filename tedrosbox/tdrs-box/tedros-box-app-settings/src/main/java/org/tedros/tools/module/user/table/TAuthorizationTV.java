/**
 * 
 */
package org.tedros.tools.module.user.table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.tedros.core.TLanguage;
import org.tedros.core.controller.TAuthorizationController;
import org.tedros.core.security.model.TAuthorization;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TLabelDefaultSetting;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.page.TPage;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.presenter.TSelectionModalPresenter;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.text.TFont;
import org.tedros.fx.presenter.modal.behavior.TSelectionModalBehavior;
import org.tedros.fx.presenter.modal.decorator.TSelectionModalDecorator;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.tools.ToolsKey;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */

@TLabelDefaultSetting(font=@TFont(size=12))
@TSelectionModalPresenter(
		paginator=@TPage(query=@TQuery(entity = TAuthorization.class), modelView=TAuthorizationTV.class, 
			serviceName = TAuthorizationController.JNDI_NAME),
		presenter=@TPresenter(behavior = @TBehavior(type = TSelectionModalBehavior.class), 
			decorator = @TDecorator(type=TSelectionModalDecorator.class, 
			viewTitle=ToolsKey.SECURITY_AUTHORIZATION_FORM_NAME)),
		tableView=@TTableView(editable=true, 
			columns = {  @TTableColumn(cellValue="securityId", text = TUsualKey.SECURITYID, prefWidth=30, resizable=true), 
						@TTableColumn(cellValue="appName", text = TUsualKey.APP, prefWidth=30, resizable=true), 
						@TTableColumn(cellValue="moduleName", text = TUsualKey.MODULE, prefWidth=40, resizable=true),
						@TTableColumn(cellValue="viewName", text = TUsualKey.VIEW, prefWidth=40, resizable=true), 
						@TTableColumn(cellValue="typeDescription", text = TUsualKey.PERMISSION,  resizable=true)}), 
		allowsMultipleSelections = true)
public final class TAuthorizationTV extends TEntityModelView<TAuthorization> {
	
	private SimpleStringProperty displayText;
	
	@TLabel(text=TUsualKey.SECURITYID)
	@TTextField(maxLength=200, required = false)
	@THBox(	pane=@TPane(children={"securityId","appName"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="securityId", priority=Priority.ALWAYS), 
		   		@TPriority(field="appName", priority=Priority.ALWAYS)}))
	private SimpleStringProperty securityId;
	
	@TLabel(text=TUsualKey.APP)
	@TTextField(maxLength=60, required = false)
	private SimpleStringProperty appName;
	
	@TLabel(text=TUsualKey.MODULE)
	@TTextField(maxLength=60, required = false)
	@THBox(	pane=@TPane(children={"moduleName","viewName"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="moduleName", priority=Priority.ALWAYS), 
			   		@TPriority(field="viewName", priority=Priority.ALWAYS)}))
	private SimpleStringProperty moduleName;
	
	@TLabel(text=TUsualKey.VIEW)
	@TTextField(maxLength=60, required = false)
	private SimpleStringProperty viewName;
	
	private SimpleStringProperty typeDescription;
	
	private SimpleStringProperty type;
	

	public TAuthorizationTV(TAuthorization entity) {
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
			TLanguage iEngine = TLanguage.getInstance(null);
			String str = (appName.getValue()==null ? "" : iEngine.getString(appName.getValue())+" / " )
					+ (moduleName.getValue()!=null ? iEngine.getString(moduleName.getValue()) +" / " : "")
					+ (viewName.getValue()!=null ? iEngine.getString(viewName.getValue()) +" / ": "")
					+ (type.getValue()!=null ? type.getValue(): "");
			displayText.setValue(str);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Observable getProperty(String f) {
		SimpleStringProperty p = (SimpleStringProperty) super.getProperty(f);
		String v = p.getValue();
		if(StringUtils.isNotBlank(v) && (f.equals("appName") || f.equals("moduleName") || f.equals("viewName"))) 
			p.setValue(TLanguage.getInstance().getString(v));
		return p;
	}
	
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.presenter.model.TModelView#getDisplayProperty()
	 */
	@Override
	public SimpleStringProperty toStringProperty() {
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

	/**
	 * @return the typeDescription
	 */
	public SimpleStringProperty getTypeDescription() {
		return typeDescription;
	}

	/**
	 * @param typeDescription the typeDescription to set
	 */
	public void setTypeDescription(SimpleStringProperty typeDescription) {
		this.typeDescription = typeDescription;
	}

	/**
	 * @return the securityId
	 */
	public SimpleStringProperty getSecurityId() {
		return securityId;
	}

	/**
	 * @param securityId the securityId to set
	 */
	public void setSecurityId(SimpleStringProperty securityId) {
		this.securityId = securityId;
	}

}
