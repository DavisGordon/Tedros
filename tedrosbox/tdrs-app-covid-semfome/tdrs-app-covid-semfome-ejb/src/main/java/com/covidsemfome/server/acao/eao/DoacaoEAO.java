/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.server.acao.eao;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.covidsemfome.model.Doacao;
import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class DoacaoEAO extends TGenericEAO<Doacao> {

	public List<Doacao> pesquisar(String nome, Date dataInicio, Date dataFim, List<Long> acaoId, List<Long> tipoAjuda){
		
		
		StringBuffer sbf = new StringBuffer("select e from Doacao e inner join e.pessoa p inner join e.tipoAjuda t left join e.acao a where 1=1 ");
		
		if(StringUtils.isNotBlank(nome))
			sbf.append("and lower(p.nome) like :nome ");
		
		if(dataInicio!=null && dataFim==null)
			sbf.append("and e.data = :data ");
		
		if(dataInicio!=null && dataFim!=null)
			sbf.append("and e.data >= :dataInicio and e.data <= :dataFim ");
		
		if(acaoId!=null)
			sbf.append("and a.id in :acao ");
		
		if(tipoAjuda!=null)
			sbf.append("and t.id in :ta ");
		
		sbf.append("order by e.data desc ");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		
		if(StringUtils.isNotBlank(nome))
			qry.setParameter("nome", "%"+nome.toLowerCase()+"%");
		
		if(dataInicio!=null && dataFim==null)
			qry.setParameter("data", dataInicio);
		
		if(dataInicio!=null && dataFim!=null){
			qry.setParameter("dataInicio", dataInicio);
			qry.setParameter("dataFim", dataFim);
		}
		
		if(acaoId!=null)
			qry.setParameter("acao", acaoId);
		
		if(tipoAjuda!=null)
			qry.setParameter("ta", tipoAjuda);

		return qry.getResultList();
	}
	
}
