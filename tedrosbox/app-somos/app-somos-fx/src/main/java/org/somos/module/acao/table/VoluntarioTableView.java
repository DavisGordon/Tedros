/**
 * 
 */
package org.somos.module.acao.table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.somos.model.Pessoa;
import org.somos.model.Voluntario;
import org.somos.module.pessoa.model.PessoaFieldValueUtil;

import com.tedros.fxapi.annotation.control.TLabel;
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
	
	@TLabel(text="Nome")
	private SimpleStringProperty nome = new SimpleStringProperty();
	
	@TLabel(text="Email")
	private SimpleStringProperty email = new SimpleStringProperty();
	
	
	@TLabel(text="Tipo")
	private SimpleStringProperty tipoVoluntario = new SimpleStringProperty();;
	
	@TLabel(text="Status")
	private SimpleStringProperty statusVoluntario = new SimpleStringProperty();;
	
	
	public VoluntarioTableView(Voluntario entity) {
		super(entity);
		nome.setValue(getModel().getPessoa().getNome());
		email.setValue(PessoaFieldValueUtil.getEmails(getModel().getPessoa()));
		tipoVoluntario.setValue(PessoaFieldValueUtil.getDescricaoTipo(getModel().getPessoa().getTipoVoluntario()));
		statusVoluntario.setValue(PessoaFieldValueUtil.getDescricaoStatus(getModel().getPessoa().getStatusVoluntario()));
		super.registerProperty("nome", nome);
		super.registerProperty("email", email);
		super.registerProperty("tipoVoluntario", tipoVoluntario);
		super.registerProperty("statusVoluntario", statusVoluntario);
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

	/**
	 * @return the tipoVoluntario
	 */
	public SimpleStringProperty getTipoVoluntario() {
		return tipoVoluntario;
	}

	/**
	 * @param tipoVoluntario the tipoVoluntario to set
	 */
	public void setTipoVoluntario(SimpleStringProperty tipoVoluntario) {
		this.tipoVoluntario = tipoVoluntario;
	}

	/**
	 * @return the statusVoluntario
	 */
	public SimpleStringProperty getStatusVoluntario() {
		return statusVoluntario;
	}

	/**
	 * @param statusVoluntario the statusVoluntario to set
	 */
	public void setStatusVoluntario(SimpleStringProperty statusVoluntario) {
		this.statusVoluntario = statusVoluntario;
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

}
