/**
 * 
 */
package com.covidsemfome.ejb.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;

import com.covidsemfome.model.Acao;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * @author Davis Gordon
 *
 */
@Local
@Remote
public interface IAcaoService extends ITEjbService<Acao> {

	public List<Acao> listAcoesParaExibirNoPainel();
}
