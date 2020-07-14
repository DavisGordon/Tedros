/**
 * 
 */
package com.covidsemfome.ejb.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.covidsemfome.model.Acao;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.TipoAjuda;
import com.covidsemfome.model.Voluntario;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.service.TResult;
import com.tedros.ejb.base.service.TResult.EnumResult;

/**
 * @author Davis Gordon
 *
 */
@Stateless(name="IVoluntarioController")
public class VoluntarioController implements IVoluntarioController {
	
	@EJB(beanName="IVoluntarioService")
	private IVoluntarioService serv;
	
	@EJB(beanName="ITipoAjudaService")
	private ITipoAjudaService tpServ;
	
	@EJB(beanName="IAcaoService")
	private IAcaoService aServ;
 
	@Override
	public TResult find(Voluntario entidade) {
		return serv.find(entidade);
	}

	public TResult<List<Acao>> participarEmAcao(Pessoa pessoa, Long acaoId, List<Long> tiposAjuda){
		try{
			Set<TipoAjuda> tiposAjudaSet = new HashSet<>();
			for (Long id : tiposAjuda) {
				TipoAjuda t = new TipoAjuda();
				t.setId(id);
				
				TResult<TipoAjuda> tpRes = tpServ.find(t);
				tiposAjudaSet.add(tpRes.getValue());
			}
			
			TResult<Voluntario> res = excluirVol(pessoa, acaoId);
			
			Acao acao = new Acao();
			acao.setId(acaoId);
			
			TResult<Acao> aRes = aServ.find(acao);
			acao = aRes.getValue();
			
			Voluntario v = new Voluntario();
			v.setAcao(acao);
			v.setPessoa(pessoa);
			v.setTiposAjuda(tiposAjudaSet);
			
			res = save(v);
			
			if(res.getResult().equals(EnumResult.SUCESS))
				return aServ.listAcoesParaExibirNoPainel();
			else 
				return new TResult<>(EnumResult.ERROR, res.getMessage());
			
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}
	
	public TResult<List<Acao>> sairDaAcao(Pessoa pessoa, Long acaoId){
		try{
			
			TResult<Voluntario> res = excluirVol(pessoa, acaoId);
			
			if(res.getResult().equals(EnumResult.SUCESS))
				return aServ.listAcoesParaExibirNoPainel();
			else 
				return new TResult<>(EnumResult.ERROR, res.getMessage());
			
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}

	private TResult<Voluntario> excluirVol(Pessoa pessoa, Long acaoId) {
		Acao acao = new Acao();
		acao.setId(acaoId);
		
		TResult<Acao> aRes = aServ.find(acao);
		acao = aRes.getValue();
		
		Voluntario v = new Voluntario();
		v.setAcao(acao);
		v.setPessoa(pessoa);
		
		TResult<Voluntario> res = find(v);
		
		if(res.getResult().equals(EnumResult.SUCESS))
			v = res.getValue();
		if(v!=null)
			res = remove(v);
		
		return res;
	}
	
	@Override
	public TResult<List<Voluntario>> findAll(Voluntario entity) throws Exception {
		return serv.findAll(entity);
	}

	@Override
	public TResult<Voluntario> save(Voluntario entidade) {
		
		TResult<Voluntario> rem = null;
		
		if(!entidade.isNew()){
			rem = serv.remove(entidade);
			entidade.setId(null);
		}
		
		if(rem!=null && !rem.getResult().equals(EnumResult.SUCESS))
			return rem;
		else
			return serv.save(entidade);
	}

	@Override
	public TResult<Voluntario> remove(Voluntario entidade) {
		return serv.remove(entidade);
	}

	@Override
	public TResult listAll(Class<? extends ITEntity> entidadeClass) {
		return serv.listAll(entidadeClass);
	}

	@Override
	public TResult pageAll(Class<? extends ITEntity> entidade, int firstResult, int maxResult) {
		return serv.pageAll(entidade, firstResult, maxResult);
	}

	@Override
	public TResult countAll(Class<? extends ITEntity> entidade) {
		return serv.countAll(entidade);
	}
	
	

}
