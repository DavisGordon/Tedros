package com.tedros.fxapi.annotation.layout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.fxapi.annotation.parser.ITAnnotationParser;
import com.tedros.fxapi.annotation.parser.THGrowParser;

/**
 * <pre>
 * 
 * THGrow set the HBox hgrow priority. 
 * 
 * Oracle documentation:
 * 
 * Sets the horizontal grow priority for the child when contained by an hbox. 
 * If set, the hbox will use the priority to allocate additional space if the 
 * hbox is resized larger than it's preferred width. If multiple hbox children 
 * have the same horizontal grow priority, then the extra space will be split 
 * evening between them. If no horizontal grow priority is set on a child, the 
 * hbox will never allocate it additional horizontal space if available. 
 * Setting the value to null will remove the constraint.
 * 
 * </pre>
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface THGrow {
	
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {THGrowParser.class};
	
	public TPriority[] priority() default {} ; 
}
