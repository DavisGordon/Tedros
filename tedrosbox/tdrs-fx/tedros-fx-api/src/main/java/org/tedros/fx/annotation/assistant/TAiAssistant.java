package org.tedros.fx.annotation.assistant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.model.TModelView;
import org.tedros.server.model.TJsonModel;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.ANNOTATION_TYPE, ElementType.TYPE})
public @interface TAiAssistant {
	
	/**
	 * Show the Artificial Inteligence assistant
	 * 
	 * @default false
	 * */
	public boolean show() default false;
	
	/**
	 * The TJsonModel class
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TJsonModel> jsonModel();
	

	/**
	 * The model view class
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TModelView> modelViewClass();
}
