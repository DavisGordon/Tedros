package com.solidarity.module.acao.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.tedros.core.TInternationalizationEngine;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class AcaoDateCellCallBack implements Callback <CellDataFeatures<AcaoItemTableView, ObservableValue<Date>>, ObservableValue<String>> {

	@Override
	public ObservableValue<String> call(CellDataFeatures<AcaoItemTableView, ObservableValue<Date>> param) {
		String as =  TInternationalizationEngine.getInstance(null).getString("#{label.as}");
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy '"+as+"' HH:mm");
		Date d = param.getValue().getData().getValue();
		return (d!=null) 
				? new SimpleStringProperty(format.format(d))
						: new SimpleStringProperty();
	}
	
	
	
}
