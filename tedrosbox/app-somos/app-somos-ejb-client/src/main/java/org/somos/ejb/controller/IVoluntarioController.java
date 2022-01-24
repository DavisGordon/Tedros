/**
 * 
 */
package org.somos.ejb.controller;

import java.util.List;

import javax.ejb.Remote;

import org.somos.model.Acao;
import org.somos.model.Pessoa;
import org.somos.model.Voluntario;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.ejb.base.result.TResult;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IVoluntarioController extends ITSecureEjbController<Voluntario>{

	public TResult<List<Acao>> participarEmAcao(Pessoa pessoa, Long acaoId, List<Long> tiposAjuda);
	
	public TResult<List<Acao>> sairDaAcao(Pessoa pessoa, Long acaoId);
}
