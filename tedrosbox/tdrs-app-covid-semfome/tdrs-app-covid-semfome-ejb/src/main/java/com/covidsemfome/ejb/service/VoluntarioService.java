/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.covidsemfome.ejb.bo.AcaoBO;
import com.covidsemfome.ejb.bo.PessoaBO;
import com.covidsemfome.ejb.bo.VoluntarioBO;
import com.covidsemfome.model.Acao;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.Voluntario;
import com.tedros.ejb.base.service.TEjbService;
import com.tedros.ejb.base.service.TResult;
import com.tedros.ejb.base.service.TResult.EnumResult;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Stateless(name="IVoluntarioService")
public class VoluntarioService extends TEjbService<Voluntario> implements IVoluntarioService {
	
	@Inject
	private PessoaBO pessBO;
	
	@Inject
	private AcaoBO acaoBO;
	
	@Inject
	private VoluntarioBO bo;
	
	@Override
	public VoluntarioBO getBussinesObject() {
		return bo;
	}
	
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public TResult<List<Acao>> participarEmAcao(Pessoa pessoa, Long acaoId){
		try{
			Pessoa pess = pessBO.find(pessoa);
			bo.participarEmAcao(pess, acaoId);
			List<Acao> lst = acaoBO.listAcoesDoDiaAnteriorEmDiante();
			return new TResult<>(EnumResult.SUCESS, "", lst);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}
	
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public TResult<List<Acao>> sairDaAcao(Pessoa pessoa, Long acaoId){
		try{
			Pessoa pess = pessBO.find(pessoa);
			bo.sairDaAcao(pess, acaoId);
			List<Acao> lst = acaoBO.listAcoesDoDiaAnteriorEmDiante();
			return new TResult<>(EnumResult.SUCESS, "", lst);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}
	
	
	
	
	
	
	
	
}
