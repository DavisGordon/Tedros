package org.tedros.fx.form;

import java.util.List;
import java.util.Map;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.api.form.ITFieldBox;
import org.tedros.api.form.ITModelForm;
import org.tedros.api.presenter.view.TViewMode;
import org.tedros.core.model.ITModelView;
import org.tedros.core.repository.TRepository;
import org.tedros.fx.descriptor.TComponentDescriptor;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Node;

public abstract class TFieldLoader<M extends ITModelView<?>> {
	
	
	private SimpleBooleanProperty allLoaded = new SimpleBooleanProperty(false);
	private SimpleIntegerProperty totalToLoad = new SimpleIntegerProperty();
	protected TRepository repo = new TRepository();
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
	
	protected List<ITFieldDescriptor> getFieldDescriptorList(){
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
	
	protected ITFieldBox geFieldBox(String fieldName){
		return descriptor.getFieldBox(fieldName);
	}
	
	protected Map<String, ITFieldBox> getFieldBoxMap() {
		return descriptor.getFieldBoxMap();
	}
	
	protected final ITFieldDescriptor getFieldDescriptor(final String fieldName){
		for(final ITFieldDescriptor field : descriptor.getFieldDescriptorList())
			if(field.getFieldName().equals(fieldName))
				return field;
		return null;
	}
	
	protected void loadEditFieldBox(final ITFieldDescriptor tFieldDescriptor, boolean bcontrol) throws Exception{
		
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
	
	protected void loadReaderFieldBox(final ObservableList<Node> nodesLoaded, final ITFieldDescriptor tFieldDescriptor) throws Exception{
		
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
	ITComponentDescriptor getDescriptor() {
		return descriptor;
	}
	
	
}
