package org.tedros.fx.annotation.view;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.presenter.model.TModelView;
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
	public Class<? extends TJsonModel> jsonModel();
	

	/**
	 * The model view class
	 * */
	public Class<? extends TModelView> modelViewClass();
}
