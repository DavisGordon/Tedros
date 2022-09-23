/**
 * 
 */
package org.tedros.core.cdi.eao;

import javax.enterprise.context.RequestScoped;

import org.tedros.core.message.model.TMessage;
import org.tedros.server.cdi.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class TMessageEao extends TGenericEAO<TMessage> {
	

}
