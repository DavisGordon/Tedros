/**
 * 
 */
package org.tedros.tools.module.user.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.controller.TAuthorizationController;
import org.tedros.core.domain.DomainApp;
import org.tedros.core.security.model.TAuthorization;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TShowField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.page.TPage;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TOrder;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.reader.TFormReaderHtml;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.fx.model.TFormatter;
import org.tedros.server.query.TCompareOp;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.user.action.TAuthorizationLoadAction;
import org.tedros.tools.module.user.behaviour.TAuthorizationBehavior;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(header = ToolsKey.SECURITY_AUTHORIZATION_FORM_NAME, showBreadcrumBar=true)
@TEjbService(serviceName = TAuthorizationController.JNDI_NAME, model=TAuthorization.class)
@TListViewPresenter(listViewMinWidth=500,
	page=@TPage(serviceName = TAuthorizationController.JNDI_NAME, 
		modelView=TAuthorizationMV.class, showSearch=true, showOrderBy=true,
		query=@TQuery(entity = TAuthorization.class,
			condition=@TCondition(label=TUsualKey.SECURITYID, field = "securityId", 
				operator=TCompareOp.LIKE),
			orderBy=@TOrder(label=TUsualKey.SECURITYID, field = "securityId")
		)), 
	presenter=@TPresenter(
		decorator = @TDecorator(viewTitle=ToolsKey.VIEW_AUTHORIZATION, newButtonText=TUsualKey.LOAD,
			buildDeleteButton=false, buildSaveButton=false, buildCollapseButton=false),
		behavior=@TBehavior(type=TAuthorizationBehavior.class, action=TAuthorizationLoadAction.class)
	))
@TSecurity(	id=DomainApp.AUTHORIZATION_FORM_ID, 
	appName=ToolsKey.APP_TOOLS, 
	moduleName=ToolsKey.MODULE_USER, 
	viewName=ToolsKey.VIEW_AUTHORIZATION,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.NEW})
public final class TAuthorizationMV extends TEntityModelView<TAuthorization> {
	
	@TLabel(text=TUsualKey.SECURITYID)
	@THBox(	pane=@TPane(children={"securityId","appName","moduleName","viewName"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="securityId", priority=Priority.NEVER), 
		   		@TPriority(field="appName", priority=Priority.NEVER),
		   		@TPriority(field="moduleName", priority=Priority.ALWAYS), 
			   		@TPriority(field="viewName", priority=Priority.ALWAYS)}))
	@TShowField
	private SimpleStringProperty securityId;
	
	@TLabel(text=TUsualKey.APP)
	@TShowField
	private SimpleStringProperty appName;
	
	@TLabel(text=TUsualKey.MODULE)
	@TShowField
	private SimpleStringProperty moduleName;
	
	@TLabel(text=TUsualKey.VIEW)
	@TShowField
	private SimpleStringProperty viewName;
	
	@TLabel(text=TUsualKey.TYPE)
	@TShowField
	@THBox(	pane=@TPane(children={"type","typeDescription"}), spacing=10, fillHeight=true,
			hgrow=@THGrow(priority={@TPriority(field="type", priority=Priority.NEVER), 
				   		@TPriority(field="typeDescription", priority=Priority.NEVER)}))
			
	private SimpleStringProperty type;
	
	@TLabel(text=TUsualKey.PERMISSION)
	@TShowField
	private SimpleStringProperty typeDescription;
	/*
	@TLabel(text=TUsualKey.ENABLED, position=TLabelPosition.LEFT)
	@THorizontalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButton(text=TUsualKey.YES, userData="S"), 
					@TRadioButton(text=TUsualKey.NO, userData="N")
	})
	private SimpleStringProperty enabled;
*/
	public TAuthorizationMV(TAuthorization entity) {
		super(entity);
		super.formatToString(TFormatter.create()
				.add(securityId, s-> s.toString())
				.add(appName, s->" / "+s.toString())
				.add(moduleName, s->" / "+ s.toString())
				.add(viewName, s->" / "+ s.toString())
				.add(type, s->" / "+ s.toString())
				);
	}

}
