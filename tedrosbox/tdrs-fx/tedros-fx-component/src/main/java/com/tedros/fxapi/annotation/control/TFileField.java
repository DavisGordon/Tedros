/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 23/03/2014
 */
package com.tedros.fxapi.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.core.model.ITModelView;
import com.tedros.fxapi.annotation.parser.ITAnnotationParser;
import com.tedros.fxapi.annotation.parser.TFileFieldParser;
import com.tedros.fxapi.annotation.parser.TStackPaneParser;
import com.tedros.fxapi.annotation.reader.TByteArrayReader;
import com.tedros.fxapi.annotation.reader.TFileReader;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.scene.control.TInsets;
import com.tedros.fxapi.annotation.scene.layout.TRegion;
import com.tedros.fxapi.builder.ITFileBuilder;
import com.tedros.fxapi.builder.TFileFieldBuilder;
import com.tedros.fxapi.control.action.TEventHandler;
import com.tedros.fxapi.domain.TDefaultValues;
import com.tedros.fxapi.domain.TFileExtension;
import com.tedros.fxapi.domain.TFileModelType;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.property.TSimpleFileProperty;

import javafx.beans.property.Property;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * <pre>
 * Build a {@link com.tedros.fxapi.control.TFileField} component.

 * Types supported:
 * 
 * {@link TSimpleFileEntityProperty}, {@link TSimpleFileProperty}, {@link Property}&lt;byte[]&gt;
 * 
 * Example: 
 * 
 * In a TModel implementation we have:
 * 
 *  <i>@</i>Entity
 *  <i>@</i>Table(name = DomainTables.product, schema = DomainSchema.main)
 *  public class Product extends TEntityImpl{
 *     ...
 *     <i>@</i>OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
 *     private TFileEntity imagem;
 *     ...
 *  }
 *  
 * and in the {@link ITModelView}: 
 *  
 *  public class ProductModelView extends TEntityModelView&lt;Product&gt; {
 *     ...
 *     <i>@</i>TFileReader
 *     <i>@</i>TLabel(text = "Imagem", control=<i>@</i>TControl(prefWidth=500))
 *     <strong><i>@</i>TFileField(extensions = TFileExtension.ALL_FILES, required = false, preLoadFileBytes=false, showImage=true)</strong>
 *     private TSimpleFileEntityProperty<ITFileEntity> imagem;
 *     ...
 *  }
 * 
 * </pre>
 * 
 * @see TByteArrayReader
 * @see TFileReader
 * @see ITModelView
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TFileField {
	
	/**
	 *<pre>
	 * The builder of type {@link ITFileBuilder} for this component.
	 * 
	 *  Default value: {@link TFileFieldBuilder}
	 *</pre> 
	 * */
	public Class<? extends ITFileBuilder<com.tedros.fxapi.control.TFileField>> builder() default TFileFieldBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TRequiredNumeberFieldParser.class, TNumberFieldParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TFileFieldParser.class, TStackPaneParser.class};
	
	/**
	 * <pre>
	 * The {@link Node} settings.
	 * </pre>
	 * */
	public TNode node() default @TNode(parse = false);
	
	/**
	 * <pre>
	 * The {@link Control} settings.
	 * 
	 * Default value: @TControl(prefWidth=250) 
	 * </pre>
	 * */
	public TControl control() default @TControl(prefWidth=TDefaultValues.LABEL_WIDTH, parse = true);
	
	/**
	 * <pre>
	 * The {@link TextInputControl} settings.
	 * </pre>
	 * */
	public TTextInputControl textInputControl() default @TTextInputControl(parse = false);
	
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
	*  Sets the margin for the child when contained by a stackpane. 
	*  If set, the stackpane will layout the child with the margin space around it.
	* </pre>
	**/
	public TInsets margin() default @TInsets;

	/**
	* <pre>
	* {@link StackPane} Class
	* 
	*  Sets the value of the property alignment. 
	*  
	*  Property description: 
	*  
	*  The default alignment of children within the stackpane's width and height. 
	*  This may be overridden on individual children by setting the child's alignment constraint.
	* </pre>
	**/
	public Pos alignment() default Pos.CENTER_LEFT;
	
	
	/**
	 * Specify the Property value type.
	 * */
	public TFileModelType propertyValueType() default TFileModelType.NONE;
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TFileField} Class
	 * 
	 * Sets the value for the fileNameField property.
	 * 
	 * Property description:
	 * 
	 * Defines the field in {@link TModelView} with the file name information.
	 * </pre>
	 * */
	public String fileNameField() default "";
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TFileField} Class
	 * 
	 * Sets the value for the extensions property.
	 * 
	 * Property description:
	 * 
	 * Defines the valid file extensions.
	 * </pre>
	 * */
	public TFileExtension[] extensions() default {TFileExtension.ALL_FILES};
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TFileField} Class
	 * 
	 * Sets the value for the moreExtensions property.
	 * 
	 * Property description:
	 * 
	 * Add more valid file extensions.
	 * </pre>
	 * */
	public String[] moreExtensions() default {};
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TFileField} Class
	 * 
	 * Sets the value for the minFileSize property.
	 * 
	 * Property description:
	 * 
	 * Defines the minimum file size.
	 * </pre>
	 * */
	public double minFileSize() default -1;
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TFileField} Class
	 * 
	 * Sets the value for the maxFileSize property.
	 * 
	 * Property description:
	 * 
	 * Defines the maximum file size.
	 * </pre>
	 * */
	public double maxFileSize() default -1;
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TFileField} Class
	 * 
	 * Sets the value for the minImageWidth property.
	 * 
	 * Property description:
	 * 
	 * If the file is a valid image, defines the minimum width.
	 * </pre>
	 * */
	public double minImageWidth() default -1;
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TFileField} Class
	 * 
	 * Sets the value for the maxImageWidth property.
	 * 
	 * Property description:
	 * 
	 * If the file is a valid image, defines the maximum width.
	 * </pre>
	 * */
	public double maxImageWidth() default -1;
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TFileField} Class
	 * 
	 * Sets the value for the minImageHeight property.
	 * 
	 * Property description:
	 * 
	 * If the file is a valid image, defines the minimum height.
	 * </pre>
	 * */
	public double minImageHeight() default -1;
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TFileField} Class
	 * 
	 * Sets the value for the maxImageHeight property.
	 * 
	 * Property description:
	 * 
	 * If the file is a valid image, defines the maximum height.
	 * </pre>
	 * */
	public double maxImageHeight() default -1;
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TFileField} Class
	 * 
	 * Sets the value for the preLoadFileBytes property.
	 * 
	 * Property description:
	 * 
	 * If true the bytes of the file will load at start.
	 * 
	 * Default value: false.
	 * </pre>
	 * */
	public boolean preLoadFileBytes() default false;
	
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
	public boolean showImage() default false;
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TFileField} Class
	 * 
	 * Sets the value of the property showFilePath.
	 * 
	 * Property description:
	 * 
	 * If true show the directory path.
	 * 
	 * Default value: true.
	 * </pre>
	 * */
	public boolean showFilePath() default true;
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TFileField} Class
	 * 
	 * Sets the value of the property required.
	 * 
	 * Property description:
	 * 
	 * Determines with this control will be required.
	 * 
	 * Default value: false.
	 * </pre>
	 * */
	public boolean required() default false;
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TFileField} Class
	 * 
	 * Sets the value to the openAction property.
	 * 
	 * Property description:
	 * 
	 * Defines the action to the open button
	 * </pre>
	 * */
	public Class<? extends TEventHandler> openAction() default TEventHandler.class;
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TFileField} Class
	 * 
	 * Sets the value to the cleanAction property.
	 * 
	 * Property description:
	 * 
	 * Defines the action to the clean button
	 * </pre>
	 * */
	public Class<? extends TEventHandler> cleanAction() default TEventHandler.class;
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TFileField} Class
	 * 
	 * Sets the value to the loadAction property.
	 * 
	 * Property description:
	 * 
	 * Defines the action to the load button
	 * </pre>
	 * */
	public Class<? extends TEventHandler> loadAction() default TEventHandler.class;
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TFileField} Class
	 * 
	 * Sets the value to the selectAction property.
	 * 
	 * Property description:
	 * 
	 * Defines the action to the select button
	 * </pre>
	 * */
	public Class<? extends TEventHandler> selectAction() default TEventHandler.class;
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TFileField} Class
	 * 
	 * Sets the value to the imageClickAction property.
	 * 
	 * Property description:
	 * 
	 * Defines the action to the image click event
	 * </pre>
	 * */
	public Class<? extends TEventHandler> imageClickAction() default TEventHandler.class;
	
	
}
