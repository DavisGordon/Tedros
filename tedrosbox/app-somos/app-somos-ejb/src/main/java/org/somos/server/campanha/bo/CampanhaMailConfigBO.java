/**
 * 
 */
package org.somos.server.campanha.bo;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.somos.model.AjudaCampanha;
import org.somos.model.CampanhaMailConfig;
import org.somos.server.acao.bo.EmailBO;
import org.somos.server.campanha.eao.CampanhaMailConfigEAO;
import org.somos.server.exception.EmailBusinessException;
import org.somos.server.producer.Item;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.util.TDateUtil;
import com.tedros.util.TSentEmailException;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class CampanhaMailConfigBO extends TGenericBO<CampanhaMailConfig> {

	@Inject
	@Named("host")
	private Item<String> host;
	
	@Inject
	private EmailBO emailBo;
	
	@Inject
	private AjudaCampanhaBO acBo;
	
	@Inject
	private CampanhaMailConfigEAO eao;
	
	@Override
	public ITGenericEAO<CampanhaMailConfig> getEao() {
		return eao;
	}
	
	public List<AjudaCampanha> processarMailing() throws Exception{
		CampanhaMailConfig ex = new CampanhaMailConfig();
		ex.setStatus("ATIVADO");
		List<CampanhaMailConfig> lst = findAll(ex);
		List<AjudaCampanha> enviados = new ArrayList<>();
		if(lst!=null && !lst.isEmpty()) {
			StringBuilder  error = new StringBuilder("");
			for(CampanhaMailConfig cmc : lst) {
				String fail;
				try {
					List<AjudaCampanha> processar = acBo.listarParaProcessamento(cmc.getCampanha(), cmc.getFormaAjuda());
					fail = enviarMailing(enviados, cmc, processar);
					if(StringUtils.isNotBlank(fail))
						error.append(fail);
				}catch(Exception e) {
					error.append("<hr>Erro tentar buscar as ajudas de campanha do tipo não processadas ");
					error.append("<br>"+cmc.toString());
					error.append("<br>Msg: "+e.getMessage());
					error.append("<br>Cause: "+e.getCause().getMessage());
				}
			}
			if(!enviados.isEmpty()) {
				try {
					emailBo.enviarEmailAjudaCampanhaRealizado(enviados);
				} catch (EmailBusinessException | TSentEmailException e) {
					e.printStackTrace();
				}
			}
			String x = error.toString();
			if(StringUtils.isNotBlank(x)) {
				try {
					emailBo.enviarEmailErro(x);
				} catch (EmailBusinessException | TSentEmailException e) {
					e.printStackTrace();
				}
			}
					
		}
		return enviados;
	}
	
	private String prepareContent(CampanhaMailConfig cmc, AjudaCampanha ac) {
		String c = cmc.getConteudo();
		c = c.replaceAll("#NOME#", ac.getAssociado().getPessoa().getNome());
		c = c.replaceAll("#TITULOCAMPANHA#", ac.getCampanha().getTitulo());
		if(ac.getFormaAjuda()!=null)
			c = c.replaceAll("#FORMAAJUDA#", ac.getFormaAjuda().getTipo());
		if(ac.getValor()!=null)
			c = c.replaceAll("#VALOR#", ac.getValor());
		if(ac.getCampanha().getDataFim()!=null)
			c = c.replaceAll("#DATAFIM#", TDateUtil.getFormatedDate(ac.getCampanha().getDataFim(), TDateUtil.DDMMYYYY));
		c = c.replaceAll("#LINKCAMPANHA#", "<a href='"+host.getValue()+"campanha.html' >Campanha</a>");
		c = c.replaceAll("#LINKPAINEL#","<a href='"+host.getValue()+"painelv.html'>Meu Painel</a>");
		c = c.replaceAll("#LINKSITE#", "<a href='"+host.getValue()+"index.html'>www.somossocial.org.br</a>");
		
		return c;
	}

	private String enviarMailing(List<AjudaCampanha> enviados, CampanhaMailConfig cmc, List<AjudaCampanha> novosLst) {
		StringBuilder  error = new StringBuilder("");
		novosLst.forEach(ac->{
			String content = prepareContent(cmc, ac);
			try {
				emailBo.enviarMailing(false, ac.getAssociado().getPessoa().getEmail(), cmc.getTitulo(), content);
				enviados.add(ac);
			} catch (TSentEmailException | EmailBusinessException e) {
				e.printStackTrace();
				error.append("<hr>"+ac.getAssociado().getPessoa().getEmail()+", "+ cmc.getTitulo()+"<br> "+ content);
			}
		});
		return error.toString();
	}
}
