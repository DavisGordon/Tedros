package com.tedros.global.brasil.module.pessoa.model;

import java.util.Date;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.util.TPropertyUtil;
import com.tedros.global.brasil.model.Documento;

public class DocumentoTableView extends TEntityModelView<Documento>{
	
	private SimpleLongProperty id;
	
	private SimpleStringProperty tipo;
	
	private SimpleStringProperty numero;
	
	private SimpleObjectProperty<Date> dataEmissao;
	
	private SimpleObjectProperty<Date> dataValidade;
	
	private SimpleStringProperty orgaoEmissor;
	
	private SimpleStringProperty observacao;
	
	
	public DocumentoTableView(Documento entidade) {
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
		
		if(obj == null || !(obj instanceof DocumentoTableView))
			return false;
		
		DocumentoTableView p = (DocumentoTableView) obj;
		
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
