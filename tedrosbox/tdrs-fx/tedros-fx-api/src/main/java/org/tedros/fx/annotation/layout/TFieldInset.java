package org.tedros.fx.annotation.layout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.scene.control.TInsets;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface TFieldInset {
	
	public String field() default "";
	public TInsets insets() default @TInsets;
	
	
}
