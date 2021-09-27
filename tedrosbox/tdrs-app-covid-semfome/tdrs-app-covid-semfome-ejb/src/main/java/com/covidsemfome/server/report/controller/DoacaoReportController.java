package com.covidsemfome.server.report.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.covidsemfome.ejb.controller.IDoacaoReportController;
import com.covidsemfome.model.Doacao;
import com.covidsemfome.report.model.DoacaoItemModel;
import com.covidsemfome.report.model.DoacaoReportModel;
import com.covidsemfome.server.acao.service.DoacaoService;
import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TRemoteSecurity;

@TRemoteSecurity
@Stateless(name="IDoacaoReportController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class DoacaoReportController implements IDoacaoReportController, ITSecurity {

	@EJB
	private DoacaoService serv;
	
	@EJB
	private ITSecurityController security;
	
	public DoacaoReportController() {
	}

	@Override
	public TResult<DoacaoReportModel> process(TAccessToken token, DoacaoReportModel m) {
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

	@Override
	public ITSecurityController getSecurityController() {
		return security;
	}

}
