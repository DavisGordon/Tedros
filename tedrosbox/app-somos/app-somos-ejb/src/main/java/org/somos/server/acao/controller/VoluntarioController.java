/**
 * 
 */
package org.somos.server.acao.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.somos.ejb.controller.IVoluntarioController;
import org.somos.model.Acao;
import org.somos.model.Pessoa;
import org.somos.model.TipoAjuda;
import org.somos.model.Voluntario;
import org.somos.server.acao.service.AcaoService;
import org.somos.server.acao.service.TipoAjudaService;
import org.somos.server.acao.service.VoluntarioService;
import org.somos.server.exception.EmailBusinessException;
import org.somos.server.pessoa.service.PessoaService;
import org.somos.server.producer.Item;

import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TRemoteSecurity;
import com.tedros.ejb.base.service.ITEjbService;
import com.tedros.util.TSentEmailException;

/**
 * @author Davis Gordon
 *
 */
@TRemoteSecurity
@Stateless(name="IVoluntarioController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class VoluntarioController extends TSecureEjbController<Voluntario> implements IVoluntarioController, ITSecurity {
	
	@EJB(beanName="IVoluntarioService")
	private VoluntarioService serv;
	
	@EJB(beanName="IPessoaService")
	private PessoaService pServ;
	
	@EJB(beanName="ITipoAjudaService")
	private TipoAjudaService tpServ;
	
	@EJB(beanName="IAcaoService")
	private AcaoService aServ;

	@EJB
	private ITSecurityController securityController;
	
 
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
			
			if(pessoa.isTermoAdesaoNecessario(tiposAjudaSet) && !pessoa.isTermoAdesaoElegivel()) {
				return new TResult<>(EnumResult.WARNING, "Favor informar seus dados pessoais primeiro.");
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
			
			String termo = null;
			if(pessoa.isTermoAdesaoNecessario(tiposAjudaSet) && pessoa.isTermoAdesaoElegivel()) {
				termo = pServ.gerarTermoAdesao(pessoa, tiposAjudaSet, acao.getData());
			}
			
			aServ.enviarEmailParticiparAcao(v, termo);
			
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
			
			if(acao!=null){
				Set<Voluntario> l = new HashSet<>();
				for(Voluntario v : acao.getVoluntarios()) {
					if(v.getPessoa().getId().equals(pessoa.getId()))
						continue;
					l.add(v);
				}
				acao.setVoluntarios(l);
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
	public TResult<Voluntario> save(TAccessToken token, Voluntario entidade) {
		
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
	public TResult<Voluntario> remove(TAccessToken token, Voluntario entidade) {
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

	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}

	

}
