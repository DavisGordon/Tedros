package com.tedros.fxapi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/***
 * Mark a field to be ignored by the model view engine.
 * 
 * @author Davis Gordon
 * 
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TIgnoreField {

}
