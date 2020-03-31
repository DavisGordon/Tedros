/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.tedros.global.brasil.ejb.eao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.tedros.ejb.base.eao.TGenericEAO;
import com.tedros.global.brasil.model.Contato;
import com.tedros.global.brasil.model.Documento;
import com.tedros.global.brasil.model.Pessoa;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class PessoaEAO extends TGenericEAO<Pessoa> {

	@SuppressWarnings("unchecked")
	public List<Pessoa> pesquisar(EntityManager em, String nome, Date dataNascimento, String tipo, String tipoDocumento, String numero){
		
		StringBuffer sbf = new StringBuffer("select distinct e from Pessoa e left join e.documentos d where 1=1 ");
		
		if(StringUtils.isNotBlank(nome))
			sbf.append("and e.nome like :nome ");
		
		if(dataNascimento!=null)
			sbf.append("and e.dataNascimento = :dataNascimento ");
		
		if(StringUtils.isNotBlank(tipo))
			sbf.append("and e.tipo = :tipo ");
		
		if(StringUtils.isNotBlank(tipoDocumento))
			sbf.append("and d.tipo = :tipoDocumento ");
		
		if(StringUtils.isNotBlank(numero))
			sbf.append("and d.numero = :numero ");
		
		sbf.append("order by e.nome");
		
		Query qry = em.createQuery(sbf.toString());
		
		if(StringUtils.isNotBlank(nome))
			qry.setParameter("nome", "%"+nome+"%");
		
		if(dataNascimento!=null)
			qry.setParameter("dataNascimento", dataNascimento);
		
		if(StringUtils.isNotBlank(tipo))
			qry.setParameter("tipo", tipo);
		
		if(StringUtils.isNotBlank(tipoDocumento))
			qry.setParameter("tipoDocumento", tipoDocumento);
		
		if(StringUtils.isNotBlank(numero))
			qry.setParameter("numero", numero);
		
		return qry.getResultList();
	}
	
	
	@Override
	public void beforePersist(EntityManager em, Pessoa entidade)	throws Exception {
		childsReference(em, entidade);
	}
	
	@Override
	public void beforeMerge(EntityManager em, Pessoa entidade) throws Exception {
		childsReference(em, entidade);
	}
	
	@Override
	public void beforeRemove(EntityManager em, Pessoa entidade) throws Exception {
		childsReference(em, entidade);
	}
	
	
	public void childsReference(EntityManager em, Pessoa entidade)throws Exception {
		
		if(entidade.getContatos()!=null){
			for(final Contato e : entidade.getContatos())
				e.setPessoa(entidade);
		}
		
		if(entidade.getDocumentos()!=null){
			for(final Documento e : entidade.getDocumentos())
				e.setPessoa(entidade);
		}
	}
	
	
}
