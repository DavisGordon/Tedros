/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 24/02/2014
 */
package com.tedros.global.brasil.module.pessoa.model;

import java.util.Date;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.SetChangeListener;
import javafx.geometry.Pos;

import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TMaskField;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.control.TTrigger;
import com.tedros.fxapi.annotation.control.TValidator;
import com.tedros.fxapi.annotation.filter.TFilter;
import com.tedros.fxapi.annotation.filter.TFilterTableColumn;
import com.tedros.fxapi.annotation.filter.TFilterTableView;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TMainCrudViewWithListViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TMainCrudViewWithListViewDecorator;
import com.tedros.fxapi.presenter.filter.TFilterModelView;
import com.tedros.global.brasil.model.Pessoa;
import com.tedros.global.brasil.module.pessoa.form.PessoaFilterForm;
import com.tedros.global.brasil.module.pessoa.process.TPessoaFilterProcess;
import com.tedros.global.brasil.module.pessoa.table.TipoPessoaTableCell;
import com.tedros.global.brasil.module.pessoa.trigger.TDocumentoTrigger;
import com.tedros.global.brasil.module.pessoa.validator.DocumentoValidator;

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
