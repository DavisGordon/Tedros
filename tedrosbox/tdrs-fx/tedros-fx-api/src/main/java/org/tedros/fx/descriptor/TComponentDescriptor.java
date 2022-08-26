package org.tedros.fx.descriptor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.api.form.ITFieldBox;
import org.tedros.api.form.ITForm;
import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.api.presenter.view.TViewMode;
import org.tedros.core.model.ITModelView;
import org.tedros.fx.util.TReflectionUtil;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.Node;

/**
 * <pre>
 * The component descriptor class, contais all necessary information about the field in execution, 
 * used to build and parse a field to a node.  
 * </pre>
 * 
 * @author Davis Gordon
 * */
@SuppressWarnings("rawtypes")
public class TComponentDescriptor implements ITComponentDescriptor {
	
	private final ITForm form;
	private final ITModelView modelView;
	private BooleanProperty loaded = new SimpleBooleanProperty(false);
	private final List<ITFieldDescriptor> fieldDescriptorList;
	private final List<String> fieldsNameList;
	private final List<Annotation> modelViewAnnotationList;
	private final ObservableMap<String, Node> components;
	private final java.util.Map<String, ITFieldBox> fieldBoxMap;
	private ITFieldDescriptor fieldDescriptor;
	private TViewMode mode;
	private String annotationPropertyInExecution;
	private Class<? extends ITAnnotationParser> parserClassInExecution;
	
	/**
	 * <pre>
	 * Initialize a new {@link TComponentDescriptor}
	 * </pre>
	 * */
	public TComponentDescriptor(ITForm form, ITModelView modelView, TViewMode mode) {
		this.form = form;
		this.modelView = modelView;
		this.components = FXCollections.observableHashMap();
		this.fieldBoxMap = new HashMap<>();
		this.fieldsNameList = new ArrayList<>(0);
		this.mode = mode;
		this.modelViewAnnotationList =  modelView!=null ? Arrays.asList(this.modelView.getClass().getAnnotations()) : null;
		this.fieldDescriptorList = modelView!=null ? TReflectionUtil.getFieldDescriptorList(this.modelView) : null;
		if(this.modelView!=null) {
			loadFieldNameList(); 
			
			BooleanExpression be = null;
			for (final ITFieldDescriptor fd : fieldDescriptorList) {
				if(fd.hasLayout() || fd.hasControl()) {
					if(be==null)
						be = BooleanBinding.booleanExpression(fd.loadedProperty());
					else
						be = be.and(fd.loadedProperty());
				}
			}
			if(be!=null)
				this.loaded.bind(be);
		}
	}
	
	/**
	 * <pre>
	 * Initialize a clone of the given {@link TComponentDescriptor} to start build a new field.
	 * 
	 * Used when one field need build another field.
	 * </pre>
	 * */
	public TComponentDescriptor(ITComponentDescriptor componentDescriptor, String fieldToBuild) {
		this.form = componentDescriptor.getForm();
		this.modelView = componentDescriptor.getModelView();
		this.modelViewAnnotationList = componentDescriptor.getModelViewAnnotationList();
		this.components = componentDescriptor.getComponents();
		this.fieldBoxMap = componentDescriptor.getFieldBoxMap();
		this.fieldDescriptorList = componentDescriptor.getFieldDescriptorList();
		this.mode = componentDescriptor.getMode();
		this.fieldsNameList = componentDescriptor.getFieldNameList();
		if(StringUtils.isNotBlank(fieldToBuild)){
			for (final ITFieldDescriptor fd : fieldDescriptorList) {
				if(fd.getFieldName().equals(fieldToBuild)){
					this.fieldDescriptor = fd;
					break;
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITComponentDescriptor#getFieldDescriptor(java.lang.String)
	 */
	@Override
	public  ITFieldDescriptor getFieldDescriptor(String fieldName) {
		if(fieldName!=null)
			for (final ITFieldDescriptor fd : fieldDescriptorList) 
				if(fd.getFieldName().equals(fieldName))
					return fd;
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITComponentDescriptor#isLoaded()
	 */
	@Override
	public boolean isLoaded() {
		return loaded.get();
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITComponentDescriptor#loadedProperty()
	 */
	@Override
	public ReadOnlyBooleanProperty loadedProperty() {
		return this.loaded;
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITComponentDescriptor#getFieldAnnotation(java.lang.Class)
	 */
	@Override
	public <A extends Annotation> A getFieldAnnotation(Class<A> type) {
		return fieldDescriptor.getAnnotation(type);
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITComponentDescriptor#getFieldAnnotationList()
	 */
	@Override
	public List<Annotation> getFieldAnnotationList(){
		return fieldDescriptor.getAnnotations();
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITComponentDescriptor#getFieldDescriptorList()
	 */
	@Override
	public List<ITFieldDescriptor> getFieldDescriptorList() {
		return fieldDescriptorList;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITComponentDescriptor#getForm()
	 */
	@Override
	public final ITForm getForm() {
		return form;
	}

	
	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITComponentDescriptor#getModelView()
	 */
	@Override
	public final ITModelView getModelView() {
		return modelView;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITComponentDescriptor#getFieldNameList()
	 */
	@Override
	public final List<String> getFieldNameList() {
		return fieldsNameList;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITComponentDescriptor#getModelViewAnnotationList()
	 */
	@Override
	public final List<Annotation> getModelViewAnnotationList() {
		return modelViewAnnotationList;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITComponentDescriptor#getComponents()
	 */
	@Override
	public final ObservableMap<String, Node> getComponents() {
		return components;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITComponentDescriptor#getFieldDescriptor()
	 */
	@Override
	public synchronized final ITFieldDescriptor getFieldDescriptor() {
		return fieldDescriptor;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITComponentDescriptor#setFieldDescriptor(org.tedros.fx.descriptor.ITFieldDescriptor)
	 */
	@Override
	public final void setFieldDescriptor(ITFieldDescriptor fieldDescriptor) {
		this.fieldDescriptor = fieldDescriptor;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITComponentDescriptor#getMode()
	 */
	@Override
	public final TViewMode getMode() {
		return mode;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITComponentDescriptor#setMode(org.tedros.fx.domain.TViewMode)
	 */
	@Override
	public final void setMode(TViewMode mode) {
		this.mode = mode;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITComponentDescriptor#getAnnotationPropertyInExecution()
	 */
	@Override
	public final String getAnnotationPropertyInExecution() {
		return annotationPropertyInExecution;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITComponentDescriptor#setAnnotationPropertyInExecution(java.lang.String)
	 */
	@Override
	public final void setAnnotationPropertyInExecution(
			String annotationPropertyInExecution) {
		this.annotationPropertyInExecution = annotationPropertyInExecution;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITComponentDescriptor#getParserClassInExecution()
	 */
	@Override
	public final Class<? extends ITAnnotationParser> getParserClassInExecution() {
		return parserClassInExecution;
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITComponentDescriptor#setParserClassInExecution(java.lang.Class)
	 */
	@Override
	public final void setParserClassInExecution(
			Class<? extends ITAnnotationParser> parserClassInExecution) {
		this.parserClassInExecution = parserClassInExecution;
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITComponentDescriptor#getFieldValue()
	 */
	@Override
	public Object getFieldValue() throws IllegalArgumentException, IllegalAccessException{
		this.fieldDescriptor.getField().setAccessible(true);
		final Object obj = this.fieldDescriptor.getField().get(this.modelView);
		this.fieldDescriptor.getField().setAccessible(false);
		return obj;
	}
	
	/**
	 * Load the fieldsNameList
	 * */
	private void loadFieldNameList() {
		for(final ITFieldDescriptor f : fieldDescriptorList)
			fieldsNameList.add(f.getFieldName());
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITComponentDescriptor#getFieldBox(java.lang.String)
	 */
	@Override
	public ITFieldBox getFieldBox(String field) {
		return this.fieldBoxMap.get(field);
	}
	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITComponentDescriptor#getFieldBoxMap()
	 */
	@Override
	public java.util.Map<String, ITFieldBox> getFieldBoxMap() {
		return fieldBoxMap;
	}
	
}
