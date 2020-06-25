/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.eao;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.covidsemfome.model.Doacao;
import com.covidsemfome.model.Doador;
import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class DoadorEAO extends TGenericEAO<Doador> {

	@SuppressWarnings("unchecked")
	public List<Doador> pesquisar(String nome, Date dataNascimento){
		
		StringBuffer sbf = new StringBuffer("select distinct e from Doador e left join e.doacoes d where 1=1 ");
		
		if(StringUtils.isNotBlank(nome))
			sbf.append("and e.nome like :nome ");
		
		if(dataNascimento!=null)
			sbf.append("and e.dataNascimento = :dataNascimento ");
		
		sbf.append("order by e.nome");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		
		if(StringUtils.isNotBlank(nome))
			qry.setParameter("nome", "%"+nome+"%");
		
		if(dataNascimento!=null)
			qry.setParameter("dataNascimento", dataNascimento);
		
	
		
		return qry.getResultList();
	}
	
	
	@Override
	public void beforePersist(Doador entidade)	throws Exception {
		childsReference(entidade);
	}
	
	@Override
	public void beforeMerge(Doador entidade) throws Exception {
		childsReference(entidade);
	}
	
	@Override
	public void beforeRemove(Doador entidade) throws Exception {
		childsReference(entidade);
	}
	
	
	public void childsReference(Doador entidade)throws Exception {
	
		if(entidade.getDoacoes()!=null){
			for(final Doacao e : entidade.getDoacoes())
				e.setDoador(entidade);
		}
	}
	
	
}
