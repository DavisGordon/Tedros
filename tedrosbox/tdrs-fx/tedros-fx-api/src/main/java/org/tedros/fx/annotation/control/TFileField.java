/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 23/03/2014
 */
package org.tedros.fx.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.parser.ITAnnotationParser;
import org.tedros.fx.annotation.parser.TFileFieldParser;
import org.tedros.fx.annotation.parser.TStackPaneParser;
import org.tedros.fx.annotation.reader.TByteArrayReader;
import org.tedros.fx.annotation.reader.TFileReader;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.scene.control.TInsets;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.ITFileBuilder;
import org.tedros.fx.builder.TFileFieldBuilder;
import org.tedros.fx.control.action.TEventHandler;
import org.tedros.fx.domain.TDefaultValues;
import org.tedros.fx.domain.TFileExtension;
import org.tedros.fx.domain.TFileModelType;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.fx.property.TSimpleFileProperty;

import javafx.beans.property.Property;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * <pre>
 * Build a {@link org.tedros.fx.control.TFileField} component.

 * Types supported:
 * 
 * {@link TSimpleFileProperty}, {@link Property}&lt;byte[]&gt;
 * 
 * Example: 
 * 
 * The jpa entity :
 * 
 *  <i>@</i>Entity
 *  <i>@</i>Table(name = DomainTables.product, schema = DomainSchema.main)
 *  public class Product extends TEntity{
 *     ...
 *     <i>@</i>OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
 *     private TFileEntity image;
 *     ...
 *  }
 *  
 * and in the {@link TEntityModelView}: 
 *  
 *  public class ProductModelView extends TEntityModelView&lt;Product&gt; {
 *     ...
 *     <i>@</i>TFileReader
 *     <i>@</i>TLabel(text="Image")
 *     <i>@</i>TFieldBox(node=@TNode(id="image", parse=true))
 *     <i>@</i>TFileField(showImage=true, propertyValueType=TFileModelType.ENTITY,
 *     preLoadFileBytes=true, extensions= {TFileExtension.JPG, TFileExtension.PNG},
 *     showFilePath=true)
 *     private TSimpleFileProperty&ltTFileEntity&gt image
 *     ...
 *  }
 * 
 * </pre>
 * 
 * @see TByteArrayReader
 * @see TFileReader
 * @see TEntityModelView
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
	public Class<? extends ITFileBuilder<org.tedros.fx.control.TFileField>> builder() default TFileFieldBuilder.class;
	
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
	 * {@link org.tedros.fx.control.TFileField} Class
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
	 * {@link org.tedros.fx.control.TFileField} Class
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
	 * {@link org.tedros.fx.control.TFileField} Class
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
	 * {@link org.tedros.fx.control.TFileField} Class
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
	 * {@link org.tedros.fx.control.TFileField} Class
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
	 * {@link org.tedros.fx.control.TFileField} Class
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
	 * {@link org.tedros.fx.control.TFileField} Class
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
	 * {@link org.tedros.fx.control.TFileField} Class
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
	 * {@link org.tedros.fx.control.TFileField} Class
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
	 * {@link org.tedros.fx.control.TFileField} Class
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
	 * {@link org.tedros.fx.control.TFileField} Class
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
	 * {@link org.tedros.fx.control.TFileField} Class
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
	 * {@link org.tedros.fx.control.TFileField} Class
	 * 
	 * Sets the initial directory.
	 * 
	 * Property description:
	 * 
	 * See the constants USER_HOME, TEDROS_ROOT, TEDROS_MODULE
	 * 
	 * Default value: USER_HOME
	 * </pre>
	 * */
	public String initialDirectory() default USER_HOME;
	
	/**
	 * The component will change this constant with the user home directory path
	 * */
	static final String USER_HOME = "user.home";
	/**
	 * The component will change this constant with the user tedros root directory path
	 * */
	static final String TEDROS_ROOT = "tedros.root";
	/**
	 * The component will change this constant with the tedros module directory path
	 * */
	static final String TEDROS_MODULE = "tedros.module";
	
	/**
	 * <pre>
	 * {@link org.tedros.fx.control.TFileField} Class
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
	 * {@link org.tedros.fx.control.TFileField} Class
	 * 
	 * Sets the value to the openAction property.
	 * 
	 * Property description:
	 * 
	 * Defines an action to the open button
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TEventHandler> openAction() default TEventHandler.class;
	
	/**
	 * <pre>
	 * {@link org.tedros.fx.control.TFileField} Class
	 * 
	 * Sets the value to the cleanAction property.
	 * 
	 * Property description:
	 * 
	 * Defines an action to the clean button
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TEventHandler> cleanAction() default TEventHandler.class;
	
	/**
	 * <pre>
	 * {@link org.tedros.fx.control.TFileField} Class
	 * 
	 * Sets the value to the selectAction property.
	 * 
	 * Property description:
	 * 
	 * Defines an action to the select button
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TEventHandler> selectAction() default TEventHandler.class;
	
	/**
	 * <pre>
	 * {@link org.tedros.fx.control.TFileField} Class
	 * 
	 * Sets the value to the imageAction property.
	 * 
	 * Property description:
	 * 
	 * Defines an action to the ImageView
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TEventHandler> imageAction() default TEventHandler.class;
	
	
}
