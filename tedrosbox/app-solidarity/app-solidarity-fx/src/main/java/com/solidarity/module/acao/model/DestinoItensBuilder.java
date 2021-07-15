/**
 * 
 */
package com.solidarity.module.acao.model;

import java.util.Arrays;

import com.tedros.core.TInternationalizationEngine;
import com.tedros.ejb.base.model.TItemModel;
import com.tedros.fxapi.builder.ITGenericBuilder;
import com.tedros.fxapi.builder.TBuilder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Davis Gordon
 *
 */
public class DestinoItensBuilder extends TBuilder implements ITGenericBuilder<ObservableList> {

	/**
	 * 
	 */
	public DestinoItensBuilder() {
	}

	@Override
	public ObservableList<TItemModel<String>> build() {
		TInternationalizationEngine iE =TInternationalizationEngine.getInstance(null);
		return FXCollections.observableList(
				Arrays.asList(new TItemModel<String>("1",iE.getString("#{label.todos}")),
						new TItemModel<String>("2",iE.getString("#{label.nao.inscritos}")),
						new TItemModel<String>("3",iE.getString("#{label.aos.inscritos}")),
						new TItemModel<String>("4",iE.getString("#{label.aos.operacionais}")),
						new TItemModel<String>("5",iE.getString("#{label.aos.estrategicos}")),
						new TItemModel<String>("6",iE.getString("#{label.aos.doadores}")),
						new TItemModel<String>("7",iE.getString("#{label.cad.site.outros}"))
						)
				);
	}

}
