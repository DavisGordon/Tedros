/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.somos.server.campanha.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.somos.ejb.controller.IAssociadoController;
import org.somos.model.AjudaCampanha;
import org.somos.model.Associado;
import org.somos.model.Campanha;
import org.somos.model.FormaAjuda;
import org.somos.model.Pessoa;
import org.somos.server.base.service.TStatelessService;
import org.somos.server.campanha.service.AjudaCampanhaService;
import org.somos.server.campanha.service.AssociadoService;
import org.somos.server.campanha.service.CampanhaService;
import org.somos.server.exception.EmailBusinessException;

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
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 **/
@TRemoteSecurity
@Stateless(name="IAssociadoController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class AssociadoController extends TSecureEjbController<Associado> implements IAssociadoController, ITSecurity {
	
	@EJB
	private AssociadoService serv;

	@EJB
	private CampanhaService cServ;
	
	@EJB
	private AjudaCampanhaService acServ;
	
	@EJB
	private TStatelessService<FormaAjuda> faServ;
	
	@EJB
	private ITSecurityController securityController;

	@Override
	public ITEjbService<Associado> getService() {
		return serv;
	}

	/**
	 * @return the securityController
	 */
	public ITSecurityController getSecurityController() {
		return securityController;
	}
	
	public TResult<Associado> cancelarAjuda(TAccessToken token, Pessoa p, Long idCamp){
		try {
			
			Associado a = serv.recuperar(p);
			if(a!=null) { 
				String nome = a.getPessoa().getNome();
				String titulo = null;
				String contato = a.getPessoa().getTodosContatos();
				if(a.getAjudaCampanhas()!=null) {
					Optional<AjudaCampanha> op = a.getAjudaCampanhas().stream().filter(e->{
						return e.getCampanha().getId().equals(idCamp);
					}).findFirst();
					
					if(op.isPresent()) {
						AjudaCampanha ac = op.get();
						a.getAjudaCampanhas().remove(ac);
						ac.setAssociado(null);
						titulo = ac.getCampanha().getTitulo();
					}
				}
				
				if(a.getAjudaCampanhas()!=null && !a.getAjudaCampanhas().isEmpty())
					a = serv.save(a);
				else {
					serv.remove(a);
					return new TResult<>(EnumResult.SUCESS, null);
				}
				if(titulo!=null) {
					try {
						serv.enviarEmailCancelarAjudaCampanha(titulo, nome, contato);
					} catch (EmailBusinessException | TSentEmailException e) {
						e.printStackTrace();
					}
				}
			}
			
			return new TResult<>(EnumResult.SUCESS, a);
			
		}catch(Exception e) {
			return super.processException(token, null, e);
		}
	}
	
	public TResult<Associado> ajudarCampanha(TAccessToken token, Pessoa p, 
			Long idCamp, String valor, String periodo, Long idForma){
		try {
			
			Associado a = serv.recuperar(p);
			AjudaCampanha ac = new AjudaCampanha();
			
			if(a==null) {
				a = new Associado();
				a.setPessoa(p);
				a.setAjudaCampanhas(new ArrayList<>());
				a.getAjudaCampanhas().add(ac);
			}else if(a.getAjudaCampanhas()!=null && !a.getAjudaCampanhas().isEmpty()) {
				Optional<AjudaCampanha> op = a.getAjudaCampanhas().stream().filter(e->{
					return e.getCampanha().getId().equals(idCamp);
				}).findFirst();
				
				if(op.isPresent())
					ac = op.get();
				else
					a.getAjudaCampanhas().add(ac);
			}
			
			if(idForma!=null) {
				FormaAjuda fa = new FormaAjuda();
				fa.setId(idForma);
				fa = faServ.findById(fa);
				ac.setFormaAjuda(fa);
			}
			
			Campanha c = new Campanha();
			c.setId(idCamp);
			c = cServ.findById(c);
			ac.setAssociado(a);
			ac.setCampanha(c);
			ac.setValor(valor);
			ac.setPeriodo(periodo);
			ac.setDataProximo(new Date());
			
			a = serv.save(a);
			
			try {
				serv.enviarEmailAjudaCampanha(ac);
			} catch (EmailBusinessException | TSentEmailException e) {
				e.printStackTrace();
			}
			
			return new TResult<>(EnumResult.SUCESS, a);
			
		}catch(Exception e) {
			return super.processException(token, null, e);
		}
	}

	@Override
	public TResult<Associado> recuperar(TAccessToken token, Pessoa p) {
		try {
			Associado e = serv.recuperar(p);
			return new TResult<>(EnumResult.SUCESS, e);
		}catch(Exception e) {
			return super.processException(token, null, e);
		}
	}

	
}
