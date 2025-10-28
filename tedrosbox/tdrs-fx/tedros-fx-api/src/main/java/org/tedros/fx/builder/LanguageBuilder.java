package org.tedros.fx.builder;

import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@SuppressWarnings("rawtypes")
public class LanguageBuilder 
extends TBuilder
implements ITGenericBuilder<ObservableList> {
	
	@Override
	public ObservableList build() {
		return FXCollections.observableList(Arrays.asList("EN","PT"));
	}

}
