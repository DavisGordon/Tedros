/**
 * 
 */
package org.somos.server.acao.controller;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.somos.ejb.controller.IAcaoController;
import org.somos.model.Acao;
import org.somos.server.acao.service.AcaoService;

import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TRemoteSecurity;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * @author Davis Gordon
 *
 */
@TRemoteSecurity
@Stateless(name="IAcaoController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class AcaoController extends TSecureEjbController<Acao> implements IAcaoController, ITSecurity{
	
	@EJB
	private AcaoService serv;
	
	@EJB
	private ITSecurityController securityController;
	
	public TResult<List<Acao>> pesquisar(TAccessToken token, String ids, String titulo, Date dataInicio, Date dataFim, String status, String orderby, String ordertype){
		try{
			List<Acao> lst = serv.pesquisar(ids, titulo, dataInicio, dataFim, status, orderby, ordertype);
			return new TResult<>(EnumResult.SUCESS, "", lst);
		}catch(Exception e){
			return super.processException(token, null, e);
		}
	}

	public TResult<List<Acao>> listAcoesProgramadasParaDecisao(){
		try{
			List<Acao> lst = serv.listAcoesProgramadasParaDecisao();
			return new TResult<>(EnumResult.SUCESS, "", lst);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}
	
	public TResult<List<Acao>> listAcoesParaExibirNoPainel(){
		try{
			List<Acao> lst = serv.listAcoesParaExibirNoPainel();
			return new TResult<>(EnumResult.SUCESS, "", lst);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}


	@Override
	public ITEjbService<Acao> getService() {
		return serv;
	}

	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}

}
