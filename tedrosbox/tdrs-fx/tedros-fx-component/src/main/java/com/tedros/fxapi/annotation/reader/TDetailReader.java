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
import javafx.scene.Node;
import javafx.scene.shape.Shape;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.entity.TEntity;
import com.tedros.fxapi.annotation.control.TConverter;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.parser.TAnnotationParser;
import com.tedros.fxapi.annotation.parser.TDetailReaderParser;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.shape.TShape;
import com.tedros.fxapi.builder.ITReaderBuilder;
import com.tedros.fxapi.builder.TDetailReaderBuilder;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.form.TDefaultDetailForm;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.presenter.model.TModelView;

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
	 * The {@link com.tedros.fxapi.form.TConverter} which process and return a custom reader node.       
	 * </pre>
	 * */
	public TConverter converter() default @TConverter(type=com.tedros.fxapi.form.TConverter.class, parse = false);
	
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
