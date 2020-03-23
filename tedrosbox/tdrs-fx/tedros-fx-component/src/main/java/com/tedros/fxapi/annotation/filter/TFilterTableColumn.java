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

import com.tedros.fxapi.control.TTableCell;

import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TFilterTableColumn  {
	public String columnName();
	public String fieldName();
	@SuppressWarnings("rawtypes")
	public Class<? extends TableColumn> customTableColumn() default TableColumn.class;
	public Pos alignment() default Pos.CENTER;
	public int order();
	public int width() default 0;
	public int minWidth() default 0;
	public int maxWidth() default 0;
	public String mask() default "";
	public String datePattern() default "";
	@SuppressWarnings("rawtypes")
	public Class<? extends TTableCell> tableCellClass() default TTableCell.class;
	
	public String componentId() default "";
}
