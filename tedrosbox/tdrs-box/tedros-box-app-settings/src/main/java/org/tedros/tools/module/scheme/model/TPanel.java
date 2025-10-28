/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 11/08/2014
 */
package org.tedros.tools.module.scheme.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.tedros.server.model.ITModel;

import javafx.scene.paint.Color;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author davis.dun
 *
 */
public class TPanel implements ITModel {
	
	private static final long serialVersionUID = 1L;

	private String modo;
	
	private Color painelCorTexto;
	private Double painelTamTexto;
	private Color painelCorFundo;
	private Double painelOpacidade;
	
	private Color botaoCorTexto;
	private Double botaoTamTexto;
	private Color botaoCorFundo;
	private Color botaoCorBorda;
	private Double botaoOpacidade;
	
	private Color campoCorTitulo;
	private Double campoTamTitulo;
	
	private Color campoCorTexto;
	private Color campoCorFundo;
	private Color campoCorBorda;
	private Double campoTamTexto;
	private Boolean campoTextoNegrito;
	
	private Color formCorFundo;
	private Double formOpacidade;
	
	private Color readerCorTituloCampo;
	private Double readerTamTituloCampo;
	
	private Color readerCorTexto;
	private Double readerTamTexto;
	
	private Color readerCorFundo;
	private Double readerOpacidade;
	
	public Color getPainelCorTexto() {
		return painelCorTexto;
	}
	public void setPainelCorTexto(Color painelCorTexto) {
		this.painelCorTexto = painelCorTexto;
	}
	public Double getPainelTamTexto() {
		return painelTamTexto;
	}
	public void setPainelTamTexto(Double painelTamTexto) {
		this.painelTamTexto = painelTamTexto;
	}
	public Color getPainelCorFundo() {
		return painelCorFundo;
	}
	public void setPainelCorFundo(Color painelCorFundo) {
		this.painelCorFundo = painelCorFundo;
	}
	public Double getPainelOpacidade() {
		return painelOpacidade;
	}
	public void setPainelOpacidade(Double painelOpacidade) {
		this.painelOpacidade = painelOpacidade;
	}
	public Color getBotaoCorTexto() {
		return botaoCorTexto;
	}
	public void setBotaoCorTexto(Color botaoCorTexto) {
		this.botaoCorTexto = botaoCorTexto;
	}
	public Double getBotaoTamTexto() {
		return botaoTamTexto;
	}
	public void setBotaoTamTexto(Double botaoTamTexto) {
		this.botaoTamTexto = botaoTamTexto;
	}
	public Color getBotaoCorFundo() {
		return botaoCorFundo;
	}
	public void setBotaoCorFundo(Color botaoCorFundo) {
		this.botaoCorFundo = botaoCorFundo;
	}
	public Color getBotaoCorBorda() {
		return botaoCorBorda;
	}
	public void setBotaoCorBorda(Color botaoCorBorda) {
		this.botaoCorBorda = botaoCorBorda;
	}
	public Double getBotaoOpacidade() {
		return botaoOpacidade;
	}
	public void setBotaoOpacidade(Double botaoOpacidade) {
		this.botaoOpacidade = botaoOpacidade;
	}
	public Color getCampoCorTitulo() {
		return campoCorTitulo;
	}
	public void setCampoCorTitulo(Color campoCorTitulo) {
		this.campoCorTitulo = campoCorTitulo;
	}
	public Double getCampoTamTitulo() {
		return campoTamTitulo;
	}
	public void setCampoTamTitulo(Double campoTamTitulo) {
		this.campoTamTitulo = campoTamTitulo;
	}
	public Color getCampoCorTexto() {
		return campoCorTexto;
	}
	public void setCampoCorTexto(Color campoCorTexto) {
		this.campoCorTexto = campoCorTexto;
	}
	public Color getCampoCorFundo() {
		return campoCorFundo;
	}
	public void setCampoCorFundo(Color campoCorFundo) {
		this.campoCorFundo = campoCorFundo;
	}
	public Color getCampoCorBorda() {
		return campoCorBorda;
	}
	public void setCampoCorBorda(Color campoCorBorda) {
		this.campoCorBorda = campoCorBorda;
	}
	public Double getCampoTamTexto() {
		return campoTamTexto;
	}
	public void setCampoTamTexto(Double campoTamTexto) {
		this.campoTamTexto = campoTamTexto;
	}
	public Boolean getCampoTextoNegrito() {
		return campoTextoNegrito;
	}
	public void setCampoTextoNegrito(Boolean campoTextoNegrito) {
		this.campoTextoNegrito = campoTextoNegrito;
	}
	public Color getFormCorFundo() {
		return formCorFundo;
	}
	public void setFormCorFundo(Color formCorFundo) {
		this.formCorFundo = formCorFundo;
	}
	public Double getFormOpacidade() {
		return formOpacidade;
	}
	public void setFormOpacidade(Double formOpacidade) {
		this.formOpacidade = formOpacidade;
	}
	public Color getReaderCorTituloCampo() {
		return readerCorTituloCampo;
	}
	public void setReaderCorTituloCampo(Color readerCorTituloCampo) {
		this.readerCorTituloCampo = readerCorTituloCampo;
	}
	public Double getReaderTamTituloCampo() {
		return readerTamTituloCampo;
	}
	public void setReaderTamTituloCampo(Double readerTamTituloCampo) {
		this.readerTamTituloCampo = readerTamTituloCampo;
	}
	public Color getReaderCorFundo() {
		return readerCorFundo;
	}
	public void setReaderCorFundo(Color readerCorFundo) {
		this.readerCorFundo = readerCorFundo;
	}
	public Double getReaderOpacidade() {
		return readerOpacidade;
	}
	public void setReaderOpacidade(Double readerOpacidade) {
		this.readerOpacidade = readerOpacidade;
	}
	public String getModo() {
		return modo;
	}
	public void setModo(String modo) {
		this.modo = modo;
	}
	
	public Color getReaderCorTexto() {
		return readerCorTexto;
	}
	public void setReaderCorTexto(Color readerCorTexto) {
		this.readerCorTexto = readerCorTexto;
	}
	public Double getReaderTamTexto() {
		return readerTamTexto;
	}
	public void setReaderTamTexto(Double readerTamTexto) {
		this.readerTamTexto = readerTamTexto;
	}
	
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}
	
	
	
	

}
