package com.tedros.fxapi.annotation.process;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.ejb.base.entity.ITEntity;

/**
 * <p>
 * The {@link com.tedros.fxapi.process.TEntityProcess} to be executed.
 * 
 * This process is used for CRUD operations on the server side.
 * 
 * 
 * 
 * @author Davis Gordon
 * @see {@link TModelProcess}
 * </p>
 * <code><br><br>
 * 		@TListViewPresenter
 * 		@TEntityProcess(process=ProjetoProcess.class, entity=Projeto.class)<br>
 * 		public class ProjetoModelView extends TEntityModelView<Projeto> {<br> 			
 * 			private SimpleLongProperty id;<br>
 * 		}<br>
 * </code>
 * 
 * 
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TEntityProcess {
	
	/**
	 * <pre>
	 * The {@link ITEntity} to be process. 
	 * </pre>
	 * */
	public Class<? extends ITEntity> entity();
	
	/**
	 * The ejb jndi name to lookup the service, this must implement ITEjbController
	 * 
	 * @see ITEjbController
	 * *
	public String serviceName();*/
	
	/**
	 * <pre>
	 * The {@link com.tedros.fxapi.process.TEntityProcess} to be executed.
	 * 
	 *  Default value: com.tedros.fxapi.process.TEntityProcess.class
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends com.tedros.fxapi.process.TEntityProcess> process()/* 
	default com.tedros.fxapi.process.TEntityProcess.class*/;
}
