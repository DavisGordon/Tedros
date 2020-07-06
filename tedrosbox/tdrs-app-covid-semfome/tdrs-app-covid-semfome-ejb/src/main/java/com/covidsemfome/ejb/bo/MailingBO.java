/**
 * 
 */
package com.covidsemfome.ejb.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.covidsemfome.ejb.eao.MailingEAO;
import com.covidsemfome.model.Mailing;
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
