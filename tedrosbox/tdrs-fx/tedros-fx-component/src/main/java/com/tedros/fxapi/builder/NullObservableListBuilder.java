package com.tedros.fxapi.builder;

import javafx.collections.ObservableList;

@SuppressWarnings("rawtypes")
public abstract class NullObservableListBuilder implements ITGenericBuilder<ObservableList> {

	@Override
	public ObservableList build() {
		return null;
	}

}
