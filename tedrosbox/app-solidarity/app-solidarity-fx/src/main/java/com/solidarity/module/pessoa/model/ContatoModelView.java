/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 16/01/2014
 */
package com.solidarity.module.pessoa.model;

import com.solidarity.model.Contato;
import com.solidarity.module.pessoa.table.TipoContatoCallback;
import com.solidarity.module.pessoa.trigger.TTipoContatoTrigger;
import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.control.TCallbackFactory;
import com.tedros.fxapi.annotation.control.TCellFactory;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TMaskField;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.control.TTrigger;
import com.tedros.fxapi.annotation.control.TValidator;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TDetailTableViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.presenter.entity.behavior.TDetailFieldBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TDetailFieldDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.util.TPropertyUtil;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(showBreadcrumBar=true, name = "#{form.contatos}")
@TDetailTableViewPresenter(
		presenter=@TPresenter(behavior=@TBehavior(type=TDetailFieldBehavior.class),
				decorator = @TDecorator(type=TDetailFieldDecorator.class, viewTitle="#{view.contatos}")
				),
		tableView=@TTableView(editable=true, 
			columns = { @TTableColumn(cellValue="tipo", text = "#{label.tipo}", resizable=true,
			cellFactory=@TCellFactory(parse = true, callBack=@TCallbackFactory(parse=true, value=TipoContatoCallback.class))), 
						@TTableColumn(cellValue="descricao", text = "#{label.descricao}", resizable=true)}))
public class ContatoModelView extends TEntityModelView<Contato> {
	
	private SimpleLongProperty id;
	
	@TReaderHtml(codeValues={@TCodeValue(code = "1", value = "#{label.email}"), 
			@TCodeValue(code = "2", value = "#{label.celnumber}"),
			@TCodeValue(code = "3", value = "#{label.housenumber}"),
			@TCodeValue(code = "4", value = "#{label.worknumber}")})
	@TLabel(text = "#{label.tipo}")
	@THorizontalRadioGroup(required=true, alignment=Pos.TOP_LEFT, spacing=4, 
			radioButtons = {@TRadioButtonField(text="#{label.email}", userData="1"), 
							@TRadioButtonField(text="#{label.celnumber}", userData="2"),
							@TRadioButtonField(text="#{label.housenumber}", userData="3"),
							@TRadioButtonField(text="#{label.worknumber}", userData="4")
			})
	@TTrigger(runAfterFormBuild = true, targetFieldName="descricao", 
	triggerClass=TTipoContatoTrigger.class, mode=TViewMode.EDIT)
	private SimpleStringProperty tipo;
	
	@TReaderHtml
	@TLabel(text="#{label.descricao}")
	@TMaskField(mask="##################################################", 
	node=@TNode(requestFocus=true, parse = true),
	textInputControl=@TTextInputControl(promptText="#{prompt.contato.desc}", parse = true),
	required=true)
	@TValidator(validatorClass = DocumentoValidator.class, associatedFieldsName={"tipo"})
	private SimpleStringProperty descricao;
	
	public ContatoModelView(Contato entidade) {
		super(entidade);
	}
	
	public String getTipoDescricao(){
		String id = TPropertyUtil.getValue(tipo);
		String desc = "";
		if(id.equals("1"))
			desc = "E-mail";
		if(id.equals("2"))
			desc = "Celular";
		if(id.equals("3"))
			desc = "Tel. Residencial";
		if(id.equals("4"))
			desc = "Tel. Comercial";
		
		return desc;
	}
	
	@Override
	public String toString() {
		return TPropertyUtil.getValue(descricao);	
	}
	
	public String getContatoCompleto(){
		return getTipoDescricao()+" "+TPropertyUtil.getValue(descricao);
	}
		
	
	public SimpleStringProperty getTipo() {
		return tipo;
	}


	public void setTipo(SimpleStringProperty tipo) {
		this.tipo = tipo;
	}


	public SimpleStringProperty getDescricao() {
		return descricao;
	}


	public void setDescricao(SimpleStringProperty descricao) {
		this.descricao = descricao;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return descricao;
	}

	public final SimpleLongProperty getId() {
		return id;
	}

	public final void setId(SimpleLongProperty id) {
		this.id = id;
	}

}
