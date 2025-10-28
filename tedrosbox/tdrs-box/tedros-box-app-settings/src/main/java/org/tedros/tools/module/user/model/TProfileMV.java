package org.tedros.tools.module.user.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.controller.TProfileController;
import org.tedros.core.domain.DomainApp;
import org.tedros.core.security.model.TAuthorization;
import org.tedros.core.security.model.TProfile;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TMultipleSelectionModal;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.control.TTextInputControl;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TVBox;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.reader.TDetailReader;
import org.tedros.fx.annotation.reader.TReader;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.entity.behavior.TMasterCrudViewBehavior;
import org.tedros.fx.presenter.entity.decorator.TMasterCrudViewDecorator;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.user.action.TProfileSaveAction;
import org.tedros.tools.module.user.table.TAuthorizationTV;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 */
@TForm(header=ToolsKey.SECURITY_PROFILE_FORM_NAME, showBreadcrumBar=true, editCssId="")
@TEjbService(serviceName = TProfileController.JNDI_NAME, model=TProfile.class)
@TPresenter(type=TDynaPresenter.class,
	model=TProfile.class,
	decorator=@TDecorator(	type=TMasterCrudViewDecorator.class, 
		viewTitle=ToolsKey.VIEW_PROFILE, 
		listTitle=ToolsKey.SECURITY_PROFILE_LIST_TITLE),
	behavior=@TBehavior(type=TMasterCrudViewBehavior.class, 
		action=TProfileSaveAction.class))
@TSecurity(	id=DomainApp.PROFILE_FORM_ID, 
	appName=ToolsKey.APP_TOOLS, 
	moduleName=ToolsKey.MODULE_USER, 
	viewName=ToolsKey.VIEW_PROFILE,
	allowedAccesses={	
			TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT,
	   		TAuthorizationType.NEW, TAuthorizationType.SAVE, TAuthorizationType.DELETE})
public final class TProfileMV extends TEntityModelView<TProfile> {
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text=ToolsKey.TEXT_PROFILE_HEADER, textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty header;
	
	@TDetailReader(label=@TLabel(text="Authorization"))
	@TMultipleSelectionModal(width=600, height=350,
	model = TAuthorization.class, modelView = TAuthorizationTV.class)
	@THBox(pane=@TPane(children= {"name","autorizations"}), 
	fillHeight=true, spacing=20)
	@TGenericType(model=TAuthorization.class, modelView=TAuthorizationTV.class)
	private ITObservableList<TAuthorizationTV> autorizations;
	
	@TReader
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=100, required=true,
		textInputControl=@TTextInputControl(
			promptText=ToolsKey.PROMPT_PROFILE_NAME, parse=true), 
		control=@TControl(minWidth=100, parse=true))
	@TVBox(pane=@TPane(children= {"name","description"}), 
	fillWidth=true, spacing=20)
	private SimpleStringProperty name;
	
	@TReader
	@TLabel(text=TUsualKey.DESCRIPTION)
	@TTextAreaField(maxLength=600, 
		textInputControl=@TTextInputControl(
			promptText=ToolsKey.PROMPT_PROFILE_DESCRIPTION, parse=true),
		prefRowCount=3)
	private SimpleStringProperty description;
	
	public TProfileMV(TProfile entity) {
		super(entity);
		super.formatToString("%s", name);
	}

}
