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
	
	public boolean isPessoaContatoExiste(String nome, String email, String telefone){
		
		StringBuffer sbf = new StringBuffer("select distinct e from Pessoa e left join e.contatos d where 1=1 ");
		
		if(StringUtils.isNotBlank(nome))
			sbf.append("and e.nome = :nome and ");
		
		if(StringUtils.isNotBlank(email))
			sbf.append(" (d.tipo = '1' and d.descricao = :email) ");
		
		if(StringUtils.isNotBlank(telefone)){
			if(StringUtils.isNotBlank(email))
				sbf.append(" or ");
			sbf.append(" (d.tipo = '2' and d.descricao = :telefone) ");
		}
		
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		
		if(StringUtils.isNotBlank(nome))
			qry.setParameter("nome",nome);
		
		if(StringUtils.isNotBlank(email))
			qry.setParameter("email", email);
		
		if(StringUtils.isNotBlank(telefone))
			qry.setParameter("telefone", telefone);
		
		List<Pessoa> lst = qry.getResultList();
		
		return lst!=null && lst.size() > 0;
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
