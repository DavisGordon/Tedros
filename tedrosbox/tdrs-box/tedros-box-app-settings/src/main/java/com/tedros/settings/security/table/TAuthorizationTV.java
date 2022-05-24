/**
 * 
 */
package com.tedros.settings.security.table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.core.TLanguage;
import com.tedros.core.ejb.controller.TAuthorizationController;
import com.tedros.core.security.model.TAuthorization;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TLabelDefaultSetting;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
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

import javafx.beans.Observable;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */

@TLabelDefaultSetting(font=@TFont(size=12))
@TSelectionModalPresenter(
		paginator=@TPaginator(entityClass = TAuthorization.class, modelViewClass=TAuthorizationTV.class, 
			serviceName = TAuthorizationController.JNDI_NAME),
		presenter=@TPresenter(behavior = @TBehavior(type = TSelectionModalBehavior.class), 
			decorator = @TDecorator(type=TSelectionModalDecorator.class, 
			viewTitle="#{security.authorization.form.name}")),
		tableView=@TTableView(editable=true, 
			columns = {  @TTableColumn(cellValue="securityId", text = "#{label.securityId}", prefWidth=30, resizable=true), 
						@TTableColumn(cellValue="appName", text = "#{label.appName}", prefWidth=30, resizable=true), 
						@TTableColumn(cellValue="moduleName", text = "#{label.moduleName}", prefWidth=40, resizable=true),
						@TTableColumn(cellValue="viewName", text = "#{label.viewName}", prefWidth=40, resizable=true), 
						@TTableColumn(cellValue="typeDescription", text = "#{label.permission}",  resizable=true)}), 
		allowsMultipleSelections = true)
public final class TAuthorizationTV extends TEntityModelView<TAuthorization> {
	
	private SimpleLongProperty id;
	
	private SimpleStringProperty displayText;
	
	@TLabel(text="#{label.securityId}")
	@THBox(	pane=@TPane(children={"securityId","appName","moduleName","viewName"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="securityId", priority=Priority.NEVER), 
		   		@TPriority(field="appName", priority=Priority.NEVER),
		   		@TPriority(field="moduleName", priority=Priority.ALWAYS), 
			   		@TPriority(field="viewName", priority=Priority.ALWAYS)}))
	private SimpleStringProperty securityId;
	
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
	
	@Override
	public Observable getProperty(String f) {
		SimpleStringProperty p = (SimpleStringProperty) super.getProperty(f);
		String v = p.getValue();
		if(StringUtils.isNotBlank(v) && (f.equals("appName") || f.equals("moduleName") || f.equals("viewName"))) 
			p.setValue(TLanguage.getInstance().getString(v));
		return p;
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
