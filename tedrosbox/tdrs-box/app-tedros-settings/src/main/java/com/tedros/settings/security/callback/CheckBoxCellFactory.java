package com.tedros.settings.security.callback;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.util.Callback;

public class CheckBoxCellFactory<S, T> implements Callback<CellDataFeatures<S, T>, TableCell<S, T>> {
	
	@Override 
	public TableCell<S, T> call(CellDataFeatures<S, T> p) {
		return new CheckBoxTableCell<>();
	}
	
}
