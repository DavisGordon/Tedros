/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 08/11/2013
 */
package org.tedros.fx.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.fx.annotation.parser.TRequiredDetailParser;
import org.tedros.fx.annotation.parser.TStackPaneParser;
import org.tedros.fx.annotation.presenter.TDetailListViewPresenter;
import org.tedros.fx.annotation.presenter.TDetailTableViewPresenter;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TInsets;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.ITFieldBuilder;
import org.tedros.fx.builder.TDetailListFieldlBuilder;
import org.tedros.fx.control.TRequiredDetailField;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.server.entity.ITEntity;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * <pre>
 * Build a {@link org.tedros.fx.control.TDetailListField} component.
 * 
 * This component build a view to add and remove detail items. 
 * 
 *  Must be used with ITObservableList 
 * </pre>
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TDetailListField  {
	
	/**
	 *<pre>
	 * The builder of type {@link ITFieldBuilder} for this component.
	 * 
	 *  Default value: {@link TDetailListFieldlBuilder}
	 *</pre> 
	 * */
	public Class<? extends ITFieldBuilder> builder() default TDetailListFieldlBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TStackPaneParser.class, TRequiredDetailParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TStackPaneParser.class, TRequiredDetailParser.class};
	 
	
	/**
	 * The detail model view class . 
	 * The TEntityModelView class must have a {@link TDetailTableViewPresenter} 
	 * or  {@link TDetailListViewPresenter} annotation.
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TEntityModelView> modelView();
	
	/**
	 * The entity type of the TEntityModelView
	 * */
	public Class<? extends ITEntity> entity();
	
	/**
	 * <pre>
	 * The {@link Node} properties.
	 * </pre>
	 * */
	public TNode node() default @TNode(parse = false);
	
	/**
	 * <pre>
	 * The {@link Region} properties.
	 * </pre>
	 * */
	public TRegion region() default @TRegion(parse = false);
	
	/**
	 * <pre>
	 * {@link TRequiredDetailField} Class
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
	
	
}
