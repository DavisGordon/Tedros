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
	
	public final Long getFileSize() {
		return fileSize;
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

	
	
}
