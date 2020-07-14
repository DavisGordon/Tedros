/**
 * 
 */
package com.covidsemfome.ejb.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.covidsemfome.ejb.bo.AcaoBO;
import com.covidsemfome.ejb.bo.EmailBO;
import com.covidsemfome.model.Acao;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.Voluntario;
import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.service.TEjbService;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="IAcaoService")
public class AcaoService extends TEjbService<Acao> {
	
	@Inject
	private AcaoBO bo;
	
	@Inject 
	private EmailBO emailBO;

	@Override
	public ITGenericBO<Acao> getBussinesObject() {
		return bo;
	}
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public List<Acao> listAcoesParaExibirNoPainel(){
		return bo.listAcoesDoDiaAnteriorEmDiante();
	}
	@TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
	public void enviarEmailSairAcao(Pessoa p, Acao a){
		emailBO.enviarEmailSairAcao(p, a);
	}
	@TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
	public void enviarEmailParticiparAcao(Voluntario v){
		emailBO.enviarEmailParticiparAcao(v);
	}
	
	

}
