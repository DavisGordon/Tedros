package com.tedros.test.component.file;

import java.util.List;

import com.tedros.ejb.base.model.ITModel;
import com.tedros.ejb.base.model.TFileModel;

public class FileTestModel implements ITModel {
	
	private TFileModel selectedFile;
	
	private List<TFileModel> fileList;

	public TFileModel getSelectedFile() {
		return selectedFile;
	}

	public void setSelectedFile(TFileModel selectedFile) {
		this.selectedFile = selectedFile;
	}

	public List<TFileModel> getFileList() {
		return fileList;
	}

	public void setFileList(List<TFileModel> fileList) {
		this.fileList = fileList;
	}
	
	

}
