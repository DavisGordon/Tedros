/**
 * 
 */
package com.covidsemfome.ejb.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityExistsException;

import com.covidsemfome.ejb.exception.EmailBusinessException;
import com.covidsemfome.ejb.producer.Item;
import com.covidsemfome.ejb.service.AcaoService;
import com.covidsemfome.ejb.service.TipoAjudaService;
import com.covidsemfome.ejb.service.VoluntarioService;
import com.covidsemfome.model.Acao;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.TipoAjuda;
import com.covidsemfome.model.Voluntario;
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.service.ITEjbService;
import com.tedros.util.TSentEmailException;

/**
 * @author Davis Gordon
 *
 */
@Stateless(name="IVoluntarioController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class VoluntarioController extends TEjbController<Voluntario> implements IVoluntarioController {
	
	@EJB(beanName="IVoluntarioService")
	private VoluntarioService serv;
	
	@EJB(beanName="ITipoAjudaService")
	private TipoAjudaService tpServ;
	
	@EJB(beanName="IAcaoService")
	private AcaoService aServ;
 
	@Inject
	@Named("errorMsg")
	private Item<String> errorMsg;
	
	public TResult<List<Acao>> participarEmAcao(Pessoa pessoa, Long acaoId, List<Long> tiposAjuda){
		
		try{
			
			Acao acao = new Acao();
			acao.setId(acaoId);
			
			acao = aServ.find(acao);
			
			Set<TipoAjuda> tiposAjudaSet = new HashSet<>();
			for (Long id : tiposAjuda) {
				TipoAjuda t = new TipoAjuda();
				t.setId(id);
				
				TipoAjuda tpRes = tpServ.find(t);
				tiposAjudaSet.add(tpRes);
			}
			
			Voluntario v = getVoluntario(acao, pessoa);
			
			if(v==null){
				v = new Voluntario();
				v.setAcao(acao);
				v.setPessoa(pessoa);
				acao.getVoluntarios().add(v);
			}
			
			v.setTiposAjuda(tiposAjudaSet);
			aServ.save(acao);
			
			aServ.enviarEmailParticiparAcao(v);
			
			List<Acao> lst = aServ.listAcoesParaExibirNoPainel();
			return new TResult<>(EnumResult.SUCESS, "", lst);
			
		}catch(Exception | TSentEmailException | EmailBusinessException e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, errorMsg.getValue());
		}
	}
	
	public TResult<List<Acao>> sairDaAcao(Pessoa pessoa, Long acaoId){
		try{
			Acao acao = new Acao();
			acao.setId(acaoId);
			
			acao = aServ.find(acao);
			
			Voluntario v = getVoluntario(acao, pessoa);
			if(v!=null){
				acao.getVoluntarios().remove(v);
				aServ.save(acao);
				aServ.enviarEmailSairAcao(pessoa, acao);
			}
			
			List<Acao> lst = aServ.listAcoesParaExibirNoPainel();
			
			return new TResult<>(EnumResult.SUCESS, "", lst);
			
		}catch(Exception | TSentEmailException | EmailBusinessException e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, errorMsg.getValue());
		}
	}


	@Override
	public TResult<Voluntario> save(Voluntario entidade) {
		
		try{
			
			Acao acao = aServ.find(entidade.getAcao());
			
			if(!entidade.isNew()){
				Voluntario v = null;
				for(Voluntario v1 : acao.getVoluntarios() )
					if(v1.getId().equals(entidade.getId())){
						v = v1;
						break;
					}
				acao.getVoluntarios().remove(v);
			}
			
			Pessoa pessoa = entidade.getPessoa();
			Voluntario v = getVoluntario(acao, pessoa);
			if(v!=null){
				return new TResult<>(EnumResult.WARNING, true, "Um registro já existe para a pessoa e ação selecionados!", entidade);
			}
				
			acao.getVoluntarios().add(entidade);
			
			acao = aServ.save(acao);
			
			v = getVoluntario(acao, pessoa);
			
			return new TResult<>(EnumResult.SUCESS, v);
			
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}

	/**
	 * @param acao
	 * @param pessoa
	 * @return
	 */
	private Voluntario getVoluntario(Acao acao, Pessoa pessoa) {
		Voluntario v = null;
		for(Voluntario v1 : acao.getVoluntarios() )
			if(v1.getPessoa().getId().equals(pessoa.getId())){
				v = v1;
				break;
			}
		return v;
	}
	
	@Override
	public TResult<Voluntario> remove(Voluntario entidade) {
		try{
			
			Acao acao = aServ.find(entidade.getAcao());
			
			if(!entidade.isNew()){
				Voluntario v = null;
				for(Voluntario v1 : acao.getVoluntarios() )
					if(v1.getId().equals(entidade.getId())){
						v = v1;
						break;
					}
				acao.getVoluntarios().remove(v);
				acao = aServ.save(acao);
			}
			
			
			return new TResult<>(EnumResult.SUCESS);
			
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}

	@Override
	public ITEjbService<Voluntario> getService() {
		return serv;
	}

	

}
