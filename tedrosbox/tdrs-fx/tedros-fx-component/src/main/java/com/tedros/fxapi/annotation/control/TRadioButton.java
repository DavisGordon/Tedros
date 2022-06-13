package com.tedros.fxapi.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.geometry.Pos;

/**
 * A set of information to build a TRadioButton
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TRadioButton {
	
	
	public String text();
	public String userData(); 
	public Pos alignment() default Pos.CENTER_LEFT;
	public boolean disable() default false;
	public boolean wrapText() default true;
	public int width() default -1; 
	public int height() default -1;
	public boolean selected() default false;
	public boolean visible() default true;
	public double opacity() default -1;
	public String style() default "";
	public String[] styleClass() default {};
	public String toolTip() default "";
	public String componentId() default "";

}
