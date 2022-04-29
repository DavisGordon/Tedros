/**
 * 
 */
package org.somos.server.campanha.eao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import org.somos.model.Campanha;

import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class CampanhaEAO extends TGenericEAO<Campanha> {

	@SuppressWarnings("unchecked")
	public List<Campanha> listarValidos(){
		StringBuffer sbf = new StringBuffer("select distinct e from Campanha e  "
				+ "where e.status = 'ATIVADO' "
				+ "and ( e.dataFim is null "
				+ "or e.dataFim >= SQL('CURRENT_DATE')) "
				+ "order by e.id desc");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		
		return (List<Campanha>) qry.getResultList();
	}
}
