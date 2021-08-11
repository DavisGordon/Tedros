package com.covidsemfome.ejb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.covidsemfome.ejb.service.ProdutoService;
import com.covidsemfome.model.Produto;
import com.covidsemfome.report.model.ProdutoItemModel;
import com.covidsemfome.report.model.ProdutoReportModel;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;

@Stateless(name="IProdutoReportController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ProdutoReportController implements IProdutoReportController {

	@EJB
	private ProdutoService serv;
	
	public ProdutoReportController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public TResult<ProdutoReportModel> process(ProdutoReportModel m) {
		try{
			List<Produto> lst = serv.pesquisar(m.getCodigo(), m.getNome(), m.getMarca(), m.getMedida(),m.getUnidadeMedida());
			if(lst!=null){
				List<ProdutoItemModel> itens = new ArrayList<>();
				for(Produto a : lst){
					itens.add(new ProdutoItemModel(a));
				}
				m.setResult(itens);
			}
			return new TResult<>(EnumResult.SUCESS, m);
		}catch(Exception e){
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}

}
