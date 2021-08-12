package com.covidsemfome.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.covidsemfome.ejb.service.EntradaService;
import com.covidsemfome.ejb.service.SaidaService;
import com.covidsemfome.report.model.EstocavelReportModel;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;

@Stateless(name="IEstocavelReportController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class EstocavelReportController implements IEstocavelReportController {

	@EJB
	private EntradaService eServ;
	
	@EJB
	private SaidaService sServ;
	
	public EstocavelReportController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public TResult<EstocavelReportModel> process(EstocavelReportModel m) {
		try{
			m = m.getOrigem().equals("Entrada") 
					? eServ.pesquisar(m)
							: sServ.pesquisar(m) ;
			return new TResult<>(EnumResult.SUCESS, m);
		}catch(Exception e){
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}

}
