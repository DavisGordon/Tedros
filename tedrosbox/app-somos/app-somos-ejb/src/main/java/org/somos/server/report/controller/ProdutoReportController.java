package org.somos.server.report.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.somos.ejb.controller.IProdutoReportController;
import org.somos.model.Produto;
import org.somos.report.model.ProdutoItemModel;
import org.somos.report.model.ProdutoReportModel;
import org.somos.server.produto.service.ProdutoService;

import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TRemoteSecurity;

@TRemoteSecurity
@Stateless(name="IProdutoReportController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ProdutoReportController implements IProdutoReportController, ITSecurity {

	@EJB
	private ProdutoService serv;
	
	@EJB
	private ITSecurityController security;
	
	public ProdutoReportController() {
	}

	@Override
	public TResult<ProdutoReportModel> process(TAccessToken token, ProdutoReportModel m) {
		try{
			List<Produto> lst = serv.pesquisar(m.getCodigo(), m.getNome(), m.getMarca(), m.getMedida(),
					m.getUnidadeMedida(), m.getOrderBy(), m.getOrderType());
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

	@Override
	public ITSecurityController getSecurityController() {
		return security;
	}

}
