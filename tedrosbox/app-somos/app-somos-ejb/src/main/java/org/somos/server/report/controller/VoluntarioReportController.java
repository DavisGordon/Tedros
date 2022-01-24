package org.somos.server.report.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.somos.ejb.controller.IVoluntarioReportController;
import org.somos.report.model.VoluntarioReportModel;
import org.somos.server.acao.service.VoluntarioService;

import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TRemoteSecurity;

@TRemoteSecurity
@Stateless(name="IVoluntarioReportController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class VoluntarioReportController implements IVoluntarioReportController, ITSecurity {

	@EJB
	private VoluntarioService serv;
	
	@EJB
	private ITSecurityController security;
	
	public VoluntarioReportController() {
	}

	@Override
	public TResult<VoluntarioReportModel> process(TAccessToken token, VoluntarioReportModel m) {
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
