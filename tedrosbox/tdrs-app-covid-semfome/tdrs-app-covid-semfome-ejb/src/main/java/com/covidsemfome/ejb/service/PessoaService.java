/**
 * 
 */
package com.covidsemfome.ejb.service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.covidsemfome.ejb.bo.EmailBO;
import com.covidsemfome.ejb.bo.PessoaBO;
import com.covidsemfome.ejb.bo.UserBO;
import com.covidsemfome.ejb.bo.VoluntarioBO;
import com.covidsemfome.ejb.exception.EmailBusinessException;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.TipoAjuda;
import com.covidsemfome.model.User;
import com.tedros.ejb.base.service.TEjbService;
import com.tedros.util.TSentEmailException;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="IPessoaService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
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
	public String gerarTermoAdesao(Pessoa p, Set<TipoAjuda> lst, Date dataHoraAcao)throws Exception{
		return bo.gerarTermoAdesao(p, lst, dataHoraAcao);
	}
	
	
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public String newPass(Pessoa pess) throws Exception{
		
		String key = UUID.randomUUID().toString();
		pess.setNewPassKey(key);
		save(pess);
		
		return key;
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
	
	@TransactionAttribute(value = TransactionAttributeType.NEVER)
	public boolean isVoluntario(Pessoa entidade) throws Exception {	
		return volunBO.isVoluntario(entidade);
	}
	
	@TransactionAttribute(value = TransactionAttributeType.NEVER)
	public boolean isLoginEmUso(Pessoa entidade) throws Exception {	
		return bo.isLoginExiste(entidade.getLoginName());
	}
	
	@TransactionAttribute(value = TransactionAttributeType.NEVER)
	public Pessoa recuperar(String loginName, String password){
		return getBussinesObject().recuperar(loginName, password);
	}
	@TransactionAttribute(value = TransactionAttributeType.NEVER)
	public List<Pessoa> pesquisar(String nome, Date dataNascimento, String tipo, String tipoDocumento, String numero){
		return getBussinesObject().pesquisar(nome, dataNascimento, tipo, tipoDocumento, numero);
	}
	
	@TransactionAttribute(value = TransactionAttributeType.NEVER)
	public void enviarEmailCadastrado(Pessoa entidade) throws TSentEmailException, EmailBusinessException {
		emailBO.enviarEmailBoasVindas(entidade);
		emailBO.enviarEmailNovoVoluntario(entidade);
	}

	@TransactionAttribute(value = TransactionAttributeType.NEVER)
	public void enviarEmailNewPass(Pessoa entidade, String key) throws TSentEmailException {
		emailBO.enviarEmailNewPass(entidade, key);
	}
}
