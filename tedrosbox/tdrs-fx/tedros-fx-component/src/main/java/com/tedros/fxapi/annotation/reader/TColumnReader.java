/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 09/03/2014
 */
package com.tedros.fxapi.annotation.reader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.fxapi.annotation.TBooleanValues;
import com.tedros.fxapi.annotation.control.TConverter;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;

/**
 * <pre>
 * </pre>
 *
 * @author Davis Gordon
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TColumnReader {
	
	/**
	 * <pre>
	 * The field name of modelView or entity to show at column.
	 * </pre>
	 * */
	public String field();
	
	/**
	 * <pre>
	 * The column name.
	 * </pre>
	 * */
	public String name();
	
	/**
	* <pre>
	*  Define a html to wrap the control value. 
	*  
	*  Important: The key %STYLE% and %CONTENT% must in the html string to 
	*  be replaced.
	*  
	*  Default value: &lt;span id='contentControlValue' style='%STYLE%'&gt;%CONTENT%&lt;/span&gt;
	* </pre>
	**/
	public String fieldValueHtmlTemplate() default "<span style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</span>";
	
	/**
	* <pre>
	*  Sets a css string to be used at the tag defined in htmlTemplateForControlValue(). 
	*  
	*  Default value: margin:2px;
	* </pre>
	**/
	public String fieldValueHtmlStyle() default "font-size: "+TStyleParameter.READER_TEXT_SIZE+"em; color: "+TStyleParameter.READER_TEXT_COLOR+"; ";
	
	/**
	* <pre>  
	*  Default value: ;
	* </pre>
	**/
	public String columnHeaderHtmlStyle() default "border-bottom: 1px solid "+TStyleParameter.PANEL_BACKGROUND_COLOR+";";
	
	/**
	* <pre>  
	*  Default value: ;
	* </pre>
	**/
	public String columnHeaderHtmlAttribute() default "align='left'";
	
	/**
	* <pre>
	*  
	* </pre>
	**/
	public String columnHtmlStyle() default "text-align:center; border-bottom: 1px solid "+TStyleParameter.PANEL_BACKGROUND_COLOR+";";
	
	/**
	* <pre>  
	*  Default value: ;
	* </pre>
	**/
	public String columnHtmlAttribute() default "align='left'";
	
	/**
	* <pre>
	*  Specifies a mask for the value.  
	*  
	*  Default value: empty string
	* </pre>
	**/
	public String mask() default "";
	
	/**
	* <pre>
	*  Specifies a date pattern for Date fields.  
	*  
	*  Default value: dd/MM/yyyy
	* </pre>
	**/
	public String datePattern() default "dd/MM/yyyy";
	
	/**
	 * <pre>
	 *  Specifies a description for boolean fields.  
	 *  
	 *  Default value: @TBooleanValues()
	 * </pre> 
	 * */
	public TBooleanValues booleanValues() default @TBooleanValues();
	
	/**
	 * Specifies a converter class. 
	 * */
	public TConverter converter() default @TConverter(type=com.tedros.fxapi.form.TConverter.class, parse = false);
	
}
