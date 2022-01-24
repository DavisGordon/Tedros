/**
 * 
 */
package org.somos.module.report.model;

import java.util.Date;

import org.somos.report.model.PessoaModel;

import com.tedros.fxapi.presenter.model.TModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class PessoaModelTableView extends TModelView<PessoaModel> {
	
	private SimpleLongProperty id;
	
	private SimpleStringProperty nome;
	
	private SimpleStringProperty tipo;
	
	private SimpleObjectProperty<Date> dataCadastro;
	
	private SimpleStringProperty email;

	private SimpleStringProperty contatos;

	public PessoaModelTableView(PessoaModel model) {
		super(model);
	}
	
	@Override
	public SimpleStringProperty getDisplayProperty() {
		// TODO Auto-generated method stub
		return null;
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
	 * @return the tipo
	 */
	public SimpleStringProperty getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(SimpleStringProperty tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the email
	 */
	public SimpleStringProperty getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(SimpleStringProperty email) {
		this.email = email;
	}

	/**
	 * @return the contatos
	 */
	public SimpleStringProperty getContatos() {
		return contatos;
	}

	/**
	 * @param contatos the contatos to set
	 */
	public void setContatos(SimpleStringProperty contatos) {
		this.contatos = contatos;
	}

	/**
	 * @return the dataCadastro
	 */
	public SimpleObjectProperty<Date> getDataCadastro() {
		return dataCadastro;
	}

	/**
	 * @param dataCadastro the dataCadastro to set
	 */
	public void setDataCadastro(SimpleObjectProperty<Date> dataCadastro) {
		this.dataCadastro = dataCadastro;
	}


}
