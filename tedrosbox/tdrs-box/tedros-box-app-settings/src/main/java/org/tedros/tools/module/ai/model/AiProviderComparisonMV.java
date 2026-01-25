package org.tedros.tools.module.ai.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.domain.DomainApp;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.component.TComponent;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.model.behavior.TViewBehavior;
import org.tedros.fx.presenter.model.decorator.TViewDecorator;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.ai.component.AiProviderComparisonComponent;

import javafx.beans.property.SimpleObjectProperty;

@TPresenter(model=AiProviderComparisonModel.class,
decorator=@TDecorator(type=TViewDecorator.class, viewTitle=ToolsKey.VIEW_AI_COMPARE_MODELS),
behavior=@TBehavior(type=TViewBehavior.class))

@TSecurity(id=DomainApp.COMPARE_AI_MODELS_FORM_ID, allowedAccesses={TAuthorizationType.VIEW_ACCESS},
	appName=ToolsKey.APP_TOOLS, moduleName=ToolsKey.MODULE_AI, viewName=ToolsKey.VIEW_AI_COMPARE_MODELS)
public class AiProviderComparisonMV extends TModelView<AiProviderComparisonModel> {
	
	@TFieldBox(node = @TNode(parse = true, id="component"))
	@TComponent(type = AiProviderComparisonComponent.class)
	private SimpleObjectProperty<Object> component;
	

	public AiProviderComparisonMV(AiProviderComparisonModel entity) {
		super(entity);
	}

}
