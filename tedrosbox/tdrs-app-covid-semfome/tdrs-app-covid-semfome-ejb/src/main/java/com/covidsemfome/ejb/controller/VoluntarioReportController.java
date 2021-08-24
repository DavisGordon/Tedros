package com.covidsemfome.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.covidsemfome.ejb.service.VoluntarioService;
import com.covidsemfome.report.model.VoluntarioReportModel;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;

@Stateless(name="IVoluntarioReportController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class VoluntarioReportController implements IVoluntarioReportController {

	@EJB
	private VoluntarioService serv;
	
	public VoluntarioReportController() {
	}

	@Override
	public TResult<VoluntarioReportModel> process(VoluntarioReportModel m) {
		try{
			m = serv.pesquisar(m);
			return new TResult<>(EnumResult.SUCESS, m);
		}catch(Exception e){
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}

}
