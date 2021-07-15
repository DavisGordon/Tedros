/**
 * 
 */
package com.solidarity.ejb.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.solidarity.ejb.controller.IAcaoController;
import com.solidarity.ejb.service.AcaoService;
import com.solidarity.model.Acao;
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * @author Davis Gordon
 *
 */
@Stateless(name="IAcaoController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
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
