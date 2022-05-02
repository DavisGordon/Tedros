/**
 * 
 */
package org.somos.module.pessoa.table;

import java.util.Date;

import org.somos.model.AjudaCampanha;
import org.somos.model.Campanha;
import org.somos.model.FormaAjuda;

import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class AjudaCampanhaTableView extends TEntityModelView<AjudaCampanha> {

	private SimpleLongProperty id;
	
	private SimpleObjectProperty<Campanha> campanha;
	
	private SimpleStringProperty valor;

	private SimpleStringProperty status;

	private SimpleStringProperty periodo;
	
	private SimpleObjectProperty<FormaAjuda> formaAjuda;
	
	private SimpleObjectProperty<Date> dataProcessado;

	private SimpleObjectProperty<Date> dataProximo;
	
	public AjudaCampanhaTableView(AjudaCampanha entity) {
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
	 * @return the campanha
	 */
	public SimpleObjectProperty<Campanha> getCampanha() {
		return campanha;
	}

	/**
	 * @param campanha the campanha to set
	 */
	public void setCampanha(SimpleObjectProperty<Campanha> campanha) {
		this.campanha = campanha;
	}

	/**
	 * @return the valor
	 */
	public SimpleStringProperty getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(SimpleStringProperty valor) {
		this.valor = valor;
	}

	/**
	 * @return the status
	 */
	public SimpleStringProperty getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(SimpleStringProperty status) {
		this.status = status;
	}

	/**
	 * @return the periodo
	 */
	public SimpleStringProperty getPeriodo() {
		return periodo;
	}

	/**
	 * @param periodo the periodo to set
	 */
	public void setPeriodo(SimpleStringProperty periodo) {
		this.periodo = periodo;
	}

	/**
	 * @return the formaAjuda
	 */
	public SimpleObjectProperty<FormaAjuda> getFormaAjuda() {
		return formaAjuda;
	}

	/**
	 * @param formaAjuda the formaAjuda to set
	 */
	public void setFormaAjuda(SimpleObjectProperty<FormaAjuda> formaAjuda) {
		this.formaAjuda = formaAjuda;
	}

	/**
	 * @return the dataProcessado
	 */
	public SimpleObjectProperty<Date> getDataProcessado() {
		return dataProcessado;
	}

	/**
	 * @param dataProcessado the dataProcessado to set
	 */
	public void setDataProcessado(SimpleObjectProperty<Date> dataProcessado) {
		this.dataProcessado = dataProcessado;
	}

	/**
	 * @return the dataProximo
	 */
	public SimpleObjectProperty<Date> getDataProximo() {
		return dataProximo;
	}

	/**
	 * @param dataProximo the dataProximo to set
	 */
	public void setDataProximo(SimpleObjectProperty<Date> dataProximo) {
		this.dataProximo = dataProximo;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		// TODO Auto-generated method stub
		return null;
	}

}
