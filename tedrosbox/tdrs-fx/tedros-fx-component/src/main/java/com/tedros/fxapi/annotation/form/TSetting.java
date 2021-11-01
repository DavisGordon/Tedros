/**
 * 
 */
package com.tedros.fxapi.annotation.form;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
/**
 * @author Davis Gordon
 *
 */
@Documented
@Retention(RUNTIME)
@Target({ TYPE })
public @interface TSetting {

	Class<? extends com.tedros.fxapi.form.TSetting> value();
}
