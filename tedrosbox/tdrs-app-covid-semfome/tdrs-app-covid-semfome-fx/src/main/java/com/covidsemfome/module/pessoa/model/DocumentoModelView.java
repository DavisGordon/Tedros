package com.covidsemfome.module.pessoa.model;

import java.util.Date;

import com.covidsemfome.model.Documento;
import com.covidsemfome.module.pessoa.trigger.TDocumentoTrigger;
import com.covidsemfome.module.pessoa.validator.DocumentoValidator;
import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TMaskField;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.control.TTrigger;
import com.tedros.fxapi.annotation.control.TValidator;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TDetailListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.presenter.entity.behavior.TDetailCrudViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TDetailCrudViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.util.TPropertyUtil;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;

@TFormReaderHtml
@TForm(showBreadcrumBar=true, name = "#{form.document.name}")
@TDetailListViewPresenter(presenter=@TPresenter(
		behavior = @TBehavior(type = TDetailCrudViewBehavior.class), 
		decorator = @TDecorator(type = TDetailCrudViewDecorator.class, viewTitle="#{view.document.name}")))
public class DocumentoModelView extends TEntityModelView<Documento>{
	
	private SimpleLongProperty id;
	
	@TLabel(text="#{label.tipo}")
	@TReaderHtml(codeValues={@TCodeValue(code = "1", value = "#{label.identity}"), 
			@TCodeValue(code = "2", value = "#{label.cpf}"),
			@TCodeValue(code = "3", value = "#{label.cnpj}")})
	@TTrigger(	targetFieldName="numero", 
				triggerClass=TDocumentoTrigger.class, 
				runAfterFormBuild=true,
				associatedFieldBox={"dataEmissao", "dataValidade", "orgaoEmissor"})
	@THorizontalRadioGroup(required=true, alignment=Pos.CENTER_LEFT, spacing=4, 
	radioButtons={
			@TRadioButtonField(text = "#{label.identity}", userData = "1"),
			@TRadioButtonField(text = "#{label.cpf}", userData = "2"), 
			@TRadioButtonField(text = "#{label.cnpj}", userData = "3")
			})
	private SimpleStringProperty tipo;
	
	
	@TReaderHtml
	@TLabel(text = "Numero")
	@TMaskField(required=true, textInputControl=@TTextInputControl(promptText="Identidade, Cpf ou Cnpj", parse = true),
	mask="######################")
	@TValidator(validatorClass = DocumentoValidator.class, associatedFieldsName={"tipo"})
	@THBox(	pane=@TPane(children={"numero","dataEmissao","dataValidade","orgaoEmissor"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="numero", priority=Priority.ALWAYS), 
   				   		@TPriority(field="dataEmissao", priority=Priority.ALWAYS), 
   				   		@TPriority(field="dataValidade", priority=Priority.ALWAYS), 
   				   		@TPriority(field="orgaoEmissor", priority=Priority.ALWAYS) }))
	private SimpleStringProperty numero;
	
	@TReaderHtml
	@TLabel(text="Data de emissão")
	@TDatePickerField
	private SimpleObjectProperty<Date> dataEmissao;
	
	@TReaderHtml
	@TLabel(text="Valido até")
	@TDatePickerField
	private SimpleObjectProperty<Date> dataValidade;
	
	@TReaderHtml
	@TLabel(text="Orgão emissor")
	@TTextField(maxLength=100)
	private SimpleStringProperty orgaoEmissor;
	
	@TReaderHtml
	@TLabel(text="Observação")
	@TTextAreaField(maxLength=300, control=@TControl(prefWidth=250, prefHeight=200, parse = true))
	private SimpleStringProperty observacao;
	
	
	public DocumentoModelView(Documento entidade) {
		super(entidade);
	}
	
	@Override
	public String toString() {
		String tipo = getTipoDescricao(); 
		if(tipo.equals(""))
			return (getNumero()!=null)? getNumero().getValue() : "";
		else
			return (tipo+": "+getNumero()!=null)? getNumero().getValue() : "";
	}
	
	public String getTipoDescricao(){
		if(TPropertyUtil.isStringValueNotBlank(getTipo())){
			if(getTipo().equals("1"))
				return "Identidade";
			if(getTipo().equals("2"))
				return "CPF";
			if(getTipo().equals("3"))
				return "CNPJ";
		}
		return "";
	}
		
	
	public SimpleStringProperty getTipo() {
		return tipo;
	}


	public void setTipo(SimpleStringProperty tipo) {
		this.tipo = tipo;
	}


	public SimpleStringProperty getNumero() {
		return numero;
	}


	public void setNumero(SimpleStringProperty numero) {
		this.numero = numero;
	}


	public SimpleObjectProperty<Date> getDataEmissao() {
		return dataEmissao;
	}


	public void setDataEmissao(SimpleObjectProperty<Date> dataEmissao) {
		this.dataEmissao = dataEmissao;
	}


	public SimpleObjectProperty<Date> getDataValidade() {
		return dataValidade;
	}


	public void setDataValidade(SimpleObjectProperty<Date> dataValidade) {
		this.dataValidade = dataValidade;
	}


	public SimpleStringProperty getOrgaoEmissor() {
		return orgaoEmissor;
	}


	public void setOrgaoEmissor(SimpleStringProperty orgaoEmissor) {
		this.orgaoEmissor = orgaoEmissor;
	}


	public SimpleStringProperty getObservacao() {
		return observacao;
	}


	public void setObservacao(SimpleStringProperty observacao) {
		this.observacao = observacao;
	}
	
	@Override
	public SimpleStringProperty getDisplayProperty() {
		return numero;
	}

	public final SimpleLongProperty getId() {
		return id;
	}

	public final void setId(SimpleLongProperty id) {
		this.id = id;
	}

}
