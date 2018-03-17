/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 23/03/2014
 */
package com.tedros.fxapi.annotation.reader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import com.tedros.core.model.ITModelView;
import com.tedros.fxapi.annotation.control.TConverter;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.parser.TAnnotationParser;
import com.tedros.fxapi.annotation.parser.TStackPaneParser;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.layout.TRegion;
import com.tedros.fxapi.builder.ITReaderBuilder;
import com.tedros.fxapi.builder.TByteArrayReaderBuilder;

/**
 * <pre>
 * Build a {@link com.tedros.fxapi.reader.TByteArrayReader}
 * which is a {@link StackPane} with a {@link ImageView} and a open button. 

 * Types supported:
 * 
 * {@link SimpleObjectProperty}&lt;byte[]&gt;
 * 
 * </pre>
 * 
 * @see TFileReader
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TByteArrayReader {
	
	/**
	 *<pre>
	 * The builder of type {@link ITReaderBuilder} for this component.
	 * 
	 *  Default value: TByteArrayReaderBuilder.class
	 *</pre> 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITReaderBuilder> builder() default TByteArrayReaderBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TStackPaneParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TAnnotationParser>[] parser() default {TStackPaneParser.class};
	
	/**
	 * <pre>
	 * The {@link Node} settings.
	 * </pre>
	 * */
	public TNode node() default @TNode(parse = false);
	
	/**
	 * <pre>
	 * The {@link Region} settings.
	 * </pre>
	 * */
	public TRegion region() default @TRegion(parse = false);
	
	/**
	* <pre>
	* {@link StackPane} Class
	* 
	*  Sets the value of the property alignment. 
	*  
	*  Property description: 
	*  
	*  The overall alignment of children within the vbox's width and height.
	* </pre>
	**/
	public Pos alignment() default Pos.CENTER_LEFT;
		
	/**
	* <pre>
	* {@link com.tedros.fxapi.control.TFileField} Class
	* 
	*  Sets the value of the property fileNameField. 
	*  
	*  Property description: 
	*  
	*  Specifies the {@link ITModelView} property field with the file name information. 
	* </pre>
	**/
	public String fileNameField();
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TFileField} Class
	 * 
	 * Sets the value for the showImage property.
	 * 
	 * Property description:
	 * 
	 * If true the image will be displayed.
	 * 
	 * Default value: false.
	 * </pre>
	 * */
	public boolean showImage() default true;
	
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
	*  Suppress the field label and change it for the reader label.   
	*  
	*  Default value: false
	* </pre>
	**/
	public boolean suppressLabels() default false;
	
	
}

