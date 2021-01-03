package com.tedros.fxapi.form;

import java.util.List;
import java.util.Map;

import com.tedros.core.model.ITModelView;
import com.tedros.core.module.TObjectRepository;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.descriptor.TFieldDescriptor;
import com.tedros.fxapi.domain.TViewMode;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Node;

public abstract class TFieldLoader<M extends ITModelView<?>> {
	
	
	private SimpleBooleanProperty allLoaded = new SimpleBooleanProperty(false);
	private SimpleIntegerProperty totalToLoad = new SimpleIntegerProperty();
	protected TObjectRepository repo = new TObjectRepository();
	protected final TComponentDescriptor descriptor;
	private final Object lock = new Object();
	
	public TFieldLoader(M modelView, ITModelForm<M> form) {
		descriptor = new TComponentDescriptor(form, modelView, TViewMode.EDIT);
	}
	
	protected void initialize(){
		descriptor.getComponents().clear();
		this.totalToLoad.setValue(0);
		this.allLoaded.bind(this.descriptor.loadedProperty());
		/*for(TFieldDescriptor fd : descriptor.getFieldDescriptorList()) {
			if(fd.hasControl() || fd.hasLayout()) {
				fd.setLoaded(false);
				totalToLoad.setValue(totalToLoad.intValue()+1);
				ChangeListener<Boolean> chl = (obj, o, n) -> {
					if(n) {
						fieldLoaded();
					}
				};
				repo.add(fd.getFieldName(), chl);
				fd.loadedProperty().addListener(new WeakChangeListener<Boolean>(chl));
			}
		}*/
	}
	
	private void fieldLoaded() {
		synchronized(lock) {
			totalToLoad.setValue(totalToLoad.intValue()-1);
			if(totalToLoad.intValue()==0) {
				this.allLoaded.setValue(true);
			}
		}
	}
	
	public ReadOnlyBooleanProperty allLoadedProperty() {
		return this.allLoaded;
	}
	
	protected List<TFieldDescriptor> getFieldDescriptorList(){
		return descriptor.getFieldDescriptorList();
	}
	
	protected Node getComponent(String fieldName){
		if(descriptor.getComponents().containsKey(fieldName))
			return descriptor.getComponents().get(fieldName);
		else
			return null;
	}
	
	protected ObservableMap<String, Node> getComponents() {
		return descriptor.getComponents();
	}
	
	protected TFieldBox geFieldBox(String fieldName){
		return descriptor.getFieldBox(fieldName);
	}
	
	protected Map<String, TFieldBox> getFieldBoxMap() {
		return descriptor.getFieldBoxMap();
	}
	
	protected final TFieldDescriptor getFieldDescriptor(final String fieldName){
		for(final TFieldDescriptor field : descriptor.getFieldDescriptorList())
			if(field.getFieldName().equals(fieldName))
				return field;
		return null;
	}
	
	protected void loadEditFieldBox(final TFieldDescriptor tFieldDescriptor, boolean bcontrol) throws Exception{
		
		/*int x=0;
		if(tFieldDescriptor.getFieldName().equals("painelCorTexto"))
			x=1;*/
		
		descriptor.setMode(TViewMode.EDIT);
		descriptor.setFieldDescriptor(tFieldDescriptor);
		
		TControlLayoutBuilder builder = new TControlLayoutBuilder();
		
		Node control = (bcontrol) 
				? builder.getControlField(descriptor)
						: builder.getLayoutField(descriptor);
		
		
	}
	
	protected void loadReaderFieldBox(final ObservableList<Node> nodesLoaded, final TFieldDescriptor tFieldDescriptor) throws Exception{
		
		/*int x=0;
		if(tAnnotatedField.getFieldName().equals("imagem"))
			x=1;*/
		descriptor.setMode(TViewMode.READER);
		descriptor.setFieldDescriptor(tFieldDescriptor);
		final Node reader = TComponentBuilder.getField(descriptor);
			
		if(reader==null){
			System.err.println("WARNING: Reader null to "+tFieldDescriptor.getFieldName());
			return;
		}	
		
		nodesLoaded.add(reader);
	}
	
	protected String getModelViewClassName(){
		return descriptor.getModelView().getClass().getSimpleName();
	}
	
	protected String getFormClassName(){
		return descriptor.getForm().getClass().getSimpleName();
	}
	
	protected List<String> getFieldsName(){
		return descriptor.getFieldNameList();
	}
	
	
}
