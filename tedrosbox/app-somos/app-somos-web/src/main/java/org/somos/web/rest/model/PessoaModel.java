/**
 * 
 */
package org.somos.web.rest.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.somos.model.Contato;
import org.somos.model.Pessoa;
import org.somos.web.rest.util.ApiUtils;

/**
 * @author Davis Gordon
 *
 */
@XmlRootElement 
public class PessoaModel implements Serializable{

	private static final long serialVersionUID = 865491492217210586L;

	private Long id;
	
	private String nome;
	
	private String contato;
	
	private String tipo;
	
	private String status;
	
	private String statusDesc;
	
	private String observacao;
	
	private String dataCadastro;

	/**
	 * 
	 */
	public PessoaModel() {
	}
	/**
	 * @param id
	 * @param nome
	 * @param contato
	 * @param tipo
	 * @param status
	 * @param statusDesc
	 * @param observacao
	 */
	public PessoaModel(Pessoa p) {
		this.id = p.getId();
		this.nome = p.getNome();
		this.dataCadastro = ApiUtils.formatDateHourToView(p.getInsertDate());
		String c = "";
		if(p.getContatos()!=null)
			for(Contato e : p.getContatos()){
				String d = e.getDescricao();
				String s = e.getTipo().equals("1") ? "mailto" : "tel";
					c += "".equals(c) ? "<a href='"+s+":"+d+"'>"+d+"</a>" : ", " + "<a href='"+s+":"+d+"'>"+d+"</a>";
				
			}
		
		this.contato =c;
		switch (p.getTipoVoluntario()) {
		case "1": this.tipo ="Operacional"; break;
		case "2": this.tipo ="Estratégico"; break;
		case "3": this.tipo ="Estratégico (Receber emails)"; break;
		case "4": this.tipo ="Doador/Filatrópico"; break;
		case "5": this.tipo ="Cadastro/Site"; break;
		case "6": this.tipo ="Outro"; break;
		}
		
		this.status = p.getStatusVoluntario();
		switch (p.getStatusVoluntario()) {
		case "1": this.statusDesc ="Aguardando"; break;
		case "2": this.statusDesc ="Contactado"; break;
		case "3": this.statusDesc ="Voluntário"; break;
		case "4": this.statusDesc ="Voluntário Ativo"; break;
		case "5": this.statusDesc ="Voluntário problematico"; break;
		case "6": this.statusDesc ="Desligado"; break;
		}
		this.observacao = p.getObservacao();
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * @return the contato
	 */
	public String getContato() {
		return contato;
	}
	/**
	 * @param contato the contato to set
	 */
	public void setContato(String contato) {
		this.contato = contato;
	}
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the statusDesc
	 */
	public String getStatusDesc() {
		return statusDesc;
	}
	/**
	 * @param statusDesc the statusDesc to set
	 */
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	/**
	 * @return the observacao
	 */
	public String getObservacao() {
		return observacao;
	}
	/**
	 * @param observacao the observacao to set
	 */
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	/**
	 * @return the dataCadastro
	 */
	public String getDataCadastro() {
		return dataCadastro;
	}
	/**
	 * @param dataCadastro the dataCadastro to set
	 */
	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	
}
