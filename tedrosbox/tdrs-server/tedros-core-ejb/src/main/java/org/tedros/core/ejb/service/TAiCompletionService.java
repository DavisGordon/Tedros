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
import org.tedros.core.ai.model.TCreateImageRequest;
import org.tedros.core.ai.model.TImageResult;
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
	
	@Override
	public TAiCompletion save(TAiCompletion e) throws Exception {
		
		TCompletionRequest req = new TCompletionRequest();
		req.setModel(e.getModel());
		req.setMaxTokens(e.getMaxTokens());
		req.setPrompt(e.getPrompt());
		req.setTemperature(e.getTemperature());
		req.setUser(e.getUser().getName());
		
		TCompletionResult r = completion(req);
		
		if(!r.isSuccess()) {
			e.setResponse("Fail see events for more details.");
			e.addEvent("Fail: "+r.getLog());
		}else {
			StringBuilder sb = new StringBuilder();
			r.getChoices().forEach(c->{
				sb.append(c.getText());
				sb.append("/n");
			});
			e.setResponse(sb.toString());
			e.addEvent("Success");
		}
		
		return super.save(e);
	}
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
