package com.tedros.fxapi.property;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.core.module.TObjectRepository;
import com.tedros.ejb.base.entity.ITFileEntity;
import com.tedros.fxapi.annotation.reader.TFileReader;
import com.tedros.fxapi.annotation.scene.image.TImageView;
import com.tedros.fxapi.control.TFileField;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;

/**
 * <pre>
 * This property can be used as representation of the {@link ITFileEntity}.   
 * </pre>
 * 
 * @see TFileReader
 * @see TFileField
 * @see com.tedros.fxapi.annotation.control.TFileField
 * @see TImageView
 * @author Davis Gordon
 * */
public class TSimpleFileEntityProperty<T extends ITFileEntity> extends SimpleObjectProperty<T> {
	
	private SimpleStringProperty fileNameProperty;
	private SimpleStringProperty fileExtensionProperty;
	private SimpleLongProperty fileSizeProperty;
	private SimpleObjectProperty<byte[]> bytesProperty;
	private SimpleLongProperty bytesEntityIdProperty;

	private TObjectRepository repo;
	
	
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
		
		this.repo = new TObjectRepository();
		this.fileNameProperty = new SimpleStringProperty();
		this.fileExtensionProperty = new SimpleStringProperty();
		this.fileSizeProperty = new SimpleLongProperty();
		this.bytesProperty = new SimpleObjectProperty<>();
		this.bytesEntityIdProperty = new SimpleLongProperty();
		
		setFilePropertyValue();
		buildListeners();
	}

	private void buildListeners() {
		
		ChangeListener<String> flnCL = (arg0, arg1, arg2) -> {
			getValue().setFileName(arg2);
			
			String ext = FilenameUtils.getExtension(arg2);
			if(StringUtils.isNotBlank(ext)){
				fileExtensionProperty.setValue(ext);
				getValue().setFileExtension(ext);
			}
			
			fireValueChangedEvent();
		};
		repo.add("flnCL", flnCL);
		this.fileNameProperty.addListener(new WeakChangeListener<String>(flnCL));
		
		ChangeListener<byte[]> bpCL = (arg0, arg1, arg2) -> {
			getValue().getByteEntity().setBytes(arg2);
			fireValueChangedEvent();
		};
		repo.add("bpCL", bpCL);
		this.bytesProperty.addListener(new WeakChangeListener<byte[]>(bpCL));		
		
		ChangeListener<Number> fsCL = (arg0, arg1, arg2) -> {
			getValue().setFileSize((Long)arg2);
			fireValueChangedEvent();
		};
		repo.add("fsCL", fsCL);
		this.fileSizeProperty.addListener(new WeakChangeListener<Number>(fsCL));
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
	
	public void invalidate() {
		repo.clear();
	}

}
