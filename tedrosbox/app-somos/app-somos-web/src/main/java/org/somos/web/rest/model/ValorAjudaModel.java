/**
 * 
 */
package org.somos.web.rest.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.math.NumberUtils;
import org.somos.model.ValorAjuda;

/**
 * @author Davis Gordon
 *
 */
@XmlRootElement
public class ValorAjudaModel implements Serializable, Comparable<ValorAjudaModel>{

	private static final long serialVersionUID = -5547851464811308933L;
	
	private Long id;
	private String valor;
	private String plano;
	
	public ValorAjudaModel() {
	}

	public ValorAjudaModel(ValorAjuda e) {
		super();
		this.id = e.getId();
		this.valor = e.getValor();
		this.plano = e.getPlanoPayPal();
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
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @return the plano
	 */
	public String getPlano() {
		return plano;
	}

	/**
	 * @param plano the plano to set
	 */
	public void setPlano(String plano) {
		this.plano = plano;
	}
	
	@Override
	public int compareTo(ValorAjudaModel o) {
		if(o!=null && NumberUtils.isCreatable(valor) && NumberUtils.isCreatable(o.getValor())){
			BigDecimal v1 = new BigDecimal(valor);
			BigDecimal v2 = new BigDecimal(valor);
			return v1.compareTo(v2);
		}
		
		return valor!=null && o!=null && o.getValor()!=null 
				? valor.compareTo(o.getValor())
						: 0;
	}
	
}
