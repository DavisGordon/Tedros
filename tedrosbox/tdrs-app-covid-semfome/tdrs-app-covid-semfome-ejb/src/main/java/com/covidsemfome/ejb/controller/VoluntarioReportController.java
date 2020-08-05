package com.covidsemfome.ejb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.covidsemfome.ejb.service.AcaoService;
import com.covidsemfome.model.Acao;
import com.covidsemfome.model.Voluntario;
import com.covidsemfome.report.model.VoluntarioItemModel;
import com.covidsemfome.report.model.VoluntarioReportModel;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;

@Stateless(name="IVoluntarioReportController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class VoluntarioReportController implements IVoluntarioReportController {

	@EJB
	private AcaoService serv;
	
	public VoluntarioReportController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public TResult<VoluntarioReportModel> process(VoluntarioReportModel m) {
		try{
			List<Acao> lst = serv.pesquisar(m.getIds(), m.getTitulo(), m.getDataInicio(), m.getDataFim(), m.getStatus());
			if(lst!=null){
				List<VoluntarioItemModel> itens = new ArrayList<>();
				for(Acao a : lst){
					for(Voluntario v : a.getVoluntarios())
						itens.add(new VoluntarioItemModel(v));
				}
				m.setResult(itens);
			}
			return new TResult<>(EnumResult.SUCESS, m);
		}catch(Exception e){
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}

}
