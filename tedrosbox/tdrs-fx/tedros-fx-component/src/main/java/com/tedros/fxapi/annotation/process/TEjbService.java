package com.tedros.fxapi.annotation.process;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.model.ITModel;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
/**
 * <pre>
 * The Ejb service settings
 * </pre>
 * @author Davis Gordon
 * */
public @interface TEjbService {
	/**
	 * <pre>
	 * The {@link ITModel} to be process. 
	 * </pre>
	 * */
	public Class<? extends ITModel> model();
	
	/**
	 * <pre>
	 * The EJB service name
	 * </pre>
	 * */
	public String serviceName();
	
	/**
	 * <pre>
	 * The service mode.
	 * 
	 * Default value: true
	 * </pre>
	 * */
	public boolean remoteMode() default true;
}
