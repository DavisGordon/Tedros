package org.tedros.fx.annotation.layout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.fx.annotation.parser.TVGrowParser;

/**
 * <pre>
 * Sets the vertical grow priority for the child when contained by a vbox. 
 * If set, the vbox will use the priority value to allocate additional space 
 * if the vbox is resized larger than its preferred height. If multiple vbox 
 * children have the same vertical grow priority, then the extra space will be 
 * split evenly between them. If no vertical grow priority is set on a child, 
 * the vbox will never allocate any additional vertical space for that child.
 * </pre>
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface TVGrow {
	
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TVGrowParser.class};
	
	public TPriority[] priority() default {} ; 
}
