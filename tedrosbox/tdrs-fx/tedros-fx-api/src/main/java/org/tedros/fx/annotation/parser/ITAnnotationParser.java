package org.tedros.fx.annotation.parser;

import java.lang.annotation.Annotation;

import org.tedros.fx.descriptor.TComponentDescriptor;

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
	 * Return the {@link TComponentDescriptor} with component information to build.
	 * </pre>
	 * */
	public TComponentDescriptor getComponentDescriptor();
	
	/**
	 * <pre>
	 * Set the {@link TComponentDescriptor} with component information to build.
	 * </pre>
	 * */
	public void setComponentDescriptor(TComponentDescriptor componentDescriptor);
	
	/**
	 * <pre>
	 * Parse the annotation to the component.
	 * </pre>
	 * */
	public void parse(final A annotation, final T object, String...byPass) throws Exception;
	
}