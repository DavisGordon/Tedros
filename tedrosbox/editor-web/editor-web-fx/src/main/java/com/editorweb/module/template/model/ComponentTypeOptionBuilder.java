/**
 * 
 */
package com.editorweb.module.template.model;

import com.tedros.editorweb.domain.ComponentType;
import com.tedros.fxapi.builder.ITGenericBuilder;
import com.tedros.fxapi.builder.TBuilder;
import com.tedros.fxapi.control.TItem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class ComponentTypeOptionBuilder extends TBuilder implements ITGenericBuilder<ObservableList> {

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.builder.ITGenericBuilder#build()
	 */
	@Override
	public ObservableList build() {
		ObservableList<TItem> lst = FXCollections.observableArrayList();
		for(ComponentType t : ComponentType.values())
			lst.add(new TItem(t.name(), t));
		return lst;
	}

}
