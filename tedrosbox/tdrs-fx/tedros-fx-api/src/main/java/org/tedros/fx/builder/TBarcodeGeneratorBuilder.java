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
import org.tedros.server.model.ITBarcode;

import javafx.beans.property.SimpleObjectProperty;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TBarcodeGeneratorBuilder 
extends TBuilder
implements ITControlBuilder<TBarcode, SimpleObjectProperty<ITBarcode>> {

	public TBarcode build(final Annotation annotation, final SimpleObjectProperty<ITBarcode> property) throws Exception {
		TBarcodeGenerator tAnn = (TBarcodeGenerator) annotation;
		final TBarcode control = new TBarcode(tAnn.modelType());
		control.tValueProperty().bindBidirectional(property);
		callParser(tAnn, control);
		return control;
	}
		
}
