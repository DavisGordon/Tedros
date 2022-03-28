/**
 * 
 */
package com.tedros.template.module.produto.model;

import java.util.Arrays;

import com.tedros.fxapi.builder.ITGenericBuilder;
import com.tedros.fxapi.builder.TBuilder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class UnidadeMedidaBuilder extends TBuilder implements ITGenericBuilder<ObservableList> {

	/**
	 * 
	 */
	public UnidadeMedidaBuilder() {
	}

	@Override
	public ObservableList<String> build() {
		return FXCollections.observableList(
				Arrays.asList("KG", "GR","LT", "ML", "UNID", "PCT" )
				);
	}

}
