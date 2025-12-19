package org.tedros.fx.annotation.process;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.server.controller.ITEjbController;
import org.tedros.server.model.ITModel;


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
	Class<? extends ITModel> model();
	
	/**
	 * The ejb jndi name to lookup the service, this must implement ITEjbController
	 * 
	 * @see ITEjbController
	 * */
	String serviceName();
	
	/**
	 * <pre>
	 * The service mode.
	 * 
	 * Default value: true
	 * </pre>
	 * */
	boolean remoteMode() default true;
	
	/**
	 * <pre>
	 * Filter by logged user, if the controller 
	 * implements ITSecureEjbController.
	 * 
	 * Default value: false
	 * </pre>
	 * */
	boolean filterByLoggedUser() default false;
}
