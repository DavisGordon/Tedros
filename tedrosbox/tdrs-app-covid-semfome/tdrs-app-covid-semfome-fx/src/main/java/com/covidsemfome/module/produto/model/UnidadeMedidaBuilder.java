/**
 * 
 */
package com.covidsemfome.module.produto.model;

import java.util.Arrays;

import com.tedros.fxapi.builder.ITGenericBuilder;
import com.tedros.fxapi.builder.TBuilder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Davis Gordon
 *
 */
public class UnidadeMedidaBuilder extends TBuilder implements ITGenericBuilder<ObservableList> {

	/**
	 * 
	 */
	public UnidadeMedidaBuilder() {
	}

	@Override
	public ObservableList<String> build() {
		return FXCollections.observableList(
				Arrays.asList("Kg", "gr","Lt", "ml", "UNID", "PCT" )
				);
	}

}
