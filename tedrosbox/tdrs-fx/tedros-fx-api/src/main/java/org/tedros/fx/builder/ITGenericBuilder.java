package org.tedros.fx.builder;


/**
 * <pre>
 * A generic builder.
 * 
 * T - the object class to build
 * </pre>
 * */
public interface ITGenericBuilder<T> extends ITBuilder {
	
	public T build();
}
