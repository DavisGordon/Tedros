/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.control.TSelectImageField;

import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TSelectImageFieldBuilder 
extends TBuilder
implements ITControlBuilder<TSelectImageField, Observable> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TSelectImageField build(final Annotation annotation, final Observable property) throws Exception {
		com.tedros.fxapi.annotation.control.TSelectImageField ann =  (com.tedros.fxapi.annotation.control.TSelectImageField) annotation;
		TSelectImageField control = property instanceof ObservableList 
				? new TSelectImageField((ObservableList)property, ann.source(), ann.target(), ann.extension(), ann.preLoadFileBytes(), ann.remoteOwner())
						: new  TSelectImageField((SimpleObjectProperty)property, ann.source(), ann.target(), ann.extension(), ann.preLoadFileBytes(), ann.remoteOwner());
		super.callParser(ann, control);
		return control;
	}
	
	
}
