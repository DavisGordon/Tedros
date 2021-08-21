/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.eao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.persistence.config.CacheUsage;
import org.eclipse.persistence.config.QueryHints;

import com.covidsemfome.model.Contato;
import com.covidsemfome.model.Documento;
import com.covidsemfome.model.Pessoa;
import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class PessoaEAO extends TGenericEAO<Pessoa> {
	
	@SuppressWarnings("unchecked")
	public List<Pessoa> estrategicoEmail(){
		
		StringBuffer sbf = new StringBuffer("select distinct e from Pessoa e where e.tipoVoluntario = :tipo ");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		
		qry.setParameter("tipo", "3");
		
		return (List<Pessoa>) qry.getResultList();
	}
	
	public Pessoa recuperar(String loginName, String password){
		
		StringBuffer sbf = new StringBuffer("select distinct e from Pessoa e where 1=1 ");
		
		if(StringUtils.isNotBlank(loginName))
			sbf.append("and e.loginName = :loginName ");
		
		if(StringUtils.isNotBlank(password))
			sbf.append("and e.password = :password ");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		
		if(StringUtils.isNotBlank(loginName))
			qry.setParameter("loginName", loginName);
		
		if(StringUtils.isNotBlank(password))
			qry.setParameter("password", password);
		
		
		return (Pessoa) qry.getSingleResult();
	}


	@SuppressWarnings("unchecked")
	public List<Pessoa> pesquisar(String nome, String tipo, String status, 
			Date dataInicio, Date dataFim, String orderBy, String orderType){
		
		StringBuffer sbf = new StringBuffer("select distinct e from Pessoa e "
				+ "left join e.contatos c "
				+ "where 1=1 ");
		
		if(StringUtils.isNotBlank(nome))
			sbf.append("and lower(e.nome) like :nome ");
		
		if(StringUtils.isNotBlank(tipo))
			sbf.append("and e.tipoVoluntario = :tipo ");
		
		if(StringUtils.isNotBlank(status))
			sbf.append("and e.statusVoluntario = :status ");
		
		if(dataInicio!=null && dataFim==null) {
			Calendar c = Calendar.getInstance();
			c.setTime(dataInicio);
			c.set(Calendar.HOUR, 23);
			c.set(Calendar.MINUTE, 59);
			dataFim = c.getTime();
		}else if(dataInicio!=null && dataFim!=null) {
			Calendar c = Calendar.getInstance();
			c.setTime(dataFim);
			c.set(Calendar.HOUR, 23);
			c.set(Calendar.MINUTE, 59);
			dataFim = c.getTime();
		}
		
		if(dataInicio!=null && dataFim!=null)
			sbf.append("and e.insertDate >= :dataInicio and e.insertDate <= :dataFim ");
		
		if(StringUtils.isNotBlank(orderBy)) {
			sbf.append("order by e."+orderBy);
			if(StringUtils.isNotBlank(orderType))
				sbf.append(" "+orderType);
			
		}
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		
		if(StringUtils.isNotBlank(nome))
			qry.setParameter("nome", "%"+nome.toLowerCase()+"%");
		
		if(StringUtils.isNotBlank(tipo))
			qry.setParameter("tipo", tipo);
		
		if(StringUtils.isNotBlank(status))
			qry.setParameter("status", status);
		
		if(dataInicio!=null && dataFim!=null){
			qry.setParameter("dataInicio", dataInicio);
			qry.setParameter("dataFim", dataFim);
		}
		
		qry.setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache);
		return qry.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Pessoa> pesquisar(String nome, Date dataNascimento, String tipo, String tipoDocumento, String numero){
		
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
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		
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
	
	public boolean isLoginExiste(String email){
		
		StringBuffer sbf = new StringBuffer("select count(e) from Pessoa e where e.loginName = :loginName ");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		
		qry.setParameter("loginName", email);
		
		Long total = (Long) qry.getSingleResult();
		
		return total > 0;
	}

	
	
	@Override
	public void beforePersist(Pessoa entidade)	throws Exception {
		childsReference(entidade);
	}
	
	@Override
	public void beforeMerge(Pessoa entidade) throws Exception {
		childsReference(entidade);
	}
	
	@Override
	public void beforeRemove(Pessoa entidade) throws Exception {
		childsReference(entidade);
	}
	
	
	public void childsReference(Pessoa entidade)throws Exception {
		
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
