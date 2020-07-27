package com.tedros.fxapi.annotation.process;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.ejb.base.model.ITModel;



/**
 * <pre>
 * The {@link com.tedros.fxapi.process.TModelProcess} to be executed. 
 * </pre>
 * @author Davis Gordon
 * @see {@link TEntityProcess}
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TReportProcess {
	
	/**
	 * <p>
	 * The {@link com.tedros.fxapi.process.TReportProcess} to be executed.
	 * </p>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends com.tedros.fxapi.process.TReportProcess> type();

	public Class<? extends ITModel> model();
	
}
