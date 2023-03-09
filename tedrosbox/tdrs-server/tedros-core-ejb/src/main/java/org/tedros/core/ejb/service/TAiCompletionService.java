/**
 * 
 */
package org.tedros.core.ejb.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.core.ai.model.TAiCompletion;
import org.tedros.core.ai.model.TCompletionRequest;
import org.tedros.core.ai.model.TCompletionResult;
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
@Stateless(name="TAiCompletionService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TAiCompletionService extends TEjbService<TAiCompletion> {

	@Inject
	private TOpenAiBO aiBo;
	
	@Inject
	private TCoreBO<TAiCompletion> bo;
	
	/*
	public TImageResult createImage(TCreateImageRequest req) throws TBusinessException {
		return aiBo.createImage(req);
	}*/
	
	public TCompletionResult completion(TCompletionRequest req) throws TBusinessException {
		return aiBo.completion(req);
	}

	@Override
	public ITGenericBO<TAiCompletion> getBussinesObject() {
		return bo;
	}

}
