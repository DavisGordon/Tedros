/**
 * 
 */
package com.covidsemfome.ejb.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

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
			excluirVol(pessoa, acao);
			
			Set<TipoAjuda> tiposAjudaSet = new HashSet<>();
			for (Long id : tiposAjuda) {
				TipoAjuda t = new TipoAjuda();
				t.setId(id);
				
				TipoAjuda tpRes = tpServ.find(t);
				tiposAjudaSet.add(tpRes);
			}
			
			Voluntario v = new Voluntario();
			v.setAcao(acao);
			v.setPessoa(pessoa);
			v.setTiposAjuda(tiposAjudaSet);
			
			v = serv.save(v);
			
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
			excluirVol(pessoa, acao);
			
			aServ.enviarEmailSairAcao(pessoa, acao);
			
			List<Acao> lst = aServ.listAcoesParaExibirNoPainel();
			return new TResult<>(EnumResult.SUCESS, "", lst);
		}catch(Exception | TSentEmailException | EmailBusinessException e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, errorMsg.getValue());
		}
	}

	private void excluirVol(Pessoa pessoa, Acao acao) throws Exception {
		Voluntario v = new Voluntario();
		v.setAcao(acao);
		v.setPessoa(pessoa);
		
		v = serv.find(v);
		
		if(v!=null)
			remove(v);
	}
	
	

	@Override
	public TResult<Voluntario> save(Voluntario entidade) {
		
		try{
			if(!entidade.isNew()){
				serv.remove(entidade);
				entidade.setId(null);
			}
			
			Voluntario  v = serv.save(entidade);
			return new TResult<>(EnumResult.SUCESS, v);
			
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
