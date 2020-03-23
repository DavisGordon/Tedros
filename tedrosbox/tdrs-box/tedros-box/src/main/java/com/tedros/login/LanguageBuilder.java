package com.tedros.login;

import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.tedros.fxapi.builder.ITGenericBuilder;
import com.tedros.fxapi.builder.TBuilder;

@SuppressWarnings("rawtypes")
public class LanguageBuilder 
extends TBuilder
implements ITGenericBuilder<ObservableList> {
	
	@Override
	public ObservableList build() {
		return FXCollections.observableList(Arrays.asList("EN","PT"));
	}

}
