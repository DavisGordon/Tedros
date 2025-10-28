/**
 * 
 */
package org.tedros.tools.module.ai.process;

import java.util.List;

import javax.naming.NamingException;

import org.tedros.core.ai.model.TAiCompletion;
import org.tedros.core.ai.model.completion.TCompletionRequest;
import org.tedros.core.ai.model.completion.TCompletionResult;
import org.tedros.core.ai.util.TAiModelUtil;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.controller.TAiCompletionController;
import org.tedros.core.service.remote.ServiceLocator;
import org.tedros.fx.process.TEntityProcess;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.TAccessToken;

/**
 * @author Davis Gordon
 *
 */
public class TAiCompletionProcess extends TEntityProcess<TAiCompletion> {

	private TAiCompletion e;
	
	public TAiCompletionProcess() {
		super(TAiCompletion.class, TAiCompletionController.JNDI_NAME);
	}
	
	public void completion(TAiCompletion e) {
		this.e = e;
	}
	
	@Override
	public boolean runBefore(List<TResult<TAiCompletion>> resultList) {
		
		if(e==null)
			return true;
		ServiceLocator loc = ServiceLocator.getInstance();
		
		try {

			TCompletionRequest req = TAiModelUtil.convert(e);
			TAccessToken token = TedrosContext.getLoggedUser().getAccessToken();
			TAiCompletionController serv = loc.lookup(TAiCompletionController.JNDI_NAME);
			TResult<TCompletionResult> res = serv.completion(token, req);
			if(res.getState().equals(TState.SUCCESS)) {
				TAiModelUtil.parse(res.getValue(), e);
				resultList.add(new TResult<>(TState.SUCCESS, e));
			}else {
				resultList.add(new TResult<>(res.getState(), res.getMessage()));
			}
		
			return false;
		
		} catch (NamingException e1) {
			throw new RuntimeException(e1);
		}finally {
			loc.close();
		}
		
	}

}
