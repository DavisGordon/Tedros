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

import javafx.scene.control.TableView;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TFilterTableView  {
	public TFilterTableColumn[] columns();
	@SuppressWarnings("rawtypes")
	public Class<? extends TableView> tableViewClass() default TableView.class; 
	public int width() default 0;
	public int height() default 0;
	public String title() default "";
	
	public String componentId() default "";
	
}
