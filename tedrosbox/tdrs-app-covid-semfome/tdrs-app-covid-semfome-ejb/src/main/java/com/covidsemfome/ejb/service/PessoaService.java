/**
 * 
 */
package com.covidsemfome.ejb.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.covidsemfome.ejb.bo.EmailBO;
import com.covidsemfome.ejb.bo.PessoaBO;
import com.covidsemfome.ejb.bo.UserBO;
import com.covidsemfome.ejb.bo.VoluntarioBO;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.User;
import com.tedros.ejb.base.service.TEjbService;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="IPessoaService")
public class PessoaService extends TEjbService<Pessoa>  {

	@Inject
	private PessoaBO bo;
	
	@Inject 
	private EmailBO emailBO;
	
	@Inject
	private UserBO userBO;
	
	@Inject
	private VoluntarioBO volunBO;
	
	@Override
	public PessoaBO getBussinesObject() {
		return bo;
	}
	
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public Pessoa desativar(Pessoa entidade) throws Exception {
		
		List<User> lst = userBO.recuperar(entidade);
		
		if(lst!=null && !lst.isEmpty())
			for(User u : lst)
				userBO.remove(u);
		
		entidade.setStatus("DESATIVADO");
		return save(entidade);
	}
	
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public void remove(Pessoa entidade) throws Exception {
		
		List<User> lst = userBO.recuperar(entidade);
		
		if(lst!=null && !lst.isEmpty())
			for(User u : lst)
				userBO.remove(u);
		
		super.remove(entidade);
	}
	
	@TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
	public boolean isVoluntario(Pessoa entidade) throws Exception {	
		return volunBO.isVoluntario(entidade);
	}
	
	@TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
	public boolean isLoginEmUso(Pessoa entidade) throws Exception {	
		return bo.isLoginExiste(entidade.getLoginName());
	}
	
	@TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
	public Pessoa recuperar(String loginName, String password){
		return getBussinesObject().recuperar(loginName, password);
	}
	@TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
	public List<Pessoa> pesquisar(String nome, Date dataNascimento, String tipo, String tipoDocumento, String numero){
		return getBussinesObject().pesquisar(nome, dataNascimento, tipo, tipoDocumento, numero);
	}
	
	@TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
	public void enviarEmailCadastrado(Pessoa entidade) throws Exception {
		emailBO.enviarEmailBoasVindas(entidade);
		emailBO.enviarEmailNovoVoluntario(entidade);
	}

}
