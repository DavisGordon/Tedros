package com.tedros.settings.security.callback;

import com.tedros.settings.security.model.TAuthorizationTableView;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class CheckBoxEnableCallBack implements Callback <CellDataFeatures<TAuthorizationTableView, ObservableValue<Boolean>>, ObservableValue<Boolean>> {

	@Override
	public ObservableValue<Boolean> call(CellDataFeatures<TAuthorizationTableView, ObservableValue<Boolean>> param) {
		return param.getValue().getEnabled();
	}
	
	
	
}
