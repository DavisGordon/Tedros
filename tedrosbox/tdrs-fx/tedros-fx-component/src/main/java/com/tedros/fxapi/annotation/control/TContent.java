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

import javafx.beans.property.Property;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.fxapi.annotation.form.TDetailForm;
import com.tedros.fxapi.annotation.form.TDetailView;
import com.tedros.fxapi.presenter.model.TEntityModelView;

/**
 * A content describe how a detail data must be built, in a detail view or inside a form, choose one.
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TContent  {
	
	/**
	 * Build a detail view
	 * */
	public TDetailView detailView() default @TDetailView(	entityClass = ITEntity.class, 
															entityModelViewClass = TEntityModelView.class, 
															formTitle = "", 
															listTitle = "", 
															propertyType = Property.class);
	/**
	 * Build a form
	 * */
	public TDetailForm detailForm() default @TDetailForm(fields = {""});
}
