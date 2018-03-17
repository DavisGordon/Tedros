package com.tedros.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.core.image.TImageView;

@Target(value={ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface TModule {
	
	public String name();
	public String menu();
	public String version() default "";
	public String description() default "";
	public Class<? extends com.tedros.core.TModule> type(); 
	public Class<? extends TImageView> icon() default TImageView.class;
	public Class<? extends TImageView> menuIcon() default TImageView.class;
	

}
