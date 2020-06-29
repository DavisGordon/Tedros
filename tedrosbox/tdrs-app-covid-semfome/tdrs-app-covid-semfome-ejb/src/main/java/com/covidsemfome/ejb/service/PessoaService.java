/**
 * 
 */
package com.covidsemfome.ejb.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.covidsemfome.ejb.bo.EmailBO;
import com.covidsemfome.ejb.bo.PessoaBO;
import com.covidsemfome.ejb.bo.UserBO;
import com.covidsemfome.model.Contato;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.User;
import com.tedros.ejb.base.service.TEjbService;
import com.tedros.ejb.base.service.TResult;
import com.tedros.ejb.base.service.TResult.EnumResult;

/**
 * @author Davis Gordon
 *
 */
@Stateless(name="IPessoaService")
public class PessoaService extends TEjbService<Pessoa> implements IPessoaService {

	@Inject
	private PessoaBO bo;
	
	@Inject 
	private EmailBO emailBO;
	
	@Inject
	private UserBO userBO;
	
	@Override
	public PessoaBO getBussinesObject() {
		return bo;
	}
	
	@Override
	public TResult<Pessoa> remove(Pessoa entidade) {
		
		List<User> lst = userBO.recuperar(entidade);
		
		try{
			if(lst!=null && !lst.isEmpty())
				for(User u : lst)
					userBO.remove(u);
			
			return super.remove(entidade);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<Pessoa>(EnumResult.ERROR, e.getMessage());
		}
	}
	
	@SuppressWarnings("rawtypes")
	public TResult recuperar(String loginName, String password){
		try{
			Pessoa pess = getBussinesObject().recuperar(loginName, password);
			return new TResult<Pessoa>(EnumResult.SUCESS, pess);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<Pessoa>(EnumResult.ERROR, e.getMessage());
		}
	}
	
	@SuppressWarnings("rawtypes")
	public TResult pesquisar(String nome, Date dataNascimento, String tipo, String tipoDocumento, String numero){
		try{
			List<Pessoa> list = getBussinesObject().pesquisar(nome, dataNascimento, tipo, tipoDocumento, numero);
			return new TResult<List<Pessoa>>(EnumResult.SUCESS, list);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<List<Pessoa>>(EnumResult.ERROR, e.getMessage());
		}
		
	}

	public TResult<Pessoa> saveFromSite(Pessoa entidade) {
		
		
		String email = null;
		String tel = null;
		if(entidade.getContatos()!=null){
			for(Contato c : entidade.getContatos()){
				if(c.getTipo()!=null && c.getTipo().equals("1"))
					email = c.getDescricao();
				if(c.getTipo()!=null && c.getTipo().equals("2"))
					tel = c.getDescricao();
			}
		}
		
		if(bo.isPessoaContatoExiste(entidade.getNome(), email, tel)){
			return new TResult<Pessoa>(EnumResult.WARNING);
		}
		
		TResult<Pessoa>  res = super.save(entidade);
		
		emailBO.enviarEmailBoasVindas(entidade);
		
		
		return res;
	}

}
