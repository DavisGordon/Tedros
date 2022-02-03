package com.covidsemfome.module.empresaParceira.model;

import com.covidsemfome.model.CsfImagem;
import com.tedros.common.model.TFileEntity;
import com.tedros.ejb.base.model.ITFileBaseModel;
import com.tedros.fxapi.annotation.control.TModelViewType;
import com.tedros.fxapi.annotation.control.TSelectImageField;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.domain.TEnvironment;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;


@TEjbService(serviceName = "ICsfImagemControllerRemote", model=CsfImagem.class)
@TListViewPresenter(
	paginator=@TPaginator(entityClass = CsfImagem.class, serviceName = "ICsfImagemControllerRemote", show=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="Fotos e Imagens", buildModesRadioButton=false )))
/*@TSecurity(	id="COVSEMFOME_PARCEIRO_FOTOSIMG_FORM", 
	appName = "#{app.name}", moduleName = "Administrativo", viewName = "Fotos e Imagens",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT,  
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})*/
public class ImagemMV extends TEntityModelView<CsfImagem> {

	private SimpleLongProperty id;
	
	private SimpleStringProperty display;
	
	@TSelectImageField(source=TEnvironment.LOCAL, target=TEnvironment.REMOTE, remoteOwner= {"csf"})
	@TModelViewType(modelClass = TFileEntity.class)
	private ITObservableList<ITFileBaseModel> files;
	
	
	public ImagemMV(CsfImagem model) {
		super(model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	@Override
	public SimpleLongProperty getId() {
		return id;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return display;
	}

	/**
	 * @return the display
	 */
	public SimpleStringProperty getDisplay() {
		return display;
	}

	/**
	 * @param display the display to set
	 */
	public void setDisplay(SimpleStringProperty display) {
		this.display = display;
	}

	/**
	 * @return the files
	 */
	public ITObservableList<ITFileBaseModel> getFiles() {
		return files;
	}

	/**
	 * @param files the files to set
	 */
	public void setFiles(ITObservableList<ITFileBaseModel> files) {
		this.files = files;
	}


}
