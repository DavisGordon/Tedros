/**
 * 
 */
package com.solidarity.ejb.controller;

import java.util.List;

import javax.ejb.Remote;

import com.solidarity.model.Acao;
import com.solidarity.model.Pessoa;
import com.solidarity.model.Voluntario;
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
