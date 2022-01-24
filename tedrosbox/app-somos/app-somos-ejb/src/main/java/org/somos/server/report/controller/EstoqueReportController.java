package org.somos.server.report.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.somos.ejb.controller.IEstoqueReportController;
import org.somos.report.model.EstoqueReportModel;
import org.somos.server.estoque.service.EstoqueService;

import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TRemoteSecurity;

@TRemoteSecurity
@Stateless(name="IEstoqueReportController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class EstoqueReportController implements IEstoqueReportController, ITSecurity {

	@EJB
	private EstoqueService serv;
	
	@EJB
	private ITSecurityController security;
	
	public EstoqueReportController() {
	}

	@Override
	public TResult<EstoqueReportModel> process(TAccessToken token, EstoqueReportModel m) {
		try{
			m = serv.pesquisar(m);
			return new TResult<>(EnumResult.SUCESS, m);
		}catch(Exception e){
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}

	@Override
	public ITSecurityController getSecurityController() {
		return security;
	}

}
