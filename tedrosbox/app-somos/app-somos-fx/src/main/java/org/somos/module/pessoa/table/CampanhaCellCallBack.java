package org.somos.module.pessoa.table;

import org.somos.model.Campanha;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class CampanhaCellCallBack implements Callback <CellDataFeatures<AjudaCampanhaTableView, ObservableValue<Campanha>>, ObservableValue<String>> {
	@Override
	public ObservableValue<String> call(CellDataFeatures<AjudaCampanhaTableView, ObservableValue<Campanha>> param) {
		return  new SimpleStringProperty( param.getValue().getCampanha().getValue().getTitulo());
	}
	
	
	
}
