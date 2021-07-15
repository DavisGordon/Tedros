/**
 * 
 */
package com.solidarity.ejb.eao;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import com.solidarity.model.Acao;
import com.solidarity.model.Pessoa;
import com.solidarity.model.Voluntario;
import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class VoluntarioEAO extends TGenericEAO<Voluntario> {

	public Voluntario recuperar(Acao acao, Pessoa pess){
		StringBuffer sbf = new StringBuffer("select distinct e from Voluntario e  where e.acao.id = :acao and e.pessoa.id = :pess");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		qry.setParameter("acao", acao.getId());
		qry.setParameter("pess", pess.getId());
		
		return (Voluntario) qry.getSingleResult();
	}
	
	public boolean isVoluntario(Pessoa pess){
		StringBuffer sbf = new StringBuffer("select count(e) from Voluntario e where e.pessoa.id = :pess");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		qry.setParameter("pess", pess.getId());
		
		return ((Long) qry.getSingleResult()) > 0;
	}
	
	@Override
	public void afterRemove(Voluntario entity) throws Exception {
		flush();
	}

	@Override
	public void afterPersist(Voluntario entity) throws Exception {
		flush();
	}
	
	@Override
	public void afterMerge(Voluntario entity) throws Exception {
		flush();
	}
	
	private void flush() {
		super.getEntityManager().flush();
	}
	
	
}
