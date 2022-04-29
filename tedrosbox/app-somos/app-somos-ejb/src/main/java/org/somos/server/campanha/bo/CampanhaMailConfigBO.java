/**
 * 
 */
package org.somos.server.campanha.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.somos.model.AjudaCampanha;
import org.somos.model.CampanhaMailConfig;
import org.somos.server.campanha.eao.CampanhaMailConfigEAO;
import org.somos.server.producer.Item;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.util.TDateUtil;

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
	private CampanhaMailConfigEAO eao;
	
	@Override
	public ITGenericEAO<CampanhaMailConfig> getEao() {
		return eao;
	}
	
	public String prepareContent(CampanhaMailConfig cmc, AjudaCampanha ac) {
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
	
}
