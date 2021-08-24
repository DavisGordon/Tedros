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
	
	private String profissao;
	
	private String statusVoluntario;
	
	private Date dataNascimento;
	
	private String sexo;
	
	private String estadoCivil;
	
	private String observacao;
	
	private String status;
	
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
		this.profissao = v.getProfissao();
		if(v.getSexo()!=null) {
			switch(v.getSexo()) {
			case "M" : this.sexo = "Masculino"; break;
			case "F" : this.sexo = "Feminino"; break;
			case "O" : this.sexo = "Outro"; break;
			}
		}
		this.dataNascimento = v.getDataNascimento();
		this.estadoCivil = v.getEstadoCivil();
		this.observacao = v.getObservacao();
		this.status = v.getStatus();
		if(v.getStatusVoluntario()!=null) {
			switch(v.getStatusVoluntario()) {
			case "1": this.statusVoluntario ="Aguardando"; break;
			case "2": this.statusVoluntario ="Contactado"; break;
			case "3": this.statusVoluntario ="Voluntário"; break;
			case "4": this.statusVoluntario ="Voluntário Ativo"; break;
			case "5": this.statusVoluntario ="Voluntário problematico"; break;
			case "6": this.statusVoluntario ="Desligado"; break;
			}
		}
		
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

	/**
	 * @return the profissao
	 */
	public String getProfissao() {
		return profissao;
	}

	/**
	 * @param profissao the profissao to set
	 */
	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	/**
	 * @return the statusVoluntario
	 */
	public String getStatusVoluntario() {
		return statusVoluntario;
	}

	/**
	 * @param statusVoluntario the statusVoluntario to set
	 */
	public void setStatusVoluntario(String statusVoluntario) {
		this.statusVoluntario = statusVoluntario;
	}

	/**
	 * @return the dataNascimento
	 */
	public Date getDataNascimento() {
		return dataNascimento;
	}

	/**
	 * @param dataNascimento the dataNascimento to set
	 */
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	/**
	 * @return the sexo
	 */
	public String getSexo() {
		return sexo;
	}

	/**
	 * @param sexo the sexo to set
	 */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	/**
	 * @return the estadoCivil
	 */
	public String getEstadoCivil() {
		return estadoCivil;
	}

	/**
	 * @param estadoCivil the estadoCivil to set
	 */
	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
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



}
