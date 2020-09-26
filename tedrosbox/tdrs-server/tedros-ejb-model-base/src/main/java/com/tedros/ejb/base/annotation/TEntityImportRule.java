/**
 * 
 */
package com.tedros.ejb.base.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
/**
 * The type rules to import for an entity 
 * 
 * @author Davis Gordon
 *
 */
public @interface TEntityImportRule {

	/**
	 * A relevant description for the content
	 * */
	String description();
	
	/**
	 * The file types acceptable to import
	 * */
	TFileType[] fileType();
	
	/**
	 * The total columns the file must contains
	 * */
	int totalColumn() default -1; 
	
	/**
	 * The XLS sheet name if applicable
	 * */
	String xlsSheetName() default "";
	
	/**
	 * <pre>
	 * Force the usage of the first row of the file as column name header,
	 * the file column names must match with the field names of the entity or with the 
	 * column property in the TFieldImportRule annotation of the field.
	 * 
	 * If false the import process assume the has no header and the first row will
	 * be used as data to create the entity.  
	 * </pre>
	 * @default true
	 * */
	boolean forceFirstRowAsColumnNameHeader() default true;
}
