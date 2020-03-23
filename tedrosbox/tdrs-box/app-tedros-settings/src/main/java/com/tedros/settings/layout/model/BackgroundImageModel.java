package com.tedros.settings.layout.model;

import java.util.List;

import com.tedros.ejb.base.model.ITModel;
import com.tedros.ejb.base.model.TFileModel;

public class BackgroundImageModel implements ITModel {

	private TFileModel fileModel;
	
	private List<TFileModel> listFileModel;
	
	public BackgroundImageModel() {
	
	}

	public final TFileModel getFileModel() {
		return fileModel;
	}

	public final void setFileModel(TFileModel fileModel) {
		this.fileModel = fileModel;
	}

	public final List<TFileModel> getListFileModel() {
		return listFileModel;
	}

	public final void setListFileModel(List<TFileModel> listFileModel) {
		this.listFileModel = listFileModel;
	}
	
	
}
