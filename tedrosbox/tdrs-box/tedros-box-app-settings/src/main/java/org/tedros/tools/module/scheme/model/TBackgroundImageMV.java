package org.tedros.tools.module.scheme.model;

import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TSelectImageField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.form.TSetting;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.domain.TEnvironment;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.server.model.ITFileBaseModel;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.scheme.behaviour.TBackgroundBehavior;
import org.tedros.tools.module.scheme.decorator.TBackgroundDecorator;

import javafx.beans.property.SimpleObjectProperty;

@TSetting(TBackgroundSetting.class)
@TForm(header = ToolsKey.VIEW_BACKGROUND, scroll=false)
@TPresenter(type=TDynaPresenter.class, 
decorator=@TDecorator(type=TBackgroundDecorator.class, viewTitle=ToolsKey.VIEW_BACKGROUND), 
behavior=@TBehavior(type=TBackgroundBehavior.class))
public class TBackgroundImageMV extends TModelView<TBackgroundImage> {
	
	@TFieldBox(node=@TNode(id="img", parse = true))
	@TSelectImageField(source=TEnvironment.LOCAL, target=TEnvironment.LOCAL)
	private SimpleObjectProperty<ITFileBaseModel> fileModel;
	
	public TBackgroundImageMV(TBackgroundImage entity) {
		super(entity);
	}

	/**
	 * @return the fileModel
	 */
	public SimpleObjectProperty<ITFileBaseModel> getFileModel() {
		return fileModel;
	}

	/**
	 * @param fileModel the fileModel to set
	 */
	public void setFileModel(SimpleObjectProperty<ITFileBaseModel> fileModel) {
		this.fileModel = fileModel;
	}

}
