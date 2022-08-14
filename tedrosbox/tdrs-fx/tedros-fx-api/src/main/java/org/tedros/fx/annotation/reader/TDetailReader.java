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

import org.tedros.fx.annotation.control.TConverter;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.parser.TAnnotationParser;
import org.tedros.fx.annotation.parser.TDetailReaderParser;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.shape.TShape;
import org.tedros.fx.builder.ITReaderBuilder;
import org.tedros.fx.builder.TDetailReaderBuilder;
import org.tedros.fx.form.ITModelForm;
import org.tedros.fx.form.TDefaultDetailForm;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.entity.TEntity;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.shape.Shape;

/**
 * <pre>
 * Mark a field of {@link TModelView}, {@link TEntityModelView} 
 * or {@link ObservableList} of both types to be read.
 * </pre>
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TDetailReader {

	
	/**
	 *<pre>
	 * The builder of type {@link ITReaderBuilder} for this component.
	 * 
	 *  Default value: {TDetailReaderBuilder.class}
	 *</pre> 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITReaderBuilder> builder() default TDetailReaderBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TDetailReaderParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TAnnotationParser>[] parser() default {TDetailReaderParser.class};
	
	/**
	 * <pre>
	 * The {@link Node} settings for this component.
	 * </pre>
	 * */
	public TNode node() default @TNode(parse = false);
	
	/**
	 * <pre>
	 * The {@link Shape} settings for this node.
	 * </pre>
	 * */
	public TShape shape() default @TShape(parse = false);
	
	/**
	 * <pre>
	 * Sets the formClass property;
	 * 
	 * Property description:
	 * 
	 * The form class for this detail entity.    
	 * 
	 * Default value: TDefaultDetailForm.class
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITModelForm> formClass() default TDefaultDetailForm.class;
	
	/**
	 * <pre>
	 * Sets the entityClass property;
	 * 
	 * Property description:
	 * 
	 * The entity class for this detail.    
	 * </pre>
	 * */
	public Class<? extends ITEntity> entityClass() default TEntity.class;
	
	/**
	 * <pre>
	 * Sets the modelViewClass property;
	 * 
	 * Property description:
	 * 
	 * The modelView class for this detail.    
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TModelView> modelViewClass() default TModelView.class;
	
	/**
	 * <pre>
	 * Sets the label to the detail form;
	 * 
	 * Property description:
	 * 
	 * Build a label at top of the form    
	 * </pre>
	 * */
	public TLabel label() default @TLabel(text="");
	
	/**
	 * <pre>
	 * Sets the converter;
	 * 
	 * Property description:
	 * 
	 * The {@link org.tedros.fx.form.TConverter} which process and return a custom reader node.       
	 * </pre>
	 * */
	public TConverter converter() default @TConverter(type=org.tedros.fx.form.TConverter.class, parse = false);
	
	/**
	 * <pre>
	 * Sets the suppressLabels property;
	 * 
	 * Property description:
	 * 
	 * Hide all labels inside the detail form.    
	 * </pre>
	 * */
	public boolean suppressLabels() default false;
	
	
}
