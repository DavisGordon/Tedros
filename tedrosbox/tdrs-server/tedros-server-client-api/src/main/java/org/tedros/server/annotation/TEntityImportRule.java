/**
 * 
 */
package org.tedros.server.annotation;

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
	 * Specify if the file to import has a header and his type.  
	 * 
	 * Any type different of THeaderType.COLUMN_INDEX force the usage of 
	 * the first row of the file as column name header. 
	 * 
	 * For THeaderType.FIELD_NAME the file must have the first row with 
	 * columns named as the field names of the entity.
	 *  
	 * For THeaderType.COLUMN_NAME the file must have the first row with 
	 * columns named as same value of the property column in the 
	 * TFieldImportRule annotation of the correspondent entity field.
	 * 
	 * For THeaderType.COLUMN_INDEX the import process assume the has no header and the first row will
	 * be used as data to create an entity and  the property column in the TFieldImportRule must have
	 * the file column index to be used to identify the correspondent data.
	 * </pre>
	 * @default THeaderType.COLUMN_NAME
	 * */
	THeaderType header() default THeaderType.COLUMN_NAME;
}
