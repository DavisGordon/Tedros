package com.tedros.fxapi.descriptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.fxapi.annotation.TIgnoreField;
import com.tedros.fxapi.util.TReflectionUtil;

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
public class TFieldDescriptor {
	
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
	
	public boolean isIgnorable(){
		if(ignorable==null) {
			ignorable = false;
			for (Annotation annotation : this.getAnnotations())
				if(annotation instanceof TIgnoreField) {
					ignorable = true;
					break;
				}
		}
		return ignorable;
	}
	
	
	
	/**
	 * <pre>
	 * Return the field name
	 * </pre>
	 * 
	 * @return {@link String}
	 * */
	public String getFieldName(){
		return field.getName();
	}
	
	/**
	 * <pre>
	 * Return the field type
	 * </pre>
	 * 
	 * @return {@link Class}
	 * */
	public Class<?> getFieldType(){
		return field.getType();
	}
	
	/**
	 * <pre>
	 * Return true if the field was loaded/builded into a node.
	 * </pre>
	 * 
	 * @return {@link Boolean}
	 * */
	public synchronized boolean isLoaded() {
		return loaded.get();
	}

	
	
	public ReadOnlyBooleanProperty loadedProperty() {
		return this.loaded;
	}

	/**
	 * <pre>
	 * Return a {@link List} of {@link Annotation} used in the field. 
	 * </pre>
	 * 
	 * @return {@link List} of {@link Annotation}
	 * */
	public List<Annotation> getAnnotations() {
		return Arrays.asList(field.getDeclaredAnnotations());
	}
	
	/**
	 * <pre>
	 * Return the {@link Field}
	 * </pre>
	 * 
	 * @return {@link Field}
	 * */
	public Field getField() {
		return field;
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, true);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, true);
	}

	/**
	 * @return the arrControl
	 */
	public Object[] getArrControl() {
		return arrControl;
	}

	/**
	 * @return the arrLayout
	 */
	public Object[] getArrLayout() {
		return arrLayout;
	}
	
	public boolean hasControl() {
		return arrControl!=null;
	}
	
	public boolean hasLayout() {
		return arrLayout!=null;
	}

	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}


	/**
	 * @return the componentLoaded
	 */
	public ReadOnlyBooleanProperty componentLoadedProperty() {
		return componentLoaded;
	}

	/**
	 * @param componentLoaded the componentLoaded to set
	 */
	public void setComponentLoaded(boolean loaded) {
		this.componentLoaded.setValue(loaded);
	}

	public boolean isComponentLoaded() {
		return componentLoaded.get();
	}
	
	/**
	 * @return the layoutLoaded
	 */
	public ReadOnlyBooleanProperty layoutLoadedProperty() {
		return layoutLoaded;
	}

	/**
	 * @param loaded the layoutLoaded to set
	 */
	public void setLayoutLoaded(boolean loaded) {
		this.layoutLoaded.setValue(loaded);
	}

	public boolean isLayoutLoaded() {
		return layoutLoaded.get();
	}

	/**
	 * @return the layout
	 */
	public Node getLayout() {
		return layout;
	}

	/**
	 * @param layout the layout to set
	 */
	public void setLayout(Node layout) {
		this.layout = layout;
		this.layoutLoaded.setValue(layout!=null);
	}

	/**
	 * @return the component
	 */
	public Node getComponent() {
		return component;
	}

	/**
	 * @param component the component to set
	 */
	public void setComponent(Node component) {
		this.component = component;
		this.componentLoaded.setValue(component!=null);
	}

	/**
	 * @return the hasParent
	 */
	public boolean hasParent() {
		return hasParent;
	}

	/**
	 * @param hasParent the hasParent to set
	 */
	public void setHasParent(boolean hasParent) {
		this.hasParent = hasParent;
	}

	/**
	 * @return the control
	 */
	public Node getControl() {
		return control;
	}

	/**
	 * @param control the control to set
	 */
	public void setControl(Node control) {
		this.control = control;
	}

	/**
	 * @param loaded the loaded to set
	 *//*
	public void setLoaded(Boolean loaded) {
		if(this.loaded.isBound())
			this.loaded.unbind();
		this.loaded.setValue(loaded);
	}*/
	
}
