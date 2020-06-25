/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 24/02/2014
 */
package com.covidsemfome.module.pessoa.model;

import java.util.Date;

import com.covidsemfome.model.Pessoa;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.presenter.filter.TFilterModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */

/*@TFilter( 	filterProcessClass = TPessoaFilterProcess.class, 
			formClass = PessoaFilterForm.class,
			tableView = 
			@TFilterTableView(
					columns = {	
							@TFilterTableColumn(columnName = "Nome", fieldName = "nome", order=1),
							@TFilterTableColumn(columnName = "Tipo da Pessoa", fieldName = "tipo", order=2, tableCellClass=TipoPessoaTableCell.class),
							@TFilterTableColumn(columnName = "Aniversario", fieldName = "dataNascimento", datePattern="dd/MM", order=3)
							}))*/
public class PessoaFilterModelView extends TFilterModelView<Pessoa> {
	
	
	private SimpleLongProperty id;
	
	/**
	 * Campo tipoPessoa - {@link SimpleStringProperty}
	 * */
	@TLabel(text="Tipo da pessoa")
	@THorizontalRadioGroup(	alignment=Pos.CENTER_LEFT, 
	radioButtons={	
				@TRadioButtonField(text = "Fisica", userData = "F"), 
				@TRadioButtonField(text = "Juridica", userData = "J")})
	private SimpleStringProperty tipoPessoa;
	
	/**
	 * Campo nome - {@link SimpleStringProperty}
	 * */
	@TLabel(text="Nome")
	@TTextField(maxLength=60, 
				textInputControl=@TTextInputControl(promptText="Nome/Razão social", parse = true),
				control=@TControl(tooltip="Nome para pessoas fisicas ou Razão social para pessoas juridicas", parse = true))
	private SimpleStringProperty nome;
	
	/**
	 * Campo data nascimento - {@link SimpleObjectProperty}
	 * */
	@TLabel(text="Data de Nascimento")
	@TDatePickerField
	private SimpleObjectProperty<Date> dataNascimento;
	
	
	public PessoaFilterModelView(Pessoa pessoa) {
		super(pessoa);
	}

	public final SimpleStringProperty getNome() {
		return nome;
	}

	public final void setNome(SimpleStringProperty nome) {
		this.nome = nome;
	}

	public final SimpleObjectProperty<Date> getDataNascimento() {
		return dataNascimento;
	}

	public final void setDataNascimento(SimpleObjectProperty<Date> dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public final SimpleStringProperty getTipoPessoa() {
		return tipoPessoa;
	}

	public final void setTipoPessoa(SimpleStringProperty tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}


	@Override
	public SimpleStringProperty getDisplayProperty() {
		return nome;
	}


	@Override
	public void setId(SimpleLongProperty id) {
		this.id = id;
		
	}

	@Override
	public SimpleLongProperty getId() {
		return id;
	}

	

}
