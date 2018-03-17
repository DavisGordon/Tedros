package com.tedros.fxapi.property;

import java.io.File;
import java.io.IOException;

import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.ejb.base.model.ITFileModel;
import com.tedros.ejb.base.model.TFileModel;

public class TSimpleFileModelProperty<T extends ITFileModel> extends SimpleObjectProperty<T> {
	
	private SimpleObjectProperty<File> fileProperty;
	private SimpleStringProperty fileNameProperty;
	private SimpleStringProperty fileExtensionProperty;
	private SimpleLongProperty fileSizeProperty;
	private SimpleObjectProperty<byte[]> bytesProperty;
	private SimpleStringProperty filePathProperty;
	
	private TSimpleFileModelProperty<T> _this;
	
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
		_this = this;
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
		this.fileNameProperty.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				_this.getValue().setFileName(arg2);
				fileExtensionProperty.setValue(_this.getValue().getFileExtension());
				_this.fireValueChangedEvent();
			}
		});
		
		this.filePathProperty.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				_this.getValue().setFilePath(arg2);
				_this.fireValueChangedEvent();
			}
		});
		
		
		this.bytesProperty.addListener(new ChangeListener<byte[]>() {
			@Override
			public void changed(ObservableValue<? extends byte[]> arg0, byte[] arg1, byte[] arg2) {
				_this.getValue().getByteModel().setBytes(arg2);
				_this.fireValueChangedEvent();
			}
		});		
		
		this.fileProperty.addListener(new ChangeListener<File>() {
			@Override
			public void changed(ObservableValue<? extends File> arg0, File arg1, File arg2) {
				try {
					_this.getValue().setFile(arg2);
					fileNameProperty.setValue(arg2==null ? null : _this.getValue().getFileName());
					filePathProperty.setValue(arg2==null ? null : _this.getValue().getFilePath());
					bytesProperty.setValue(arg2==null ? null : _this.getValue().getByteModel().getBytes());
					fileSizeProperty.setValue(arg2==null ? null : _this.getValue().getFileSize());
				} catch (IOException e) {
					e.printStackTrace();
				}
				_this.fireValueChangedEvent();
			}
		});
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

}
