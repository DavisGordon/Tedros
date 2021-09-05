/**
 * 
 */
package com.covidsemfome.ejb.controller;

import java.util.List;

import javax.ejb.Remote;

import com.covidsemfome.model.Acao;
import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.ejb.base.result.TResult;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IAcaoController extends ITSecureEjbController<Acao> {

	public TResult<List<Acao>> listAcoesProgramadasParaDecisao();
	
	public TResult<List<Acao>> listAcoesParaExibirNoPainel();
}
