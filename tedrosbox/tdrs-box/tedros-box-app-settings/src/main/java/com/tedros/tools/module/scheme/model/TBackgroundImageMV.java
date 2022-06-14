package com.tedros.tools.module.scheme.model;

import com.tedros.ejb.base.model.ITFileBaseModel;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TSelectImageField;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.form.TSetting;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.domain.TEnvironment;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.tools.module.scheme.behaviour.TBackgroundBehavior;
import com.tedros.tools.module.scheme.decorator.TBackgroundDecorator;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

@TSetting(TBackgroundSetting.class)
@TForm(name = "#{view.background}", scroll=false)
@TPresenter(type=TDynaPresenter.class, 
decorator=@TDecorator(type=TBackgroundDecorator.class, viewTitle="#{view.background}"), 
behavior=@TBehavior(type=TBackgroundBehavior.class))
public class TBackgroundImageMV extends TModelView<TBackgroundImage> {
	
	@TFieldBox(node=@TNode(id="img", parse = true))
	@TSelectImageField(source=TEnvironment.LOCAL, target=TEnvironment.LOCAL)
	private SimpleObjectProperty<ITFileBaseModel> fileModel;
	
	public TBackgroundImageMV(TBackgroundImage entity) {
		super(entity);
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		//return fileModel.tFileNameProperty();
		return null;
	}

	@Override
	public void setId(SimpleLongProperty id) {
		
	}

	@Override
	public SimpleLongProperty getId() {
		return null;
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
