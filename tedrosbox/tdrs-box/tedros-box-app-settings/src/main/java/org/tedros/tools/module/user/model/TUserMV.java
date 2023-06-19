/**
 * 
 */
package org.tedros.tools.module.user.model;

import org.tedros.api.presenter.view.TViewMode;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.controller.TProfileController;
import org.tedros.core.controller.TUserController;
import org.tedros.core.domain.DomainApp;
import org.tedros.core.security.model.TProfile;
import org.tedros.core.security.model.TUser;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.TObservableValue;
import org.tedros.fx.annotation.control.TCheckBoxField;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TPasswordField;
import org.tedros.fx.annotation.control.TPickListField;
import org.tedros.fx.annotation.control.TProcess;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.property.TReadOnlyBooleanProperty;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.reader.TFormReaderHtml;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TLabeled;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.domain.TLabelPosition;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.entity.behavior.TMasterCrudViewBehavior;
import org.tedros.fx.presenter.entity.decorator.TMasterCrudViewDecorator;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.user.action.TEncriptPasswordChangeListener;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(header=ToolsKey.SECURITY_USER_FORM_NAME, showBreadcrumBar=true, editCssId="")
@TEjbService(serviceName = TUserController.JNDI_NAME, model=TUser.class)
@TPresenter(type=TDynaPresenter.class, 
			decorator=@TDecorator(type = TMasterCrudViewDecorator.class, 
			viewTitle=ToolsKey.VIEW_USER, listTitle=ToolsKey.SECURITY_USER_LIST_TITLE),
			behavior=@TBehavior(type=TMasterCrudViewBehavior.class))
@TSecurity(	id=DomainApp.USER_FORM_ID, 
			appName=ToolsKey.APP_TOOLS, 
			moduleName=ToolsKey.MODULE_USER, 
			viewName=ToolsKey.VIEW_USER,
			allowedAccesses={	
				TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT,
			   	TAuthorizationType.NEW, TAuthorizationType.SAVE, TAuthorizationType.DELETE})
public class TUserMV extends TEntityModelView<TUser> {
	
	@TTabPane(tabs = { 
		@TTab(closable=false, scroll=false, text = TUsualKey.MAIN,
			fields={"header", "name", "login", "active"}), 
		@TTab(closable=false, fields={"profilesText","profiles"}, 
			text = TUsualKey.DETAIL)})
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id=TFieldBox.INFO, parse = true))
	@TText(text=ToolsKey.TEXT_USER_HEADER, textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty header;
	
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=100, required=true)
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.LOGIN)
	@TTextField(maxLength=100, required=true)
	@THBox(	pane=@TPane(children={"login","password"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="login", priority=Priority.NEVER), 
		   		@TPriority(field="password", priority=Priority.NEVER)}))
	private SimpleStringProperty login;
	
	@TLabel(text=TUsualKey.PASSWORD)
	@TPasswordField(required=true, 
		node=@TNode(focusedProperty=@TReadOnlyBooleanProperty(
				observableValue=@TObservableValue(addListener=TEncriptPasswordChangeListener.class), 
				parse = true), 
		parse = true))
	private SimpleStringProperty password;
	
	@TLabel(text=TUsualKey.ACTIVE, position=TLabelPosition.LEFT)
	@TCheckBoxField(labeled=@TLabeled(text=TUsualKey.YES, parse = true))
	private SimpleBooleanProperty active;
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id=TFieldBox.INFO, parse = true))
	@TText(text=ToolsKey.TEXT_PROFILE_HEADER, textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE, mode=TViewMode.EDIT)
	private SimpleStringProperty profilesText;
	
	@TPickListField(targetLabel=TUsualKey.SELECTED, 
			sourceLabel=TUsualKey.PROFILES, required=true,
			process=@TProcess(service = TProfileController.JNDI_NAME,
					modelView=TProfileMV.class, query=@TQuery(entity=TProfile.class)))
	@TGenericType(model=TProfile.class, modelView=TProfileMV.class)
	private ITObservableList<TProfileMV> profiles;
	
	private SimpleStringProperty lastPassword;
	
	public TUserMV(TUser entity) {
		super(entity);
		copyPassword();
		super.formatToString("%s", name);
	}

	private void copyPassword() {
		if(password!=null && password.getValue()!=null)
			lastPassword.setValue(password.getValue());
	}
	
	@Override
	public void reload(TUser model) {
		super.reload(model);
		copyPassword();
	}

	/**
	 * @return the password
	 */
	public SimpleStringProperty getPassword() {
		return password;
	}

	/**
	 * @return the lastPassword
	 */
	public SimpleStringProperty getLastPassword() {
		return lastPassword;
	}
}
