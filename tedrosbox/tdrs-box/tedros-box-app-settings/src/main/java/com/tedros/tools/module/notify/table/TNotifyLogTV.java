/**
 * 
 */
package com.tedros.tools.module.notify.table;

import java.util.Date;

import com.tedros.core.TLanguage;
import com.tedros.core.notify.model.TNotifyLog;
import com.tedros.core.notify.model.TState;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.tools.start.TConstant;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class TNotifyLogTV extends TEntityModelView<TNotifyLog> {

	private SimpleLongProperty id;

	private SimpleObjectProperty<TState> state;

	private SimpleStringProperty description;

	private SimpleObjectProperty<Date> insertDate;
	
	public TNotifyLogTV(TNotifyLog entity) {
		super(entity);
	}

	/**
	 * @return the id
	 */
	public SimpleLongProperty getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	/**
	 * @return the state
	 */
	public SimpleObjectProperty<TState> getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(SimpleObjectProperty<TState> state) {
		this.state = state;
	}

	/**
	 * @return the description
	 */
	public SimpleStringProperty getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(SimpleStringProperty description) {
		this.description = description;
	}

	/**
	 * @return the insertDate
	 */
	public SimpleObjectProperty<Date> getInsertDate() {
		return insertDate;
	}

	/**
	 * @param insertDate the insertDate to set
	 */
	public void setInsertDate(SimpleObjectProperty<Date> insertDate) {
		this.insertDate = insertDate;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return new SimpleStringProperty(TLanguage.getInstance(TConstant.UUI)
				.getString(state.getValue().getValue()));
	}
}
