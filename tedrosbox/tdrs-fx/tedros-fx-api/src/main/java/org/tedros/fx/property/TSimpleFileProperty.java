package org.tedros.fx.property;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.tedros.core.repository.TRepository;
import org.tedros.server.entity.ITFileEntity;
import org.tedros.server.model.ITFileBaseModel;
import org.tedros.server.model.ITFileModel;
import org.tedros.server.model.TFileModel;

import javafx.beans.property.LongProperty;
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
	
	private TRepository repo;
	
	
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
		this.repo = new TRepository();
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
		
		ChangeListener<T> valCL = (a,b,n) -> {
			setFilePropertyValue();
		};
		repo.add("valCL", valCL);
		super.addListener(new WeakChangeListener<T>(valCL));
		
		ChangeListener<String> flnCL = (a, b, n) -> {
			ITFileBaseModel v = getValue();
			if(v!=null) {
				v.setFileName(n);
				String ext = n!=null ? FilenameUtils.getExtension(n) : null;
				v.setFileExtension(ext);
				tFileExtensionProperty.setValue(ext);
				fireValueChangedEvent();
			}
		};
		repo.add("flnCL", flnCL);
		this.tFileNameProperty.addListener(new WeakChangeListener<String>(flnCL));
		
		ChangeListener<String> flpCL = (a, b, n) -> {
			ITFileBaseModel v = getValue();
			if(v!=null && v instanceof ITFileModel) {
				((ITFileModel)v).setFilePath(n);
				fireValueChangedEvent();
			}
		};
		repo.add("flpCL", flpCL);
		this.tFilePathProperty.addListener(new WeakChangeListener<String>(flpCL));
		
		ChangeListener<byte[]> bpCL = (a, b, n) -> {
			ITFileBaseModel v = getValue();
			if(v!=null) {
				v.getByte().setBytes(n);
				fireValueChangedEvent();
			}
		};
		repo.add("bpCL", bpCL);
		this.tBytesProperty.addListener(new WeakChangeListener<byte[]>(bpCL));		
		
		ChangeListener<Number> szCL = (a, b, n) -> {
			ITFileBaseModel v = getValue();
			if(v!=null) {
				v.setFileSize((Long) n);
				fireValueChangedEvent();
			}
		};
		repo.add("szCL", szCL);
		this.tFileSizeProperty.addListener(new WeakChangeListener<>(szCL));		
		
		
		ChangeListener<File> fCL = (a, b, n) -> {
			//ITFileBaseModel v = getValue();
			//if(v!=null && v instanceof ITFileModel) {
			if(n!=null) {
				try {
					ITFileModel m = new TFileModel(); //(ITFileModel) v;
					m.setFile(n);
					
					tFileNameProperty.setValue(/*n==null ? null :*/ m.getFileName());
					tFilePathProperty.setValue(/*n==null ? null :*/ m.getFilePath());
					tBytesProperty.setValue(/*n==null ? null :*/ m.getByteModel().getBytes());
					tFileSizeProperty.setValue(/*n==null ? null : */m.getFileSize());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else {
				tFileNameProperty.setValue(null);
				tFilePathProperty.setValue(null);
				tBytesProperty.setValue(null);
				tFileSizeProperty.setValue(null);
			}
			fireValueChangedEvent();
		};
		repo.add("fCL", fCL);
		this.tFileProperty.addListener(new WeakChangeListener<File>(fCL));
	}

	@SuppressWarnings("unchecked")
	private void setFilePropertyValue() {
		T v = getValue();
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
			setValue((T) new TFileModel());
		}
	}
	
	/*
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
	*/
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
