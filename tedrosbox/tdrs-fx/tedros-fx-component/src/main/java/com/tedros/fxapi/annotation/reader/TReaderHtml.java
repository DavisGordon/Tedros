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

import com.tedros.core.model.ITModelView;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.fxapi.annotation.TBooleanValues;
import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.control.TConverter;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.builder.ITReaderHtmlBuilder;
import com.tedros.fxapi.builder.TReaderHtmlBuilder;
import com.tedros.fxapi.domain.THtmlConstant;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

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
public @interface TReaderHtml {
	
	/**
	 *<pre>
	 * The builder of type {@link ITReaderHtmlBuilder} for this component.
	 * 
	 *  Default value: {TReaderHtmlBuilder.class}
	 *</pre> 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITReaderHtmlBuilder> builder() default TReaderHtmlBuilder.class;
	
	/**
	* <pre>
	*  Define a html to wrap the control value. 
	*  
	*  Important: The key %STYLE% and %CONTENT% must in the html string to 
	*  be replaced.
	*  
	*  Default value: 
	*  
	*  &lt;span id='%ID%' name='%NAME%' style='%STYLE%'&gt;%CONTENT%&lt;/span&gt;
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
	 * <pre>Specifies values for codes
	 * 
	 * Example:
	 * <code>
	 * @TReaderHtml(
	 * codeValues={ <b  style='color:green'> @TCodeValue(code="1", value="#{label.identity}")</b>, <b  style='color:red'> @TCodeValue(code="2", value="#{label.cpf}")</b>})
	 * 	
	 * @THorizontalRadioGroup(required=true, alignment=Pos.CENTER_LEFT, spacing=4, 
	 * radioButtons={ <b  style='color:green'> @TRadioButton(text = "#{label.identity}", userData = "1")</b>, <b  style='color:red'> @TRadioButton(text = "#{label.cpf}", userData = "2")</b>})
	 * private SimpleStringProperty tipo;
	 * </code><pre>
	 *  
	 * */
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
	public TConverter converter() default @TConverter(type=com.tedros.fxapi.form.TConverter.class, parse = false);
	
}
