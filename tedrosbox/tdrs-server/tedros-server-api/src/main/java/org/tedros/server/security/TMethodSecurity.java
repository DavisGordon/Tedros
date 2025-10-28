package org.tedros.server.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({METHOD })
@Retention(RUNTIME)
public @interface TMethodSecurity {
	
	/**
	 * The security policies defined in the application to be respected on the server.
	 * */
	public TMethodPolicie[] value() default {};
	
}