/**
 * 
 */
package com.covidsemfome.report.model;

import java.util.Date;
import java.util.List;

import com.covidsemfome.model.Contato;
import com.covidsemfome.model.Pessoa;
import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
public class PessoaModel extends TEntity {

	private static final long serialVersionUID = 7964845555411113410L;
	
	private String nome;
	
	private String tipo;
	
	private String email;
	
	private String contatos;
	
	private Date dataCadastro;
	
	private List<AcaoItemModel> acoes;
	
	/**
	 * 
	 */
	public PessoaModel() {
		// TODO Auto-generated constructor stub
	}

	public PessoaModel(Pessoa v) {
		this.nome = v.getNome();
		String tipo = v.getTipoVoluntario();
		if(tipo!=null && tipo.equals("1"))
			this.tipo = "Operacional";
		else if(tipo!=null && tipo.equals("2"))
			this.tipo = "Estratégico";
		else if(tipo!=null && tipo.equals("3"))
			this.tipo = "Estratégico (Email)";
		else if(tipo!=null && tipo.equals("4"))
			this.tipo = "Doador/Filatrópico";
		else if(tipo!=null && tipo.equals("5"))
			this.tipo = "Cadastro/Site";
		else if(tipo!=null && tipo.equals("6"))
			this.tipo = "Outro";
		this.email = v.getLoginName();
		this.dataCadastro = v.getInsertDate();
		String ct = "";
		if(v.getContatos()!=null)
			for(Contato c : v.getContatos()){
				if(this.email!=null 
						&& this.email.toLowerCase().trim().equals(c.getDescricao().toLowerCase().trim()))
					continue;
				
				ct += (!ct.isEmpty()) ? "," + c.getDescricao() : c.getDescricao();
			}
		
		this.contatos = ct;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the contatos
	 */
	public String getContatos() {
		return contatos;
	}

	/**
	 * @param contatos the contatos to set
	 */
	public void setContatos(String contatos) {
		this.contatos = contatos;
	}

	/**
	 * @return the acoes
	 */
	public List<AcaoItemModel> getAcoes() {
		return acoes;
	}

	/**
	 * @param acoes the acoes to set
	 */
	public void setAcoes(List<AcaoItemModel> acoes) {
		this.acoes = acoes;
	}

	/**
	 * @return the dataCadastro
	 */
	public Date getDataCadastro() {
		return dataCadastro;
	}

	/**
	 * @param dataCadastro the dataCadastro to set
	 */
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}



}
