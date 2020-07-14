/**
 * 
 */
package com.covidsemfome.ejb.controller;

import java.util.List;

import javax.ejb.Remote;

import com.covidsemfome.model.Acao;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.Voluntario;
import com.tedros.ejb.base.controller.ITEjbController;
import com.tedros.ejb.base.result.TResult;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IVoluntarioController extends ITEjbController<Voluntario>{

	public TResult<List<Acao>> participarEmAcao(Pessoa pessoa, Long acaoId, List<Long> tiposAjuda);
	
	public TResult<List<Acao>> sairDaAcao(Pessoa pessoa, Long acaoId);
}
