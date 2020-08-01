/**
 * 
 */
package com.covidsemfome.ejb.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.covidsemfome.ejb.bo.AcaoBO;
import com.covidsemfome.ejb.bo.EmailBO;
import com.covidsemfome.ejb.exception.EmailBusinessException;
import com.covidsemfome.model.Acao;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.Voluntario;
import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.service.TEjbService;
import com.tedros.util.TSentEmailException;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="IAcaoService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
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
	@TransactionAttribute(value = TransactionAttributeType.NEVER)
	public void enviarEmailSairAcao(Pessoa p, Acao a) throws TSentEmailException, EmailBusinessException{
		emailBO.enviarEmailSairAcao(p, a);
	}
	@TransactionAttribute(value = TransactionAttributeType.NEVER)
	public void enviarEmailParticiparAcao(Voluntario v) throws TSentEmailException, EmailBusinessException{
		emailBO.enviarEmailParticiparAcao(v);
	}
	
	public List<Acao> pesquisar(String ids, String titulo, Date dataInicio, Date dataFim, String status){
		return bo.pesquisar(ids, titulo, dataInicio, dataFim, status);
	}
	

}
