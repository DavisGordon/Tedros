/**
 * 
 */
package org.tedros.core.ejb.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.core.ai.model.TAiChatCompletion;
import org.tedros.core.ai.model.completion.chat.TChatRequest;
import org.tedros.core.ai.model.completion.chat.TChatResult;
import org.tedros.core.cdi.bo.TCoreBO;
import org.tedros.core.cdi.bo.TOpenAiBO;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;
import org.tedros.server.exception.TBusinessException;

/**
 * @author Davis Gordon
 *
 */

@LocalBean
@Stateless(name="TAiChatCompletionService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TAiChatCompletionService extends TEjbService<TAiChatCompletion> {

	@Inject
	private TOpenAiBO aiBo;
	
	@Inject
	private TCoreBO<TAiChatCompletion> bo;
	
	public TChatResult chat(TChatRequest req) throws TBusinessException {
		return aiBo.chat(req);
	}

	@Override
	public ITGenericBO<TAiChatCompletion> getBussinesObject() {
		return bo;
	}

}
