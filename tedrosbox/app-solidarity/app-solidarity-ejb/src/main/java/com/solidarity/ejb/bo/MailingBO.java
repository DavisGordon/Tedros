/**
 * 
 */
package com.solidarity.ejb.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.solidarity.ejb.eao.MailingEAO;
import com.solidarity.model.Mailing;
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
