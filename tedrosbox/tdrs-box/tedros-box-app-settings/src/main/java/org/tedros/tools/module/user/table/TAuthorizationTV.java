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
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TLabelDefaultSetting;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.control.TTextInputControl;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.presenter.TSelectionModalPresenter;
import org.tedros.fx.annotation.reader.TReader;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.text.TFont;
import org.tedros.fx.annotation.view.TPaginator;
import org.tedros.fx.presenter.modal.behavior.TSelectionModalBehavior;
import org.tedros.fx.presenter.modal.decorator.TSelectionModalDecorator;
import org.tedros.fx.presenter.model.TEntityModelView;

import javafx.beans.Observable;
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
	 * @see com.tedros.fxapi.presenter.model.TModelView#getDisplayProperty()
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
