/**
 * 
 */
package com.covidsemfome.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.ejb.base.model.TItemModel;

/**
 * @author Davis Gordon
 *
 */
public class Mailing extends Acao {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3471391811366058032L;

	private String tituloEmail;
	
	private String conteudo;
	
	private TItemModel<String> destino;
	
	private String emails;
	
	private String html;
	
	public Mailing() {
	}
	
	public Mailing(Long id, Integer versionNum, Date lastUpdate, Date insertDate, String titulo, String descricao,
			Date data, String status, String observacao, Integer qtdMinVoluntarios, Integer qtdMaxVoluntarios,
			BigDecimal vlrPrevisto, BigDecimal vlrArrecadado, BigDecimal vlrExecutado, Set<Voluntario> voluntarios) {
		super(id, versionNum, lastUpdate, insertDate, titulo, descricao, data, status, observacao, qtdMinVoluntarios,
				qtdMaxVoluntarios, vlrPrevisto, vlrArrecadado, vlrExecutado, voluntarios);
	}

	/**
	 * @return the tituloEmail
	 */
	public String getTituloEmail() {
		return tituloEmail;
	}

	/**
	 * @param tituloEmail the tituloEmail to set
	 */
	public void setTituloEmail(String tituloEmail) {
		this.tituloEmail = tituloEmail;
	}

	/**
	 * @return the conteudo
	 */
	public String getConteudo() {
		return conteudo;
	}

	/**
	 * @param conteudo the conteudo to set
	 */
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	/**
	 * @return the destino
	 */
	public TItemModel<String> getDestino() {
		return destino;
	}

	/**
	 * @param destino the destino to set
	 */
	public void setDestino(TItemModel<String> destino) {
		this.destino = destino;
	}

	/**
	 * @return the emails
	 */
	public String getEmails() {
		return emails;
	}

	/**
	 * @param emails the emails to set
	 */
	public void setEmails(String emails) {
		this.emails = emails;
	}

	/**
	 * @return the html
	 */
	public String getHtml() {
		return html;
	}

	/**
	 * @param html the html to set
	 */
	public void setHtml(String html) {
		this.html = html;
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
