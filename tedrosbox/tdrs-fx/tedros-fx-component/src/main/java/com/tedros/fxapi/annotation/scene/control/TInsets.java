package com.tedros.fxapi.annotation.scene.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.fxapi.annotation.TInsetsConverter;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TInsets {
	
	public Class<? extends TInsetsConverter> converter() default TInsetsConverter.class;
	
	public double top() default 0;
	public double right() default 0;
	public double bottom() default 0;
	public double left() default 0;

}
