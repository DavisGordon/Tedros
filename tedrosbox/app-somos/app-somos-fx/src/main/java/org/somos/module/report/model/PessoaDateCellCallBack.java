package org.somos.module.report.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class PessoaDateCellCallBack implements Callback <CellDataFeatures<PessoaModelTableView, ObservableValue<Date>>, ObservableValue<String>> {
	private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	@Override
	public ObservableValue<String> call(CellDataFeatures<PessoaModelTableView, ObservableValue<Date>> param) {
		Date d = param.getValue().getDataCadastro().getValue();
		return (d!=null) 
				? new SimpleStringProperty(format.format(d))
						: new SimpleStringProperty();
	}
	
	
	
}
