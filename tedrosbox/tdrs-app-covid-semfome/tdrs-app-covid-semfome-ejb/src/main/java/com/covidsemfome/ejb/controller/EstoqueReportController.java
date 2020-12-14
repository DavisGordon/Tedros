package com.covidsemfome.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.covidsemfome.ejb.service.EstoqueService;
import com.covidsemfome.report.model.EstoqueReportModel;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;

@Stateless(name="IEstoqueReportController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class EstoqueReportController implements IEstoqueReportController {

	@EJB
	private EstoqueService serv;
	
	public EstoqueReportController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public TResult<EstoqueReportModel> process(EstoqueReportModel m) {
		try{
			m = serv.pesquisar(m);
			return new TResult<>(EnumResult.SUCESS, m);
		}catch(Exception e){
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}

}
