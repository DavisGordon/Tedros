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

import javafx.collections.ObservableList;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.entity.TEntity;
import com.tedros.fxapi.annotation.TBooleanValues;
import com.tedros.fxapi.annotation.control.TConverter;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.builder.ITReaderHtmlBuilder;
import com.tedros.fxapi.builder.TTableReaderHtmlBuilder;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.presenter.model.TModelView;

/**
 * <pre>
 * Mark a field of {@link TModelView}, {@link TEntityModelView} 
 * or {@link ObservableList} of both types to be read.
 * </pre>
 *
 * @author Davis Gordon
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TTableReaderHtml {

	
	/**
	 *<pre>
	 * The builder of type {@link ITReaderHtmlBuilder} for this component.
	 * 
	 *  Default value: TTableReaderHtmlBuilder.class
	 *</pre> 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITReaderHtmlBuilder> builder() default TTableReaderHtmlBuilder.class;
	
	
	/**
	 * <pre>
	 * The entity class for this detail.    
	 * </pre>
	 * */
	public Class<? extends ITEntity> entityClass() default TEntity.class;
	
	/**
	 * <pre>
	 * The modelView class for this detail.    
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TModelView> modelViewClass() default TModelView.class;
	
	/**
	 * <pre>
	 * The fields of the modelView or entity class to show at detail form.
	 * 
	 * Default value: * (Show all fields)
	 * </pre>
	 * */
	public TColumnReader[] column(); 
	
	/**
	* <pre>
	*  Sets a css string to be used at the tag which will represent a label. 
	*  
	*  Default value: font-weight: bold; margin:2px;
	* </pre>
	**/
	public String cssForLabelValue() default "font-weight: bold; margin:2px; color: "+TStyleParameter.READER_LABEL_COLOR+"; font-size: "+TStyleParameter.READER_LABEL_SIZE+"em;";
	
	/**
	* <pre>
	*  Sets a css string to be used at the tag which will wrap a value of a control and his label.
	*  
	*  Default value: margin:4px;
	* </pre>
	**/
	public String cssForHtmlBox() default "margin:4px;";
	
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
	*  Sets a css string to be used at the tag which will represent a label. 
	*  
	*  Default value: font-weight: bold; margin:2px;
	* </pre>
	**/
	public String labelHtmlStyle() default "font-weight: bold; margin:2px; color: "+TStyleParameter.READER_LABEL_COLOR+"; font-size: "+TStyleParameter.READER_LABEL_SIZE+"em;";
	
	
	/**
	* <pre>
	*  
	*  Default value: ;
	* </pre>
	**/
	public String tableHtmlStyle() default "width: 100%;";
	
	/**
	* <pre>  
	*  Default value: ;
	* </pre>
	**/
	public String tableHtmlAttribute() default "";
	
	/**
	* <pre>  
	*  Default value: ;
	* </pre>
	**/
	public String rowHeaderHtmlStyle() default "";
	
	/**
	* <pre>  
	*  Default value: ;
	* </pre>
	**/
	public String rowHeaderHtmlAttribute() default "";
	
	/**
	* <pre>
	* </pre>
	**/
	public String rowHtmlStyle() default "margin:1px;";
	
	/**
	* <pre>  
	*  Default value: ;
	* </pre>
	**/
	public String rowHtmlAttribute() default "";
	
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
	 * <pre>
	 *  The label for the reader field
	 * </pre> 
	 * */
	public TLabel label() default @TLabel(text="");
	
	/**
	 * Specifies a converter class. 
	 * */
	public TConverter converter() default @TConverter(type=com.tedros.fxapi.form.TConverter.class, parse = false);
	
	/**
	 * <pre>
	 * Hide all labels inside the detail form.    
	 * </pre>
	 * */
	public boolean suppressLabels() default false;
	
	
}
