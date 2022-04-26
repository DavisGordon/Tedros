/**
 * 
 */
package org.somos.server.campanha.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.somos.model.AjudaCampanha;
import org.somos.model.Associado;
import org.somos.model.Pessoa;
import org.somos.server.acao.bo.EmailBO;
import org.somos.server.campanha.bo.AssociadoBO;
import org.somos.server.exception.EmailBusinessException;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.service.TEjbService;
import com.tedros.util.TSentEmailException;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="AssociadoService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class AssociadoService extends TEjbService<Associado>  {

	@Inject
	private AssociadoBO bo;
	
	@Inject
	private EmailBO emailBo;
	
	@Override
	public TGenericBO<Associado> getBussinesObject() {
		return bo;
	}
	
	public Associado recuperar(Pessoa p){
		return bo.recuperar(p);
	}

	@TransactionAttribute(value = TransactionAttributeType.NEVER)
	public void enviarEmailCancelarAjudaCampanha(String titulo, String nome, String contato) throws EmailBusinessException, TSentEmailException {
		emailBo.enviarEmailCancelarAjudaCampanha(titulo, nome, contato);
	}
	
	@TransactionAttribute(value = TransactionAttributeType.NEVER)
	public void enviarEmailAjudaCampanha(AjudaCampanha ac) throws EmailBusinessException, TSentEmailException {
		emailBo.enviarEmailAjudaCampanha(ac.getCampanha().getTitulo(), ac.getAssociado().getPessoa().getNome(),
				ac.getAssociado().getPessoa().getTodosContatos(), ac.getValor(), ac.getPeriodo(), 
				ac.getFormaAjuda()!=null ? ac.getFormaAjuda().getTipo() : null);
	}

}
