package org.somos.module.pessoa.table;

import org.somos.model.DetalheAjuda;
import org.somos.model.FormaAjuda;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class DetalheAjudaCellCallBack implements Callback <CellDataFeatures<AjudaCampanhaTableView, ObservableValue<FormaAjuda>>, ObservableValue<String>> {
	@Override
	public ObservableValue<String> call(CellDataFeatures<AjudaCampanhaTableView, ObservableValue<FormaAjuda>> param) {
		DetalheAjuda da = param.getValue().getDetalheAjuda().getValue();
		String s ="";
		if(da!=null) {
			s = da.getDesc();
		}
		return  new SimpleStringProperty(s);
	}
}
