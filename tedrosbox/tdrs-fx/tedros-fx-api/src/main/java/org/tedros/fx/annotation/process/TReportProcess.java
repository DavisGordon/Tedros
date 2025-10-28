package org.tedros.fx.annotation.process;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.server.model.ITModel;



/**
 * <pre>
 * The {@link org.tedros.fx.process.TModelProcess} to be executed. 
 * </pre>
 * @author Davis Gordon
 * @see {@link TEntityProcess}
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TReportProcess {
	
	/**
	 * <p>
	 * The {@link org.tedros.fx.process.TReportProcess} to be executed.
	 * </p>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends org.tedros.fx.process.TReportProcess> type();

	public Class<? extends ITModel> model();
	
}
