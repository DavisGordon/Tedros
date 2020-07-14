/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.controller;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.covidsemfome.ejb.service.DoacaoService;
import com.covidsemfome.model.Doacao;
import com.covidsemfome.model.TipoAjuda;
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 **/
@Stateless(name="IDoacaoController")
public class DoacaoController extends TEjbController<Doacao> implements IDoacaoController {
	
	@EJB
	private DoacaoService serv;
	
	public TResult<List<Doacao>> pesquisar(String nome, Date dataInicio, Date dataFim, Long acaoId, TipoAjuda tipoAjuda  ){
		try{
			List<Doacao> list = serv.pesquisar(nome, dataInicio, dataFim, acaoId, tipoAjuda);
			return new TResult<List<Doacao>>(EnumResult.SUCESS, list);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<List<Doacao>>(EnumResult.ERROR, e.getMessage());
		}
		
	}


	@Override
	public ITEjbService<Doacao> getService() {
		return serv;
	}

	
}
