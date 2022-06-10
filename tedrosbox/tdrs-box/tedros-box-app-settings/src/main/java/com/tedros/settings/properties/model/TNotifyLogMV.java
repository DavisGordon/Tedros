/**
 * 
 */
package com.tedros.settings.properties.model;

import java.util.Date;

import com.tedros.core.TLanguage;
import com.tedros.core.notify.model.TNotifyLog;
import com.tedros.core.notify.model.TState;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TShowField;
import com.tedros.fxapi.annotation.control.TShowField.TField;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TDetailListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TDetailCrudViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TDetailCrudViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.settings.start.TConstant;
import com.tedros.util.TDateUtil;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
@TDetailListViewPresenter(presenter=@TPresenter(
behavior = @TBehavior(type = TDetailCrudViewBehavior.class), 
decorator = @TDecorator(type = TDetailCrudViewDecorator.class, 
buildModesRadioButton=false, buildDeleteButton=false, buildNewButton=false, buildSaveButton=false, 
viewTitle="#{label.event.log}")))

public class TNotifyLogMV extends TEntityModelView<TNotifyLog> {

	
	private SimpleLongProperty id;

	@TLabel(text="#{label.state}")
	@TShowField()
	private SimpleObjectProperty<TState> state;
	

	@TLabel(text="#{label.description}")
	@TShowField()
	private SimpleStringProperty description;
	
	@TLabel(text="#{label.date.insert}")
	@TShowField(fields= {@TField(pattern=TDateUtil.DDMMYYYY_HHMM)})
	private SimpleObjectProperty<Date> insertDate;
	
	public TNotifyLogMV(TNotifyLog entity) {
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
