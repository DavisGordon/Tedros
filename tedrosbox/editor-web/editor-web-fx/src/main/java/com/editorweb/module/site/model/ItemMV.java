/**
 * 
 */
package com.editorweb.module.site.model;

import com.tedros.editorweb.model.Item;
import com.tedros.fxapi.control.TItem;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class ItemMV extends TEntityModelView<Item> {

	

	public ItemMV(Item entity) {
		super(entity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setId(SimpleLongProperty id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SimpleLongProperty getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		// TODO Auto-generated method stub
		return null;
	}

}
