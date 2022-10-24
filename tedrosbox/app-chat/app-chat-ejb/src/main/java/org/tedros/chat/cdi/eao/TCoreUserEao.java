/**
 * 
 */
package org.tedros.chat.cdi.eao;

import javax.enterprise.context.RequestScoped;

import org.tedros.core.security.model.TUser;
import org.tedros.server.cdi.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class TCoreUserEao extends TGenericEAO<TUser> {
	

}
