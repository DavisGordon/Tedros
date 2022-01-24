package org.somos.module.empresaParceira.model;

import java.util.Arrays;

import com.tedros.fxapi.builder.ITGenericBuilder;
import com.tedros.fxapi.builder.TBuilder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@SuppressWarnings("rawtypes")
public class EstiloOptionBuilder 
extends TBuilder
implements ITGenericBuilder<ObservableList> {
	
	@Override
	public ObservableList build() {
		return FXCollections.observableList(Arrays.asList("style1","style2","style3","style4","style5"));
	}

}
