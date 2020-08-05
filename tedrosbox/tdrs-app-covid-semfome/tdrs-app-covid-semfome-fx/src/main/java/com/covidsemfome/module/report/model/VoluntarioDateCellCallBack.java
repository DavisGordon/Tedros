package com.covidsemfome.module.report.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class VoluntarioDateCellCallBack implements Callback <CellDataFeatures<VoluntarioItemTableView, ObservableValue<Date>>, ObservableValue<String>> {
	private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy à's' HH:mm");
	@Override
	public ObservableValue<String> call(CellDataFeatures<VoluntarioItemTableView, ObservableValue<Date>> param) {
		Date d = param.getValue().getData().getValue();
		return (d!=null) 
				? new SimpleStringProperty(format.format(d))
						: new SimpleStringProperty();
	}
	
	
	
}
