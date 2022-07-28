package com.tedros.tools.module.notify.table;

import com.tedros.core.TLanguage;
import com.tedros.core.notify.model.TState;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class TNotifyLogStateCallBack implements Callback <CellDataFeatures<TNotifyLogTV, ObservableValue<TState>>, ObservableValue<String>> {
	
	@Override
	public ObservableValue<String> call(CellDataFeatures<TNotifyLogTV, ObservableValue<TState>> param) {
		TState d = param.getValue().getState().getValue();
		return (d!=null) 
				? new SimpleStringProperty(TLanguage.getInstance().getString(d.getValue()))
						: new SimpleStringProperty();
	}
	
	
	
}
