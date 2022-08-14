/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 09/03/2014
 */
package org.tedros.fx.annotation.reader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.core.model.ITModelView;
import org.tedros.fx.annotation.TCodeValue;
import org.tedros.fx.annotation.TStringBooleanValues;
import org.tedros.fx.annotation.control.TConverter;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.builder.ITReaderHtmlBuilder;
import org.tedros.fx.builder.TTextReaderHtmlBuilder;
import org.tedros.fx.domain.THtmlConstant;
import org.tedros.server.entity.ITEntity;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.text.Text;

/**
 * <pre>
 * Mark the field as a Reader type.
 * 
 * A reader type field is builded only when the user set the view to reader mode.
 * 
 * Types supported:
 * 
 * {@link SimpleStringProperty}, {@link SimpleBooleanProperty}, {@link SimpleLongProperty}, 
 * {@link SimpleDoubleProperty}, {@link SimpleFloatProperty}, {@link SimpleIntegerProperty}, 
 *  and {@link SimpleObjectProperty} 
 * 
 * Note: 
 * 
 * A field of {@link ITEntity} type in an entity class can be configured as 
 * SimpleObjectProperty&lt;ITEntity&gt; at the {@link ITModelView} representation. 
 * 
 * </pre>
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TTextReaderHtml {
	
	/**
	 *<pre>
	 * The builder of type {@link ITReaderHtmlBuilder} for this component.
	 * 
	 *  Default value: {TReaderHtmlBuilder.class}
	 *</pre> 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITReaderHtmlBuilder> builder() default TTextReaderHtmlBuilder.class;
	
	/**
	* <pre>
	* {@link Text} Class
	* 
	*  Sets the value of the property text. 
	*  
	*  Property description: 
	*  
	*  Defines text string that is to be displayed. 
	*  
	*  Default value: empty string
	* </pre>
	**/
	public String text() default "";
	
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
	public String htmlTemplateForControlValue() default "<span id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</span>";
	
	/**
	* <pre>
	*  Sets a css string to be used at the tag defined in htmlTemplateForControlValue(). 
	*  
	*  Default value: margin:2px;
	* </pre>
	**/
	public String cssForControlValue() default "margin:2px;";
	
	/**
	* <pre>
	*  Sets a css string to be used at the tag which will represent a label. 
	*  
	*  Default value: font-weight: bold; margin:2px;
	* </pre>
	**/
	public String cssForLabelValue() default "font-weight: bold; margin:2px;";
	
	/**
	* <pre>
	*  Sets a css string to be used at the tag which will wrap a value of a control and his label.
	*  
	*  Default value: margin:4px;
	* </pre>
	**/
	public String cssForHtmlBox() default "padding:8px;";
	
	/**
	* <pre>
	*  Sets a css string to be used at the tag that wraps the content in the html box, 
	*  a html box is used to layout the control value and his label.
	*  
	*  Default value: margin:1px;
	* </pre>
	**/
	public String cssForContentValue() default "margin:1px;";
	
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
	public String datePattern() default "";
	
	/**
	 * <pre>
	 *  Specifies a description for string boolean representation.
	 *  
	 *    Valid String booleans for True value: T, t, True, TRUE, true or 1;
	 *    Valid String booleans for False value: F, f, False, FALSE, false or 0;
	 *  
	 *  Default value: @TBooleanValues(falseValue = "#{tedros.fxapi.label.false}", trueValue = "#{tedros.fxapi.label.true}")
	 * </pre> 
	 * */
	public TStringBooleanValues booleanValues() default @TStringBooleanValues(trueCodes="", falseCodes="");
	
	
	
	public TCodeValue[] codeValues() default @TCodeValue(code="", value="");
	
	/**
	 * <pre>
	 *  The label for the reader field
	 * </pre> 
	 * */
	public TLabel label() default @TLabel(text="");
	
	/**
	 * Specifies a converter class. 
	 * */
	public TConverter converter() default @TConverter(type=org.tedros.fx.form.TConverter.class, parse = false);
	
}
