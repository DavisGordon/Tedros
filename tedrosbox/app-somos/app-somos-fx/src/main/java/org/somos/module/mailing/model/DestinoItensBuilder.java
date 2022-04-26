/**
 * 
 */
package org.somos.module.mailing.model;

import java.util.Arrays;

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
		return FXCollections.observableList(
				Arrays.asList(new TItemModel<String>("1","Todos"),
						new TItemModel<String>("2","Não inscritos"),
						new TItemModel<String>("3","Aos Inscritos"),
						new TItemModel<String>("4","Aos Operacionais"),
						new TItemModel<String>("5","Aos estratégicos"),
						new TItemModel<String>("6","Aos doadores"),
						new TItemModel<String>("7","Aos Cadastrados no site e outros")
						)
				);
	}

}
