package com.tedros.fxapi.property;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.common.model.TFileEntity;
import com.tedros.core.module.TObjectRepository;
import com.tedros.ejb.base.entity.ITFileEntity;
import com.tedros.ejb.base.model.ITFileBaseModel;
import com.tedros.ejb.base.model.ITFileModel;
import com.tedros.ejb.base.model.TFileModel;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;

public class TSimpleFileProperty<T extends ITFileBaseModel> extends SimpleObjectProperty<T> {
	
	private SimpleObjectProperty<File> tFileProperty;
	private SimpleStringProperty tFileNameProperty;
	private SimpleStringProperty tFileExtensionProperty;
	private SimpleLongProperty tFileSizeProperty;
	private SimpleObjectProperty<byte[]> tBytesProperty;
	private SimpleStringProperty tFilePathProperty;
	private SimpleLongProperty bytesEntityIdProperty;
	
	private TObjectRepository repo;
	
	
	public TSimpleFileProperty() {
		super();
		initialize();
	}
	
	public TSimpleFileProperty(T value) {
		super(value);
		initialize();
	}
	
	public TSimpleFileProperty(Object arg0, String arg1, T value) {
		super(arg0, arg1, value);
		initialize();
	}
	
	public TSimpleFileProperty(Object arg0, String arg1) {
		super(arg0, arg1);
		initialize();
	}
	
	private void initialize(){
		this.repo = new TObjectRepository();
		this.tFileProperty = new SimpleObjectProperty<>();
		this.tFileNameProperty = new SimpleStringProperty();
		this.tFileExtensionProperty = new SimpleStringProperty();
		this.tFileSizeProperty = new SimpleLongProperty();
		this.tBytesProperty = new SimpleObjectProperty<>();
		this.tFilePathProperty = new SimpleStringProperty();
		this.bytesEntityIdProperty = new SimpleLongProperty();
		setFilePropertyValue();
		buildListeners();
	}

	private void buildListeners() {
		
		ChangeListener<String> flnCL = (arg0, arg1, arg2) -> {
				getValue().setFileName(arg2);
				tFileExtensionProperty.setValue(getValue().getFileExtension());
				fireValueChangedEvent();
			};
		repo.add("flnCL", flnCL);
		this.tFileNameProperty.addListener(new WeakChangeListener<String>(flnCL));
		
		ChangeListener<String> flpCL = (arg0, arg1, arg2) -> {
			ITFileBaseModel v = getValue();
			if(v!=null && v instanceof ITFileModel) {
				((ITFileModel)v).setFilePath(arg2);
				fireValueChangedEvent();
			}
		};
		repo.add("flpCL", flpCL);
		this.tFilePathProperty.addListener(new WeakChangeListener<String>(flpCL));
		
		ChangeListener<byte[]> bpCL = (arg0, arg1, arg2) -> {
			ITFileBaseModel v = getValue();
			if(v!=null) {
				if(v instanceof ITFileModel) 
					((ITFileModel)v).getByteModel().setBytes(arg2);
				else
					((ITFileEntity)v).getByteEntity().setBytes(arg2);
				fireValueChangedEvent();
			}
		};
		repo.add("bpCL", bpCL);
		this.tBytesProperty.addListener(new WeakChangeListener<byte[]>(bpCL));		
		
		ChangeListener<File> fCL = (arg0, arg1, arg2) -> {
			ITFileBaseModel v = getValue();
			if(v!=null && v instanceof ITFileModel) {
				try {
					ITFileModel m = (ITFileModel) v;
					m.setFile(arg2);
					tFileNameProperty.setValue(arg2==null ? null : m.getFileName());
					tFilePathProperty.setValue(arg2==null ? null : m.getFilePath());
					tBytesProperty.setValue(arg2==null ? null : m.getByteModel().getBytes());
					tFileSizeProperty.setValue(arg2==null ? null : m.getFileSize());
				} catch (IOException e) {
					e.printStackTrace();
				}
				fireValueChangedEvent();
			}
		};
		repo.add("fCL", fCL);
		this.tFileProperty.addListener(new WeakChangeListener<File>(fCL));
	}

	@SuppressWarnings("unchecked")
	private void setFilePropertyValue() {
		ITFileBaseModel v = getValue();
		if(v!=null) {
			try{
				if(v instanceof ITFileModel) {
					ITFileModel m = (ITFileModel) v;
					this.tBytesProperty.setValue(m.getByteModel().getBytes());
					this.tFileProperty.setValue(m.getFile());
				}else {
					ITFileEntity m = (ITFileEntity) v;
					this.tBytesProperty.setValue(m.getByteEntity().getBytes());
					this.bytesEntityIdProperty.setValue(m.getByteEntity().getId());
				}
					
				this.tFileNameProperty.setValue(getValue().getFileName());
				this.tFileExtensionProperty.setValue(getValue().getFileExtension());
				this.tFileSizeProperty.setValue(getValue().getFileSize());
				
			}catch(IOException e){
				e.printStackTrace();
			}
	
		}else{
			try {
				setValue((T) new TFileModel());
			}catch(ClassCastException e) {
				setValue((T) new TFileEntity());
			}
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
	
	public final SimpleStringProperty tFileNameProperty() {
		return tFileNameProperty;
	}
	
	public final SimpleObjectProperty<byte[]> tBytesProperty() {
		return tBytesProperty;
	}
	
	public final ReadOnlyStringProperty tFileExtensionProperty() {
		return tFileExtensionProperty;
	}

	public final LongProperty tFileSizeProperty() {
		return tFileSizeProperty;
	}
	
	public final SimpleStringProperty tFilePathProperty() {
		return tFilePathProperty;
	}
	
	public final SimpleObjectProperty<File> tFileProperty() {
		return tFileProperty;
	}
	
	public final SimpleLongProperty tBytesEntityIdProperty() {
		return bytesEntityIdProperty;
	}
	
	public final File gettFile() throws IOException {
		return tFileProperty.getValue();
	}
	public final void settFile(File file) throws IOException {
		tFileProperty.setValue(file);
	}
	
	public final String gettFileName() {
		return tFileNameProperty.getValue();
	}
	public final void settFileName(String fileName) {
		this.tFileNameProperty.setValue(fileName);
	}
	public final String gettFileExtension() {
		return tFileExtensionProperty.getValue();
	}
	
	public final Long gettFileSize() {
		return tFileSizeProperty.getValue();
	}
	
	public final byte[] gettBytes() {
		return tBytesProperty.getValue();
	}
	
	public final void settBytes(byte[] bytes) {
		this.tBytesProperty.setValue(bytes);
	}
	
	public String gettFilePath() {
		return tFilePathProperty.getValue();
	}

	public void settFilePath(String filePath) {
		this.tFilePathProperty.setValue(filePath);
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
