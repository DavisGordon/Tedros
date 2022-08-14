package org.tedros.fx.form;

import java.util.List;
import java.util.Map;

import org.tedros.core.model.ITModelView;
import org.tedros.core.module.TObjectRepository;
import org.tedros.fx.descriptor.TComponentDescriptor;
import org.tedros.fx.descriptor.TFieldDescriptor;
import org.tedros.fx.domain.TViewMode;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Node;

public abstract class TFieldLoader<M extends ITModelView<?>> {
	
	
	private SimpleBooleanProperty allLoaded = new SimpleBooleanProperty(false);
	private SimpleIntegerProperty totalToLoad = new SimpleIntegerProperty();
	protected TObjectRepository repo = new TObjectRepository();
	protected final TComponentDescriptor descriptor;
	
	public TFieldLoader(M modelView, ITModelForm<M> form) {
		descriptor = new TComponentDescriptor(form, modelView, TViewMode.EDIT);
	}
	
	protected void initialize(){
		descriptor.getComponents().clear();
		this.totalToLoad.setValue(0);
		this.allLoaded.bind(this.descriptor.loadedProperty());
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
		
		if (bcontrol) 
			builder.getControlField(descriptor);
		else
			builder.getLayoutField(descriptor);
		
		
	}
	
	protected void loadReaderFieldBox(final ObservableList<Node> nodesLoaded, final TFieldDescriptor tFieldDescriptor) throws Exception{
		
		descriptor.setMode(TViewMode.READER);
		descriptor.setFieldDescriptor(tFieldDescriptor);
		final Node reader = TControlLayoutReaderBuilder.getField(descriptor);
			
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

	/**
	 * @return the descriptor
	 */
	TComponentDescriptor getDescriptor() {
		return descriptor;
	}
	
	
}
