package org.tedros.fx.descriptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.fx.annotation.TIgnoreField;
import org.tedros.fx.util.TReflectionUtil;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;

/**
 * <pre>
 * The field descriptor class, contais all necessary information about the field.  
 * </pre>
 * 
 * @author Davis Gordon
 * */
public class TFieldDescriptor implements ITFieldDescriptor  {
	
	private final Field field;
	private boolean hasParent;
	private BooleanProperty componentLoaded = new SimpleBooleanProperty(false);
	private BooleanProperty layoutLoaded = new SimpleBooleanProperty(false);
	private BooleanProperty loaded = new SimpleBooleanProperty(false);
	private Boolean ignorable;
	private Object[] arrControl;
	private Object[] arrLayout;
	private int order;
	private Node layout;
	private Node component;
	private Node control;
	
	/**
	 * Initialize this object
	 * */
	public TFieldDescriptor(Field field) {
		this.field = field;
		List<Annotation> lst = this.getAnnotations();
		arrControl = TReflectionUtil.getControlBuilder(lst);
		arrLayout = TReflectionUtil.getLayoutBuilder(lst);
		this.hasParent = false;
		if(this.hasLayout() && this.hasControl())
			this.loaded.bind(BooleanBinding.booleanExpression(componentLoaded).and(layoutLoaded));
		else if(!this.hasLayout() && this.hasControl())
			this.loaded.bind(componentLoaded);
		else if(this.hasLayout() && !this.hasControl())
			this.loaded.bind(layoutLoaded);
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#isIgnorable()
	 */
	@Override
	public boolean isIgnorable(){
		if(ignorable==null) 
			ignorable = this.getAnnotation(TIgnoreField.class) != null;
		
		return ignorable;
	}
	
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#getFieldName()
	 */
	@Override
	public String getFieldName(){
		return field.getName();
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#getFieldType()
	 */
	@Override
	public Class<?> getFieldType(){
		return field.getType();
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#isLoaded()
	 */
	@Override
	public synchronized boolean isLoaded() {
		return loaded.get();
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#loadedProperty()
	 */
	@Override
	public ReadOnlyBooleanProperty loadedProperty() {
		return this.loaded;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#getAnnotations()
	 */
	@Override
	public List<Annotation> getAnnotations() {
		return Arrays.asList(field.getDeclaredAnnotations());
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#getField()
	 */
	@Override
	public Field getField() {
		return field;
	}
	
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, true);
	}
	
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#hashCode()
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, true);
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#getArrControl()
	 */
	@Override
	public Object[] getArrControl() {
		return arrControl;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#getArrLayout()
	 */
	@Override
	public Object[] getArrLayout() {
		return arrLayout;
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#hasControl()
	 */
	@Override
	public boolean hasControl() {
		return arrControl!=null;
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#hasLayout()
	 */
	@Override
	public boolean hasLayout() {
		return arrLayout!=null;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#getOrder()
	 */
	@Override
	public int getOrder() {
		return order;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#setOrder(int)
	 */
	@Override
	public void setOrder(int order) {
		this.order = order;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#componentLoadedProperty()
	 */
	@Override
	public ReadOnlyBooleanProperty componentLoadedProperty() {
		return componentLoaded;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#setComponentLoaded(boolean)
	 */
	@Override
	public void setComponentLoaded(boolean loaded) {
		this.componentLoaded.setValue(loaded);
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#isComponentLoaded()
	 */
	@Override
	public boolean isComponentLoaded() {
		return componentLoaded.get();
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#layoutLoadedProperty()
	 */
	@Override
	public ReadOnlyBooleanProperty layoutLoadedProperty() {
		return layoutLoaded;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#setLayoutLoaded(boolean)
	 */
	@Override
	public void setLayoutLoaded(boolean loaded) {
		this.layoutLoaded.setValue(loaded);
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#isLayoutLoaded()
	 */
	@Override
	public boolean isLayoutLoaded() {
		return layoutLoaded.get();
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#getLayout()
	 */
	@Override
	public Node getLayout() {
		return layout;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#setLayout(javafx.scene.Node)
	 */
	@Override
	public void setLayout(Node layout) {
		this.layout = layout;
		this.layoutLoaded.setValue(layout!=null);
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#getComponent()
	 */
	@Override
	public Node getComponent() {
		return component;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#setComponent(javafx.scene.Node)
	 */
	@Override
	public void setComponent(Node component) {
		this.component = component;
		this.componentLoaded.setValue(component!=null);
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#hasParent()
	 */
	@Override
	public boolean hasParent() {
		return hasParent;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#setHasParent(boolean)
	 */
	@Override
	public void setHasParent(boolean hasParent) {
		this.hasParent = hasParent;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#getControl()
	 */
	@Override
	public Node getControl() {
		return control;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.descriptor.ITFieldDescriptor#setControl(javafx.scene.Node)
	 */
	@Override
	public void setControl(Node control) {
		this.control = control;
	}

	@Override
	public <T extends Annotation> T getAnnotation(Class<T> type) {
		return field.getAnnotation(type);
	}

}
