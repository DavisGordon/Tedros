/**
 * 
 */
package org.somos.server.acao.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.somos.model.Acao;
import org.somos.model.Pessoa;
import org.somos.model.Voluntario;
import org.somos.server.acao.bo.AcaoBO;
import org.somos.server.acao.bo.EmailBO;
import org.somos.server.exception.EmailBusinessException;

import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.service.TEjbService;
import com.tedros.util.TSentEmailException;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="IAcaoService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class AcaoService extends TEjbService<Acao> {
	
	@Inject
	private AcaoBO bo;
	
	@Inject 
	private EmailBO emailBO;

	@Override
	public ITGenericBO<Acao> getBussinesObject() {
		return bo;
	}
	
	public List<Acao> listAcoesProgramadasParaDecisao(){
		return bo.listAcoesProgramadasParaDecisao();
	}
	@TransactionAttribute(value = TransactionAttributeType.NEVER)
	public List<Acao> listAcoesParaExibirNoPainel(){
		return bo.listAcoesDoDiaAnteriorEmDiante();
	}
	
	public void enviarEmailProgramadasParaDecisao(List<Acao> lst) throws TSentEmailException, EmailBusinessException{
		emailBO.enviarEmailAcoesProgramadasParaDecisao(lst);
	}
	@TransactionAttribute(value = TransactionAttributeType.NEVER)
	public void enviarEmailSairAcao(Pessoa p, Acao a) throws TSentEmailException, EmailBusinessException{
		emailBO.enviarEmailSairAcao(p, a);
	}
	@TransactionAttribute(value = TransactionAttributeType.NEVER)
	public void enviarEmailParticiparAcao(Voluntario v, String termo) throws TSentEmailException, EmailBusinessException{
		emailBO.enviarEmailParticiparAcao(v, termo);
	}
	
	public List<Acao> pesquisar(String ids, String titulo, Date dataInicio, Date dataFim, String status, String orderby, String ordertype){
		return bo.pesquisar(ids, titulo, dataInicio, dataFim, status, orderby, ordertype);
	}
	

}
