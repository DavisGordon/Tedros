package org.tedros.tools.module.ai.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.domain.DomainApp;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.form.TSetting;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.annotation.scene.web.TWebEngine;
import org.tedros.fx.annotation.scene.web.TWebView;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.model.behavior.TViewBehavior;
import org.tedros.fx.presenter.model.decorator.TViewDecorator;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.start.TConstant;

import javafx.beans.property.SimpleStringProperty;

@TForm(scroll=false)
//@TSetting(TmpSettings.class)
@TPresenter(model=HtmlMessageViewerModel.class,
decorator=@TDecorator(type=TViewDecorator.class, viewTitle=ToolsKey.VIEW_AI_CHAT_MESSAGE_VIEWER, 
	region = @TRegion(parse = true, maxWidth = 1080, maxHeight=620)),
behavior=@TBehavior(type=TViewBehavior.class))
@TSecurity(id=DomainApp.MESSAGE_VIEWER_FORM_ID,
appName=ToolsKey.APP_TOOLS, moduleName=ToolsKey.MODULE_AI, viewName=ToolsKey.VIEW_AI_CHAT_MESSAGE_VIEWER,
allowedAccesses={	TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT,  
	   				TAuthorizationType.NEW, TAuthorizationType.SAVE, TAuthorizationType.DELETE})
public class HtmlMessageViewerMV extends TModelView<HtmlMessageViewerModel> {
	
	//private final static double HEIGHT = 500;
		
	@TWebView(engine = @TWebEngine(load = TWebEngine.MODULE_FOLDER+"/"+TConstant.UUI+"/teros_ia_response.html"),
			maxWidth = 1040, maxHeight=550, zoom = 0.8)
	private SimpleStringProperty webContent;

	public HtmlMessageViewerMV(HtmlMessageViewerModel model) {
		super(model);
	}

	public SimpleStringProperty getWebContent() {
		return webContent;
	}

	public void setWebContent(SimpleStringProperty webContent) {
		this.webContent = webContent;
	}
	
	

}
