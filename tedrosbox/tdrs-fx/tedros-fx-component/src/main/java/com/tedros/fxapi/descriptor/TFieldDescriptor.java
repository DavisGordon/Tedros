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
public class TFieldDescriptor  {
	
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
	
	
	public String getFieldName(){
		return field.getName();
	}
	
	public Class<?> getFieldType(){
		return field.getType();
	}
	
	public synchronized boolean isLoaded() {
		return loaded.get();
	}

	public ReadOnlyBooleanProperty loadedProperty() {
		return this.loaded;
	}

	public List<Annotation> getAnnotations() {
		return Arrays.asList(field.getDeclaredAnnotations());
	}
	
	public Field getField() {
		return field;
	}
	
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, true);
	}
	
	
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, true);
	}

	public Object[] getArrControl() {
		return arrControl;
	}

	public Object[] getArrLayout() {
		return arrLayout;
	}
	
	public boolean hasControl() {
		return arrControl!=null;
	}
	
	public boolean hasLayout() {
		return arrLayout!=null;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public ReadOnlyBooleanProperty componentLoadedProperty() {
		return componentLoaded;
	}

	public void setComponentLoaded(boolean loaded) {
		this.componentLoaded.setValue(loaded);
	}

	public boolean isComponentLoaded() {
		return componentLoaded.get();
	}
	
	public ReadOnlyBooleanProperty layoutLoadedProperty() {
		return layoutLoaded;
	}

	public void setLayoutLoaded(boolean loaded) {
		this.layoutLoaded.setValue(loaded);
	}

	public boolean isLayoutLoaded() {
		return layoutLoaded.get();
	}

	public Node getLayout() {
		return layout;
	}

	public void setLayout(Node layout) {
		this.layout = layout;
		this.layoutLoaded.setValue(layout!=null);
	}

	public Node getComponent() {
		return component;
	}

	public void setComponent(Node component) {
		this.component = component;
		this.componentLoaded.setValue(component!=null);
	}

	public boolean hasParent() {
		return hasParent;
	}

	public void setHasParent(boolean hasParent) {
		this.hasParent = hasParent;
	}

	public Node getControl() {
		return control;
	}

	public void setControl(Node control) {
		this.control = control;
	}

}
