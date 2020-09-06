/**
 * 
 */
package com.tedros.fxapi.annotation.view;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ANNOTATION_TYPE})
public @interface TOption {

	public String text();
	public String value();
}
