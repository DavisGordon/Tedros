package com.tedros.fxapi.annotation.text;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.fxapi.domain.TDefaultValues;

import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TFont {
	public String family() default TDefaultValues.FONT_FAMILY;
	public FontWeight weight() default FontWeight.NORMAL;
	public FontPosture posture () default FontPosture.REGULAR;
	public double size() default TDefaultValues.FONT_SIZE;
	 
}
