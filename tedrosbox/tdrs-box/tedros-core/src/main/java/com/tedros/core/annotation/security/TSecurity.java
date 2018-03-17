package com.tedros.core.annotation.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.FIELD })
public @interface TSecurity {
	
	public String id();
	
	public String appName();
	
	public String moduleName() default "";
	
	public String viewName() default "";
	
	public String description() default "";
	
	public TAuthorizationType[] allowedAccesses();
	
	/*public TAllowedAccess[] allowedAccesses() default {
		@TAllowedAccess({TAuthorizationType.APP_ACCESS, 
		TAuthorizationType.MODULE_ACCESS})
		};*/

}
