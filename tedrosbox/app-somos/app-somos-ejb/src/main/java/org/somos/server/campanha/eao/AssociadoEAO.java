/**
 * 
 */
package org.somos.server.campanha.eao;

import javax.persistence.Query;

import org.somos.model.Associado;
import org.somos.model.Pessoa;

import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
public class AssociadoEAO extends TGenericEAO<Associado> {

	public Associado recuperar(Pessoa p){
		StringBuffer sbf = new StringBuffer("select distinct e from Associado e  "
				+ "where e.pessoa.id = :pess");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		qry.setParameter("pess", p.getId());
		
		return (Associado) qry.getSingleResult();
	}
	
	
}
