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

import com.tedros.fxapi.annotation.parser.ITAnnotationParser;
import com.tedros.fxapi.annotation.parser.TRequiredSelectImageParser;
import com.tedros.fxapi.annotation.parser.TSelectImageParser;
import com.tedros.fxapi.annotation.parser.TStackPaneParser;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.scene.control.TInsets;
import com.tedros.fxapi.annotation.scene.layout.TRegion;
import com.tedros.fxapi.builder.ITFieldBuilder;
import com.tedros.fxapi.builder.TSelectImageFieldBuilder;
import com.tedros.fxapi.control.action.TEventHandler;
import com.tedros.fxapi.domain.TEnvironment;
import com.tedros.fxapi.domain.TFileModelType;
import com.tedros.fxapi.domain.TImageExtension;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * <pre>
 * Build a {@link com.tedros.fxapi.control.TSelectImageField} component.
 * 
 * Example:
 * 
 * Select a list of local images to be saved on remote repository. 
 * The file repository is shared with all apps and which file is identified by the remote owner name.
 * 
 * <i>@</i>TSelectImageField(source=TEnvironment.LOCAL, target=TEnvironment.REMOTE, remoteOwner= "myApp")
 * <i>@</i>TModelViewCollectionType(modelClass = TFileEntity.class)
 * private ITObservableList&ltITFileBaseModel&gt files;
 * 
 * List the images saved on the remote repository to be selected and aggregate to an entity.
 * 
 * <i>@</i>TSelectImageField(source=TEnvironment.REMOTE, target=TEnvironment.REMOTE, remoteOwner="myApp")
 * private SimpleObjectProperty&ltITFileBaseModel&gt image;
 * </pre>
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TSelectImageField {

	/**
	 *<pre>
	 * The builder of type {@link ITFieldBuilder} for this component.
	 * 
	 *  Default value: {@link TSelectImageFieldBuilder}
	 *</pre> 
	 * */
	public Class<? extends ITFieldBuilder> builder() default TSelectImageFieldBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TSelectImageParser.class, TStackPaneParser.class, TRequiredSelectImageParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TSelectImageParser.class, TStackPaneParser.class, TRequiredSelectImageParser.class};
	
	/**
	 * <pre>
	 * The {@link Node} settings.
	 * </pre>
	 * */
	public TNode node() default @TNode(parse = false);
	
	/**
	 * <pre>
	 * The {@link Control} settings.
	 * </pre>
	 * */
	public TControl control() default @TControl(parse = false);
	
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
	public TFileModelType propertyValueType() default TFileModelType.ENTITY;
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TSelectImageField} Class
	 * 
	 * Set the component height inside the form. 
	 * 
	 * Default value: 450
	 * </pre>
	 * */
	public double height() default 450;
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TSelectImageField} Class
	 * 
	 * Specify the  owner name of the remote files to be loaded 
	 * when the source property is TEnvironment.REMOTE
	 * 
	 * Default value: Empty String
	 * </pre>
	 * */
	public String[] remoteOwner() default "";
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TSelectImageField} Class
	 * 
	 * Specify the file source location.
	 * 
	 * Default value: TEnvironment.LOCAL
	 * </pre>
	 * */
	public TEnvironment source() default TEnvironment.LOCAL;
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TSelectImageField} Class
	 * 
	 * Specify the file target location.
	 * 
	 * Default value: TEnvironment.LOCAL
	 * </pre>
	 * */
	public TEnvironment target() default TEnvironment.LOCAL;
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TSelectImageField} Class
	 * 
	 * Specify the image file type.
	 * 
	 * Default value: TImageExtension.ALL_IMAGES
	 * </pre>
	 * */
	public TImageExtension extension() default TImageExtension.ALL_IMAGES;
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TSelectImageField} Class
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
	 * {@link com.tedros.fxapi.control.TSelectImageField} Class
	 * 
	 * Sets the value for the fitWidth property.
	 * 
	 * Property description:
	 * 
	 * If the file is a valid image, defines the minimum width.
	 * </pre>
	 * */
	public double fitWidth() default -1;
	
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TSelectImageField} Class
	 * 
	 * Sets the value for the fitHeight property.
	 * 
	 * Property description:
	 * 
	 * If the file is a valid image, defines the maximum height.
	 * </pre>
	 * */
	public double fitHeight() default 250;
	
	/**
	 * <pre>
	 * {@link com.tedros.fxapi.control.TSelectImageField} Class
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
	 * {@link com.tedros.fxapi.control.TSelectImageField} Class
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
	 * Sets the value to the imageClickAction property.
	 * 
	 * Property description:
	 * 
	 * Defines the action to the image click event
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TEventHandler>[] imageViewEvents() default TEventHandler.class;
	
	
}
