package com.tedros.fxapi.descriptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * <pre>
 * The field descriptor class, contais all necessary information about the field.  
 * </pre>
 * 
 * @author Davis Gordon
 * */
public class TFieldDescriptor {
	
	private final Field field;
	private boolean loaded;
	
	/**
	 * Initialize this object
	 * */
	public TFieldDescriptor(Field field) {
		this.field = field;
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
	public boolean isLoaded() {
		return loaded;
	}

	/**
	 * <pre>
	 * Set if the field was loaded/builded into a node.
	 * </pre>
	 * */
	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
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
	
}
