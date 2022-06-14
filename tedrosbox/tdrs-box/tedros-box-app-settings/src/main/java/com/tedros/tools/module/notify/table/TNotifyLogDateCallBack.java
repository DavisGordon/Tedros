package com.tedros.tools.module.notify.table;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class TNotifyLogDateCallBack implements Callback <CellDataFeatures<TNotifyLogTV, ObservableValue<Date>>, ObservableValue<String>> {
	private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	@Override
	public ObservableValue<String> call(CellDataFeatures<TNotifyLogTV, ObservableValue<Date>> param) {
		Date d = param.getValue().getInsertDate().getValue();
		return (d!=null) 
				? new SimpleStringProperty(format.format(d))
						: new SimpleStringProperty();
	}
	
	
	
}
