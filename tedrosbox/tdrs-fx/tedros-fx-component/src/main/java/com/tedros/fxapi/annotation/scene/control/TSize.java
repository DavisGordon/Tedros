package com.tedros.fxapi.annotation.scene.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.fxapi.annotation.TSizeConverter;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface TSize {
	
	public Class<? extends TSizeConverter> converter() default TSizeConverter.class;
	
	public double width();
	public double height();
	
	
}
