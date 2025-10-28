package org.tedros.api.parser;

import java.lang.annotation.Annotation;

import org.tedros.api.descriptor.ITComponentDescriptor;

/**
 * <pre>
 * Interface for the annotation parser.
 * </pre>
 * 
 * @author Davis Gordon
 * */
public interface ITAnnotationParser<A extends Annotation, T> {
	
	/**
	 * <pre>
	 * Return the {@link ITComponentDescriptor} with component information to build.
	 * </pre>
	 * */
	public ITComponentDescriptor getComponentDescriptor();
	
	/**
	 * <pre>
	 * Set the {@link ITComponentDescriptor} with component information to build.
	 * </pre>
	 * */
	public void setComponentDescriptor(ITComponentDescriptor componentDescriptor);
	
	/**
	 * <pre>
	 * Parse the annotation to the component.
	 * </pre>
	 * */
	public void parse(final A annotation, final T object, String...byPass) throws Exception;
	
}