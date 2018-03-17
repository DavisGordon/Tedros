package com.tedros.fxapi.annotation.layout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.scene.layout.Priority;

/**
 * <pre>
 * 
 * TPriority set the {@link Priority} param.
 * 
 * Oracle documentation:
 * 
 * Enumeration used to determine the grow (or shrink) priority of 
 * a given node's layout area when its region has more (or less) 
 * space available and multiple nodes are competing for that space.
 * </pre>
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface TPriority {

	/**
	 * The field name to be setted. 
	 * */
	public String field() default "";
	
	/**
	 * The {@link Priority} setting.
	 * */
	public Priority priority() default Priority.ALWAYS;
	
	
}
