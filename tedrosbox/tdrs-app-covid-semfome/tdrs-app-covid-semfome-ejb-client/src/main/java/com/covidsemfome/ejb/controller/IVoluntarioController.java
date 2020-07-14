/**
 * 
 */
package com.covidsemfome.ejb.service;

import java.util.List;

import javax.ejb.Remote;

import com.covidsemfome.model.Acao;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.Voluntario;
import com.tedros.ejb.base.service.ITEjbService;
import com.tedros.ejb.base.service.TResult;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IVoluntarioController extends ITEjbService<Voluntario> {

	public TResult<List<Acao>> participarEmAcao(Pessoa pessoa, Long acaoId, List<Long> tiposAjuda);
	
	public TResult<List<Acao>> sairDaAcao(Pessoa pessoa, Long acaoId);
}
