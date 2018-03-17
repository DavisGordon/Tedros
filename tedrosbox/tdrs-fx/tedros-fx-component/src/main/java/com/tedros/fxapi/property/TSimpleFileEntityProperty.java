package com.tedros.fxapi.property;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.ejb.base.entity.ITFileEntity;
import com.tedros.fxapi.annotation.reader.TFileReader;
import com.tedros.fxapi.control.TFileField;

/**
 * <pre>
 * This property can be used as representation of the {@link ITFileEntity} 
 * and to bind with {@link TFileField)   
 * </pre>
 * 
 * @see TFileReader
 * @see TFileField
 * @see com.tedros.fxapi.annotation.control.TFileField
 * @author Davis Gordon
 * */
public class TSimpleFileEntityProperty<T extends ITFileEntity> extends SimpleObjectProperty<T> {
	
	private SimpleStringProperty fileNameProperty;
	private SimpleStringProperty fileExtensionProperty;
	private SimpleLongProperty fileSizeProperty;
	private SimpleObjectProperty<byte[]> bytesProperty;
	private SimpleLongProperty bytesEntityIdProperty;
	private TSimpleFileEntityProperty<T> _this;
	
	public TSimpleFileEntityProperty() {
		super();
		initialize();
	}
	
	public TSimpleFileEntityProperty(T value) {
		super(value);
		initialize();
	}
	
	public TSimpleFileEntityProperty(Object arg0, String arg1, T value) {
		super(arg0, arg1, value);
		initialize();
	}
	
	public TSimpleFileEntityProperty(Object arg0, String arg1) {
		super(arg0, arg1);
		initialize();
	}
	
	private void initialize(){
		
		_this = this;
		this.fileNameProperty = new SimpleStringProperty();
		this.fileExtensionProperty = new SimpleStringProperty();
		this.fileSizeProperty = new SimpleLongProperty();
		this.bytesProperty = new SimpleObjectProperty<>();
		this.bytesEntityIdProperty = new SimpleLongProperty();
		
		setFilePropertyValue();
		buildListeners();
	}

	private void buildListeners() {
		this.fileNameProperty.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				_this.getValue().setFileName(arg2);
				
				String ext = FilenameUtils.getExtension(arg2);
				if(StringUtils.isNotBlank(ext)){
					fileExtensionProperty.setValue(ext);
					_this.getValue().setFileExtension(ext);
				}
				
				_this.fireValueChangedEvent();
				
			}
		});
		
		this.bytesProperty.addListener(new ChangeListener<byte[]>() {
			@Override
			public void changed(ObservableValue<? extends byte[]> arg0, byte[] arg1, byte[] arg2) {
				_this.getValue().getByteEntity().setBytes(arg2);
				_this.fireValueChangedEvent();
			}
		});
		
		this.fileSizeProperty.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				_this.getValue().setFileSize((Long)arg2);
				_this.fireValueChangedEvent();
			}
		});
	}

	private void setFilePropertyValue() {
		if(getValue()!=null){
			this.fileNameProperty.setValue(getValue().getFileName());
			this.bytesProperty.setValue(getValue().getByteEntity().getBytes());
			this.fileExtensionProperty.setValue(getValue().getFileExtension());
			this.fileSizeProperty.setValue(getValue().getFileSize());
			this.bytesEntityIdProperty.setValue(getValue().getByteEntity().getId());
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
	
	public final SimpleStringProperty fileExtensionProperty() {
		return fileExtensionProperty;
	}

	public final SimpleLongProperty fileSizeProperty() {
		return fileSizeProperty;
	}
	
	public final SimpleLongProperty bytesEntityIdProperty() {
		return bytesEntityIdProperty;
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, "bytesProperty");
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, "bytesProperty");
	}

}
