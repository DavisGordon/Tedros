package com.tedros.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.core.image.TImageView;

/**
 * Define a module of an application
 * 
 * @author Davis Gordon
 * */
@Target(value={ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface TModule {
	
	/**
	 * The module name
	 * */
	public String name();
	
	/**
	 * The module menu item description  
	 * */
	public String menu();
	
	/**
	 * The module version
	 * */
	public String version() default "";
	
	/**
	 * The module description
	 * */
	public String description() default "";
	
	/**
	 * The module class
	 * */
	public Class<? extends com.tedros.core.TModule> type();
	
	/**
	 * The module icon to show in app painel
	 * */
	public Class<? extends TImageView> icon() default TImageView.class;
	
	/**
	 * The module icon to show in menu
	 * */
	public Class<? extends TImageView> menuIcon() default TImageView.class;
	

}
