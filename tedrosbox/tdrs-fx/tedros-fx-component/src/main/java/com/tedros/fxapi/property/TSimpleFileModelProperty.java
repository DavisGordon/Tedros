package com.tedros.fxapi.property;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.core.module.TListenerRepository;
import com.tedros.ejb.base.model.ITFileModel;
import com.tedros.ejb.base.model.TFileModel;

import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;

public class TSimpleFileModelProperty<T extends ITFileModel> extends SimpleObjectProperty<T> {
	
	private SimpleObjectProperty<File> fileProperty;
	private SimpleStringProperty fileNameProperty;
	private SimpleStringProperty fileExtensionProperty;
	private SimpleLongProperty fileSizeProperty;
	private SimpleObjectProperty<byte[]> bytesProperty;
	private SimpleStringProperty filePathProperty;
	
	private TListenerRepository repo;
	
	
	public TSimpleFileModelProperty() {
		super();
		initialize();
	}
	
	public TSimpleFileModelProperty(T value) {
		super(value);
		initialize();
	}
	
	public TSimpleFileModelProperty(Object arg0, String arg1, T value) {
		super(arg0, arg1, value);
		initialize();
	}
	
	public TSimpleFileModelProperty(Object arg0, String arg1) {
		super(arg0, arg1);
		initialize();
	}
	
	private void initialize(){
		this.repo = new TListenerRepository();
		this.fileProperty = new SimpleObjectProperty<>();
		this.fileNameProperty = new SimpleStringProperty();
		this.fileExtensionProperty = new SimpleStringProperty();
		this.fileSizeProperty = new SimpleLongProperty();
		this.bytesProperty = new SimpleObjectProperty<>();
		this.filePathProperty = new SimpleStringProperty();
		
		setFilePropertyValue();
		buildListeners();
	}

	private void buildListeners() {
		
		ChangeListener<String> flnCL = (arg0, arg1, arg2) -> {
				getValue().setFileName(arg2);
				fileExtensionProperty.setValue(getValue().getFileExtension());
				fireValueChangedEvent();
			};
		repo.addListener("flnCL", flnCL);
		this.fileNameProperty.addListener(new WeakChangeListener<String>(flnCL));
		
		ChangeListener<String> flpCL = (arg0, arg1, arg2) -> {
			getValue().setFilePath(arg2);
			fireValueChangedEvent();
		};
		repo.addListener("flpCL", flpCL);
		this.filePathProperty.addListener(new WeakChangeListener<String>(flpCL));
		
		ChangeListener<byte[]> bpCL = (arg0, arg1, arg2) -> {
			getValue().getByteModel().setBytes(arg2);
			fireValueChangedEvent();
		};
		repo.addListener("bpCL", bpCL);
		this.bytesProperty.addListener(new WeakChangeListener<byte[]>(bpCL));		
		
		ChangeListener<File> fCL = (arg0, arg1, arg2) -> {
			try {
				getValue().setFile(arg2);
				fileNameProperty.setValue(arg2==null ? null : getValue().getFileName());
				filePathProperty.setValue(arg2==null ? null : getValue().getFilePath());
				bytesProperty.setValue(arg2==null ? null : getValue().getByteModel().getBytes());
				fileSizeProperty.setValue(arg2==null ? null : getValue().getFileSize());
			} catch (IOException e) {
				e.printStackTrace();
			}
			fireValueChangedEvent();
		};
		repo.addListener("fCL", fCL);
		this.fileProperty.addListener(new WeakChangeListener<File>(fCL));
	}

	@SuppressWarnings("unchecked")
	private void setFilePropertyValue() {
		if(getValue()!=null){
			
			try{
				this.fileNameProperty.setValue(getValue().getFileName());
				this.bytesProperty.setValue(getValue().getByteModel().getBytes());
				this.fileProperty.setValue(getValue().getFile());
				this.fileExtensionProperty.setValue(getValue().getFileExtension());
				this.fileSizeProperty.setValue(getValue().getFileSize());
			}catch(IOException e){
				e.printStackTrace();
			}
	
		}else{
			setValue((T) new TFileModel());
		}
	}
	
	@Override
	public void set(T arg0) {
		super.set(arg0);
		setFilePropertyValue();
	}
	
	@Override
	public void setValue(T arg0) {
		super.setValue(arg0);
		setFilePropertyValue();
	}
	
	public final SimpleStringProperty fileNameProperty() {
		return fileNameProperty;
	}
	
	public final SimpleObjectProperty<byte[]> bytesProperty() {
		return bytesProperty;
	}
	
	public final ReadOnlyStringProperty fileExtensionProperty() {
		return fileExtensionProperty;
	}

	public final ReadOnlyLongProperty fileSizeProperty() {
		return fileSizeProperty;
	}
	
	public final SimpleStringProperty filePathProperty() {
		return filePathProperty;
	}
	
	public final SimpleObjectProperty<File> fileProperty() {
		return fileProperty;
	}
	
	
	public final File getFile() throws IOException {
		return fileProperty.getValue();
	}
	public final void setFile(File file) throws IOException {
		fileProperty.setValue(file);
	}
	
	public final String getFileName() {
		return fileNameProperty.getValue();
	}
	public final void setFileName(String fileName) {
		this.fileNameProperty.setValue(fileName);
	}
	public final String getFileExtension() {
		return fileExtensionProperty.getValue();
	}
	
	public final Long getFileSize() {
		return fileSizeProperty.getValue();
	}
	
	public final byte[] getBytes() {
		return bytesProperty.getValue();
	}
	
	public final void setBytes(byte[] bytes) {
		this.bytesProperty.setValue(bytes);
	}
	
	public String getFilePath() {
		return filePathProperty.getValue();
	}

	public void setFilePath(String filePath) {
		this.filePathProperty.setValue(filePath);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}

	public void invalidate() {
		repo.clear();
	}

}
