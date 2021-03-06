package com.solidarity.ejb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.solidarity.ejb.controller.IDoacaoReportController;
import com.solidarity.ejb.service.DoacaoService;
import com.solidarity.model.Doacao;
import com.solidarity.report.model.DoacaoItemModel;
import com.solidarity.report.model.DoacaoReportModel;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;

@Stateless(name="IDoacaoReportController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class DoacaoReportController implements IDoacaoReportController {

	@EJB
	private DoacaoService serv;
	
	public DoacaoReportController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public TResult<DoacaoReportModel> process(DoacaoReportModel m) {
		try{
			List<Doacao> lst = serv.pesquisar(m.getNome(), m.getDataInicio(), m.getDataFim(), m.getAcaoId(), m.getTiposAjuda());
			if(lst!=null){
				List<DoacaoItemModel> itens = new ArrayList<>();
				for(Doacao d : lst){
					Long qtd = d.getQuantidade()!=null ? Long.valueOf(d.getQuantidade()) : null;
					String acao = d.getAcao()!=null?d.getAcao().getTitulo(): null;
					itens.add(new DoacaoItemModel(d.getData(), d.getTipoAjuda().getDescricao(), qtd , d.getValor(), d.getPessoa().getNome(),acao));
				}
				m.setResult(itens);
			}
			return new TResult<>(EnumResult.SUCESS, m);
		}catch(Exception e){
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}

}
