/**
 * 
 */
package com.covidsemfome.module.acao.model;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.covidsemfome.model.Acao;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.Voluntario;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.reader.TReader;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class VoluntarioTableView extends TEntityModelView<Voluntario> {

	private SimpleLongProperty id;
	
	private SimpleObjectProperty<Pessoa> pessoa;
	
	@TReader
	@TLabel(text="Nome")
	private SimpleStringProperty nome = new SimpleStringProperty();
	
	/*@TReader
	@TLabel(text="Tipo")
	private SimpleStringProperty tipoVoluntario;
	
	@TReader
	@TLabel(text="Status")
	private SimpleStringProperty statusVoluntario;*/
	
	
	public VoluntarioTableView(Voluntario entity) {
		super(entity);
		nome.setValue(getModel().getPessoa().getNome());
		super.registerProperty("nome", nome);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}
	
	@Override
	public void setId(SimpleLongProperty id) {
		this.id = id;
		
	}

	@Override
	public SimpleLongProperty getId() {
		return id;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return getNome();
	}

	/**
	 * @return the nome
	 */
	public SimpleStringProperty getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(SimpleStringProperty nome) {
		this.nome = nome;
	}

	
	/**
	 * @return the pessoa
	 */
	public SimpleObjectProperty<Pessoa> getPessoa() {
		return pessoa;
	}

	/**
	 * @param pessoa the pessoa to set
	 */
	public void setPessoa(SimpleObjectProperty<Pessoa> pessoa) {
		this.pessoa = pessoa;
	}

}
