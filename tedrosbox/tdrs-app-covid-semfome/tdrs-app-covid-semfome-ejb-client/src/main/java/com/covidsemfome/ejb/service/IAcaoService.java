/**
 * 
 */
package com.covidsemfome.ejb.service;

import java.util.List;

import javax.ejb.Remote;

import com.covidsemfome.model.Acao;
import com.tedros.ejb.base.service.ITEjbService;
import com.tedros.ejb.base.service.TResult;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IAcaoService extends ITEjbService<Acao> {

	public TResult<List<Acao>> listAcoesParaExibirNoPainel();
}
