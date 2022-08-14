package org.tedros.server.security;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ TYPE })
@Retention(RUNTIME)
public @interface TBeanSecurity {
	
	/**
	 * The security policies defined in the application to be respected on the server.
	 * */
	public TBeanPolicie[] value() default {};
	
}