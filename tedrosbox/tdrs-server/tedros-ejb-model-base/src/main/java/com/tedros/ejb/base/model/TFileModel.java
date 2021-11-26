/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 04/09/2014
 */
package com.tedros.ejb.base.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TFileModel implements ITFileModel, Serializable {

	private static final long serialVersionUID = 5791991808180003595L;

	private File file;
	private String fileName = "";
	private String fileExtension;
	private String filePath;
	private Long fileSize;
	private ITByteModel byteModel;
	
	public TFileModel() {
		
	}
	
	public TFileModel(File file) throws IOException {
		this.file = file;
		readFile();
	}
	
	private void readFile() throws IOException {
		if(file!=null){
			if(file.isFile()){
				fileName = file.getName();
				filePath = FilenameUtils.getFullPath(file.getAbsolutePath());
				fileSize = FileUtils.sizeOf(file);
				if(StringUtils.isNotBlank(fileName))
					fileExtension = FilenameUtils.getExtension(fileName);
				getByteModel().setBytes(FileUtils.readFileToByteArray(file));
			}
		}else{
			fileSize = null;
			setFileName(null);
			setFilePath(null);
			getByteModel().setBytes(null);
		}
	}
	
	private void writeFile() throws IOException {
		if(file==null && StringUtils.isNotBlank(fileName) && getByteModel().getBytes()!=null && filePath!=null){
			file = new File(filePath+fileName);
			FileUtils.writeByteArrayToFile(file, getByteModel().getBytes());
		}
	}
	
	public final File getFile() throws IOException {
		writeFile();
		return file;
	}
	public final void setFile(File file) throws IOException {
		this.file = file;
		readFile();
	}
	
	public final InputStream getInputStream() {
		return getByteModel().getBytes()!=null ? new ByteArrayInputStream(getByteModel().getBytes()) : null;
	}
	
	public final void setInputStream(InputStream inputStream) throws IOException {
		getByteModel().setBytes(inputStream==null ? null : IOUtils.toByteArray(inputStream));
	}
	public final String getFileName() {
		return fileName;
	}
	public final void setFileName(String fileName) {
		this.fileName = fileName;
		if(StringUtils.isNotBlank(fileName))
			fileExtension = FilenameUtils.getExtension(fileName);
		else
			fileExtension = null;
	}
	public final String getFileExtension() {
		return fileExtension;
	}
	
	public final ITByteModel getByteModel() {
		initializeByteModel();
		return byteModel;
	}
	
	public final void setByteModel(ITByteModel byteModel) {
		initializeByteModel();
		this.byteModel = byteModel;
	}
	
	private void initializeByteModel() {
		if(byteModel==null)
			byteModel = new TByteModel();
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public void setFileExtension(String extension) {
		this.fileExtension = extension;
	}


	@Override
	public ITByteBaseModel getByte() {
		return this.getByteModel();
	}

	/**
	 * @return the fileSize;
	 */
	public Long getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public <T extends ITByteBaseModel> void setByte(T byteModel) {
		this.setByteModel((ITByteModel) byteModel);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((byteModel == null) ? 0 : byteModel.hashCode());
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + ((fileExtension == null) ? 0 : fileExtension.hashCode());
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
		result = prime * result + ((fileSize == null) ? 0 : fileSize.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TFileModel))
			return false;
		TFileModel other = (TFileModel) obj;
		if (byteModel == null) {
			if (other.byteModel != null)
				return false;
		} else if (!byteModel.equals(other.byteModel))
			return false;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (fileExtension == null) {
			if (other.fileExtension != null)
				return false;
		} else if (!fileExtension.equals(other.fileExtension))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
			return false;
		if (fileSize == null) {
			if (other.fileSize != null)
				return false;
		} else if (!fileSize.equals(other.fileSize))
			return false;
		return true;
	}


	
	
}
