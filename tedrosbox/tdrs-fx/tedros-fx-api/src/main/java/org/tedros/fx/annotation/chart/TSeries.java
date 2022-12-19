package org.tedros.fx.annotation.chart;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TSeries {
	//public Class<?> type();
	public String  name(); 
	public String xField() default "";
	public String yField() default "";
	public TData[] data() default {};
	
}
