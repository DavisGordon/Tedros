package org.tedros.tools.module.ai.model;

import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.component.TComponent;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.model.behavior.TViewBehavior;
import org.tedros.fx.presenter.model.decorator.TViewDecorator;
import org.tedros.tools.module.ai.component.AiProviderComparisonComponent;

import javafx.beans.property.SimpleObjectProperty;

@TPresenter(model=AiProviderComparisonModel.class,
decorator=@TDecorator(type=TViewDecorator.class, viewTitle="Compare AI Providers"),
behavior=@TBehavior(type=TViewBehavior.class))
public class AiProviderComparisonMV extends TModelView<AiProviderComparisonModel> {
	
	@TFieldBox(node = @TNode(parse = true, id="component"))
	@TComponent(type = AiProviderComparisonComponent.class)
	private SimpleObjectProperty<Object> component;
	

	public AiProviderComparisonMV(AiProviderComparisonModel entity) {
		super(entity);
	}

}
