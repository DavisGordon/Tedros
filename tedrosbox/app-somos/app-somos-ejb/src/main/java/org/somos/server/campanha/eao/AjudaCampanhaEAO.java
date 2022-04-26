/**
 * 
 */
package org.somos.server.campanha.eao;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import org.somos.model.AjudaCampanha;
import org.somos.model.Campanha;
import org.somos.model.FormaAjuda;

import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class AjudaCampanhaEAO extends TGenericEAO<AjudaCampanha> {

	public List<AjudaCampanha> recuperar(Campanha c, FormaAjuda fa, String p, Date dtIni){
		StringBuffer sbf = new StringBuffer("select distinct e from AjudaCampanha e "
				+ "join e.campanha c"
				+ "left join e.formaAjuda fa "
				+ "where 1=1 ");
		
		//if(c!=null)
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		//qry.setParameter("pess", p.getId());
		return qry.getResultList();
	}
	
	
}
