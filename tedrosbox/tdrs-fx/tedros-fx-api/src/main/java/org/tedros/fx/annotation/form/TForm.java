package org.tedros.fx.annotation.form;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.api.form.ITModelForm;
import org.tedros.fx.form.TDefaultForm;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@SuppressWarnings("rawtypes")
public @interface TForm {
	
	public Class<? extends ITModelForm> form() default TDefaultForm.class;
	
	public boolean showBreadcrumBar() default false;
	
	public String name();
	
	public String editCssId() default "t-form";
	
	public String readerCssId() default "t-reader";
	
	public String style() default "";
	
	public boolean scroll() default true;
}
