/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 08/11/2013
 */
package com.tedros.fxapi.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.ejb.base.model.ITModel;
import com.tedros.fxapi.annotation.TAnnotationDefaultValue;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TVBoxMargin;
import com.tedros.fxapi.annotation.layout.TVGrow;
import com.tedros.fxapi.annotation.parser.ITAnnotationParser;
import com.tedros.fxapi.annotation.parser.TRequiredModalParser;
import com.tedros.fxapi.annotation.parser.TVBoxParser;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.layout.TRegion;
import com.tedros.fxapi.builder.ITFieldBuilder;
import com.tedros.fxapi.builder.TEditEntityModalBuilder;
import com.tedros.fxapi.control.TRequiredTextField;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * <pre>
 * Build a {@link com.tedros.fxapi.control.TEditEntitynModal} component.
 * 
 * This component opens a modal that allows a user to edit items. 
 * 
 *  Must be used with ITObservableList 
 * </pre>
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TEditEntityModal  {
	
	/**
	 *<pre>
	 * The builder of type {@link ITFieldBuilder} for this component.
	 * 
	 *  Default value: {@link TEditEntityModalBuilder}
	 *</pre> 
	 * */
	public Class<? extends ITFieldBuilder> builder() default TEditEntityModalBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TVBoxParser.class, TRequiredModalParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TVBoxParser.class, TRequiredModalParser.class};
	 
	
	/**
	 * The model view class with the fields to search. 
	 * This class  must be annotated with @TSelectionModalPresenter
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TModelView> modelViewClass();
	
	/**
	 * The model or entity type
	 * */
	public Class<? extends ITModel> modelClass();
	
	/**
	 * The list view width 
	 * @default 220
	 * */
	public double width() default 220;
	/**
	 * The list view height 
	 * @default 120
	 * */
	public double height() default 120;
	
	/**
	 * The modal width 
	 * @default 950
	 * */
	public double modalWidth() default 950;
	/**
	 * The modal height 
	 * @default 600
	 * */
	public double modalHeight() default 600;
	
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
	 * The {@link Pane} settings.
	 * </pre>
	 * */
	public TPane pane() default @TPane();

	/**
	* <pre>
	* {@link VBox} Class
	* 
	*  Sets the vertical grow priority for the child when contained by an vbox. If set, the vbox will use the priority to allocate additional space if the vbox is resized larger than it's preferred height. If multiple vbox children have the same vertical grow priority, then the extra space will be split evenly between them. If no vertical grow priority is set on a child, the vbox will never allocate it additional vertical space if available. Setting the value to null will remove the constraint. Parameters: child - the child of a vbox value - the horizontal grow priority for the child
	* </pre>
	**/
	public TVGrow vgrow() default @TVGrow;

	/**
	* <pre>
	* {@link VBox} Class
	* 
	*  Sets the margin for the child when contained by a vbox. If set, the vbox will layout the child so that it has the margin space around it. Setting the value to null will remove the constraint. Parameters: child - the child mode of a vbox value - the margin of space around the child
	* </pre>
	**/
	public TVBoxMargin margin() default @TVBoxMargin;

	/**
	* <pre>
	* {@link VBox} Class
	* 
	*  Sets the value of the property spacing. Property description: The amount of vertical space between each child in the vbox.
	* </pre>
	**/
	public double spacing() default TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link VBox} Class
	* 
	*  Sets the value of the property alignment. Property description: The overall alignment of children within the vbox's width and height.
	* </pre>
	**/
	public Pos alignment() default Pos.CENTER;

	/**
	* <pre>
	* {@link VBox} Class
	* 
	*  Sets the value of the property fillWidth. Property description: Whether or not resizable children will be resized to fill the full width of the vbox or be kept to their preferred width and aligned according to the alignment hpos value.
	* </pre>
	**/
	public boolean fillWidth() default false;
	
	/**
	* <pre>
	* Specifies the view mode to use this compent.
	* 
	* Set to TMode.READER will build this component only when the user set the view to Reader mode.
	* 
	* Default value:
	* {TMode.EDIT, TMode.READER}
	* </pre>
	**/
	public TViewMode[] mode() default {TViewMode.EDIT, TViewMode.READER};
	
	/**
	 * <pre>
	 * {@link TRequiredTextField} Class
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
	
	
}
