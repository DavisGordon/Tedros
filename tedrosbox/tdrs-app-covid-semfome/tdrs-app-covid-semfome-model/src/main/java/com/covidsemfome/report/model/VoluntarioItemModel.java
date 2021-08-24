/**
 * 
 */
package com.covidsemfome.report.model;

import java.util.Date;

import com.covidsemfome.model.Contato;
import com.covidsemfome.model.TipoAjuda;
import com.covidsemfome.model.Voluntario;
import com.tedros.ejb.base.model.ITModel;

/**
 * @author Davis Gordon
 *
 */
public class VoluntarioItemModel implements ITModel {

	private static final long serialVersionUID = 7964845555411113410L;

	
	private Long id;
	
	private String titulo;
	
	private Date data;
	
	private String nome;
	
	private String tipo;
	
	private String email;
	
	private String contatos;
	
	private String tiposAjuda;
	
	
	/**
	 * 
	 */
	public VoluntarioItemModel() {
		// TODO Auto-generated constructor stub
	}

	public VoluntarioItemModel(Voluntario v) {
		this.id = v.getAcao().getId();
		this.titulo = v.getAcao().getTitulo();
		this.data = v.getAcao().getData();
		this.nome = v.getPessoa().getNome();
		String tipo = v.getPessoa().getTipoVoluntario();
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
		this.email = v.getPessoa().getLoginName();
		
		String ct = "";
		if(v.getPessoa().getContatos()!=null)
			for(Contato c : v.getPessoa().getContatos()){
				if(this.email!=null 
						&& this.email.toLowerCase().trim().equals(c.getDescricao().toLowerCase().trim()))
					continue;
				
				ct += (!ct.isEmpty()) ? ", " + c.getDescricao() : c.getDescricao();
			}
		
		this.contatos = ct;
		
		String ta = "";
		if(v.getTiposAjuda()!=null)
			for(TipoAjuda c : v.getTiposAjuda())
				ta += (!ta.isEmpty()) ? ", " + c.getDescricao() : c.getDescricao();
		this.tiposAjuda = ta;
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
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}


	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	/**
	 * @return the data
	 */
	public Date getData() {
		return data;
	}


	/**
	 * @param data the data to set
	 */
	public void setData(Date data) {
		this.data = data;
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
	 * @return the tiposAjuda
	 */
	public String getTiposAjuda() {
		return tiposAjuda;
	}

	/**
	 * @param tiposAjuda the tiposAjuda to set
	 */
	public void setTiposAjuda(String tiposAjuda) {
		this.tiposAjuda = tiposAjuda;
	}



}
