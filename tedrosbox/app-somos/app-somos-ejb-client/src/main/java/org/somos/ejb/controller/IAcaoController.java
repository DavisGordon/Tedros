/**
 * 
 */
package org.somos.ejb.controller;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import org.somos.model.Acao;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.security.TAccessToken;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IAcaoController extends ITSecureEjbController<Acao> {

	public TResult<List<Acao>> pesquisar(TAccessToken token, String ids, String titulo, Date dataInicio,
			Date dataFim, String status, String orderby, String ordertype);
		
	
	public TResult<List<Acao>> listAcoesProgramadasParaDecisao();
	
	public TResult<List<Acao>> listAcoesParaExibirNoPainel();
}
