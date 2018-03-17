package com.tedros.settings.layout.model;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import com.tedros.ejb.base.model.ITFileModel;
import com.tedros.ejb.base.model.TFileModel;
import com.tedros.fxapi.annotation.control.TFileField;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.property.TSimpleFileModelProperty;
import com.tedros.settings.layout.behavior.BackgroundBehavior;
import com.tedros.settings.layout.decorator.BackgroundDecorator;

@TPresenter(type=TDynaPresenter.class, 
decorator=@TDecorator(type=BackgroundDecorator.class, viewTitle="#{background.painel.title}"), 
behavior=@TBehavior(type=BackgroundBehavior.class))
public class BackgroundImageModelView extends TModelView<BackgroundImageModel> {
	
	@TFileField
	private TSimpleFileModelProperty<ITFileModel> fileModel;
	
	
	private SimpleListProperty<TFileModel> listFileModel;
	
	
	public BackgroundImageModelView(BackgroundImageModel entity) {
		super(entity);
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return fileModel.fileNameProperty();
	}

	@Override
	public void setId(SimpleLongProperty id) {
		
	}

	@Override
	public SimpleLongProperty getId() {
		return null;
	}

}
