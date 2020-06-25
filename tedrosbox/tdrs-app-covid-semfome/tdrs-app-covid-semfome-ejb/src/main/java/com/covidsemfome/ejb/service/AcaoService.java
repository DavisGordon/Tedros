/**
 * 
 */
package com.covidsemfome.ejb.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.covidsemfome.ejb.bo.AcaoBO;
import com.covidsemfome.model.Acao;
import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.service.TEjbService;
import com.tedros.ejb.base.service.TResult;
import com.tedros.ejb.base.service.TResult.EnumResult;

/**
 * @author Davis Gordon
 *
 */
@Stateless(name="IAcaoService")
public class AcaoService extends TEjbService<Acao> implements IAcaoService{
	
	@Inject
	private AcaoBO bo;

	@Override
	public ITGenericBO<Acao> getBussinesObject() {
		// TODO Auto-generated method stub
		return bo;
	}
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public TResult<List<Acao>> listAcoesParaExibirNoPainel(){
		try{
			List<Acao> lst = bo.listAcoesDoDiaAnteriorEmDiante();
			return new TResult<>(EnumResult.SUCESS, "", lst);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}

}
