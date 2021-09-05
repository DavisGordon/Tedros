package com.covidsemfome.ejb.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.covidsemfome.ejb.service.PessoaService;
import com.covidsemfome.ejb.service.VoluntarioService;
import com.covidsemfome.model.Acao;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.Voluntario;
import com.covidsemfome.report.model.AcaoItemModel;
import com.covidsemfome.report.model.PessoaModel;
import com.covidsemfome.report.model.PessoaReportModel;
import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TRemoteSecurity;

@TRemoteSecurity
@Stateless(name="IPessoaReportController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class PessoaReportController implements IPessoaReportController, ITSecurity {

	@EJB
	private PessoaService serv;
	
	@EJB
	private VoluntarioService vServ;
	
	@EJB
	private ITSecurityController security;
	
	public PessoaReportController() {
	}

	@Override
	public TResult<PessoaReportModel> process(TAccessToken token, PessoaReportModel m) {
		try{
			List<Pessoa> lst = serv.pesquisar(m.getNome(), m.getTipoVoluntario(), m.getStatusVoluntario(), 
					m.getDataInicio(), m.getDataFim(), m.getOrderBy(), m.getOrderType());

			if(lst!=null){
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				List<PessoaModel> itens = new ArrayList<>();
				for(Pessoa a : lst){
					PessoaModel pm = new PessoaModel(a);
					if(m.isListarAcoes()) {
						List<AcaoItemModel> l = new ArrayList<>();
						Voluntario vEx = new Voluntario();
						vEx.setPessoa(a);
						List<Voluntario> v = vServ.findAll(vEx);
						if(v!=null) {
							for (Voluntario b : v) {
								Calendar c1 = Calendar.getInstance();
								Acao x = b.getAcao();
								c1.setTime(x.getData());
								if(c.before(c1)) {
									l.add(new AcaoItemModel(b.getAcao()));
								}
							}
							pm.setAcoes(l);
						}
					}
					itens.add(pm);
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
