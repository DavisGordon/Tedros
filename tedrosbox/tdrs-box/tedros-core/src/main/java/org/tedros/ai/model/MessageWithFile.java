package org.tedros.ai.model;

import java.util.List;

import org.tedros.common.model.TFileContentInfo;

public class MessageWithFile {
	
	private Object model;
	
	private List<TFileContentInfo> filesContentInfo;
	
	public MessageWithFile() {
		
	}

	public MessageWithFile(Object model, List<TFileContentInfo> filesContentInfo) {
		super();
		this.model = model;
		this.filesContentInfo = filesContentInfo;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		this.model = model;
	}

	public List<TFileContentInfo> getFilesContentInfo() {
		return filesContentInfo;
	}

	public void setFilesContentInfo(List<TFileContentInfo> filesContentInfo) {
		this.filesContentInfo = filesContentInfo;
	}		

}
