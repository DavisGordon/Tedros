/**
 * 
 */
package org.somos.server.acao.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.somos.model.Mailing;
import org.somos.server.acao.eao.MailingEAO;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class MailingBO extends TGenericBO<Mailing> {
	
	@Inject
	private MailingEAO eao;

	@Override
	public ITGenericEAO<Mailing> getEao() {
		return eao;
	}

}
