/**
 * 
 */
package com.covidsemfome.ejb.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.covidsemfome.ejb.service.AcaoService;
import com.covidsemfome.model.Acao;
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * @author Davis Gordon
 *
 */
@Stateless(name="IAcaoController")
public class AcaoController extends TEjbController<Acao> implements IAcaoController{
	
	@EJB
	private AcaoService serv;

	
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

}
