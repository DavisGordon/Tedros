package com.tedros.global.brasil.module.pessoa.model;

import java.util.Date;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;

import org.apache.commons.lang3.builder.HashCodeBuilder;

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
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TDetailCrudViewWithListViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TDetailCrudViewWithListViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.util.TPropertyUtil;
import com.tedros.global.brasil.model.Documento;
import com.tedros.global.brasil.module.pessoa.trigger.TDocumentoTrigger;
import com.tedros.global.brasil.module.pessoa.validator.DocumentoValidator;

@TForm(showBreadcrumBar=true, name = "#{form.document.name}")
@TFormReaderHtml
@TPresenter(type = TDynaPresenter.class,
behavior = @TBehavior(type = TDetailCrudViewWithListViewBehavior.class), 
decorator = @TDecorator(type = TDetailCrudViewWithListViewDecorator.class, viewTitle="#{view.document.name}", listTitle="#{label.select}"))
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
		
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj == null || !(obj instanceof DocumentoModelView))
			return false;
		
		DocumentoModelView p = (DocumentoModelView) obj;
		
		if(getId()!=null && getId().getValue()!=null &&  p.getId()!=null && p.getId().getValue()!=null){
			if(!(getId().getValue().equals(Long.valueOf(0)) && p.getId().getValue().equals(Long.valueOf(0))))
				return getId().getValue().equals(p.getId().getValue());
		}	
		
		String str1 = getTipo()!=null && getTipo().getValue()!=null ? getTipo().getValue() : "";
		str1 += getNumero()!=null && getNumero().getValue()!=null ? getNumero().getValue() : "";
		
		String str2 = p.getTipo()!=null && p.getTipo().getValue()!=null ? p.getTipo().getValue() : "";
		str2 += p.getNumero()!=null && p.getNumero().getValue()!=null ? p.getNumero().getValue() : "";
		
		return str1.equals(str2);
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
