package org.tedros.fx.builder;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

@SuppressWarnings("rawtypes")
public abstract class NullCallbackListViewListCellBuilder implements ITGenericBuilder<Callback<ListView, ListCell>> {

	@Override
	public Callback<ListView, ListCell> build() {
		return null;
	}

}
