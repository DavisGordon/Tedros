/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 08/11/2013
 */
package com.tedros.fxapi.annotation.filter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.fxapi.form.ITFilterForm;
import com.tedros.fxapi.form.TDefaultFilterForm;
import com.tedros.fxapi.process.TFilterProcess;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TFilter  {
	@SuppressWarnings("rawtypes")
	public Class<? extends ITFilterForm> formClass() default TDefaultFilterForm.class;
	public Class<? extends TFilterProcess> filterProcessClass();
	
	public String fields();
	
}
