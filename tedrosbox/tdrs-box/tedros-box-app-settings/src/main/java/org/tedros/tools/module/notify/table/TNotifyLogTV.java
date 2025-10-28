/**
 * 
 */
package org.tedros.tools.module.notify.table;

import java.util.Date;

import org.tedros.core.TLanguage;
import org.tedros.core.notify.model.TNotifyLog;
import org.tedros.core.notify.model.TState;
import org.tedros.fx.model.TEntityModelView;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class TNotifyLogTV extends TEntityModelView<TNotifyLog> {

	private SimpleObjectProperty<TState> state;

	private SimpleStringProperty description;

	private SimpleObjectProperty<Date> insertDate;
	
	public TNotifyLogTV(TNotifyLog entity) {
		super(entity);
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
	public SimpleStringProperty toStringProperty() {
		return new SimpleStringProperty(TLanguage.getInstance()
				.getString(state.getValue().getValue()));
	}
}
