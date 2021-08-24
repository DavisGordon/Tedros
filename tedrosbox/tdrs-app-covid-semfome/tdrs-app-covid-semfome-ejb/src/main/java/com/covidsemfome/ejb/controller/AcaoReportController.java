package com.covidsemfome.ejb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.covidsemfome.ejb.service.AcaoService;
import com.covidsemfome.model.Acao;
import com.covidsemfome.report.model.AcaoItemModel;
import com.covidsemfome.report.model.AcaoReportModel;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;

@Stateless(name="IAcaoReportController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class AcaoReportController implements IAcaoReportController {

	@EJB
	private AcaoService serv;
	
	public AcaoReportController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public TResult<AcaoReportModel> process(AcaoReportModel m) {
		try{
			List<Acao> lst = serv.pesquisar(m.getIds(), m.getTitulo(), m.getDataInicio(), m.getDataFim(), 
					m.getStatus(), m.getOrderBy(), m.getOrderType());
			if(lst!=null){
				List<AcaoItemModel> itens = new ArrayList<>();
				for(Acao a : lst){
					itens.add(new AcaoItemModel(a));
				}
				m.setResult(itens);
			}
			return new TResult<>(EnumResult.SUCESS, m);
		}catch(Exception e){
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}

}
