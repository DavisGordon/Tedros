/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.annotation.control.TBarcodeGenerator;
import org.tedros.fx.control.TBarcode;

import javafx.beans.property.SimpleStringProperty;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TBarcodeGeneratorBuilder 
extends TBuilder
implements ITControlBuilder<TBarcode, SimpleStringProperty> {

	public TBarcode build(final Annotation annotation, final SimpleStringProperty property) throws Exception {
		TBarcodeGenerator tAnnotation = (TBarcodeGenerator) annotation;
		final TBarcode control = new TBarcode(property);
		callParser(tAnnotation, control);
		return control;
	}
		
}
