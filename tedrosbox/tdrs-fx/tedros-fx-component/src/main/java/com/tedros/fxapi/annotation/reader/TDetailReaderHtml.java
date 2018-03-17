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
import com.tedros.fxapi.annotation.control.TConverter;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.builder.ITReaderHtmlBuilder;
import com.tedros.fxapi.builder.TDetailReaderHtmlBuilder;
import com.tedros.fxapi.domain.TLayoutType;
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
public @interface TDetailReaderHtml {

	
	/**
	 *<pre>
	 * The builder of type {@link ITReaderHtmlBuilder} for this component.
	 * 
	 *  Default value: {TDetailReaderHtmlBuilder.class}
	 *</pre> 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITReaderHtmlBuilder> builder() default TDetailReaderHtmlBuilder.class;
	
	
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
	
	/**
	 * <pre>
	 * Define a layout to organize the model fields;
	 * 
	 * Default value: TLayoutType.FLOWPANE
	 * </pre>
	 * */
	public TLayoutType fieldsLayout() default TLayoutType.FLOWPANE;
	
	/**
	 * <pre>
	 * Define a layout to organize the model collection;
	 * 
	 * Default value: TLayoutType.VBOX
	 * </pre>
	 * */
	public TLayoutType modelLayout() default TLayoutType.VBOX;
	
	
}
