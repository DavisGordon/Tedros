/**
 * 
 */
package org.tedros.tools.module.ai.process;

import java.util.List;

import javax.naming.NamingException;

import org.tedros.core.ai.model.TAiCreateImage;
import org.tedros.core.ai.model.image.TCreateImageRequest;
import org.tedros.core.ai.model.image.TImageResult;
import org.tedros.core.ai.util.TAiModelUtil;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.controller.TAiCreateImageController;
import org.tedros.core.service.remote.ServiceLocator;
import org.tedros.fx.process.TEntityProcess;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.TAccessToken;

/**
 * @author Davis Gordon
 *
 */
public class TAiCreateImageProcess extends TEntityProcess<TAiCreateImage> {

	private TAiCreateImage e;
	
	public TAiCreateImageProcess() {
		super(TAiCreateImage.class, TAiCreateImageController.JNDI_NAME);
	}
	
	public void createImage(TAiCreateImage e) {
		this.e = e;
	}
	
	@Override
	public boolean runBefore(List<TResult<TAiCreateImage>> resultList) {
		
		if(e==null)
			return true;
		ServiceLocator loc = ServiceLocator.getInstance();
		
		try {

			TCreateImageRequest req = TAiModelUtil.convert(e);
			TAccessToken token = TedrosContext.getLoggedUser().getAccessToken();
			TAiCreateImageController serv = loc.lookup(TAiCreateImageController.JNDI_NAME);
			TResult<TImageResult> res = serv.createImage(token, req);
			if(res.getState().equals(TState.SUCCESS)) {
				TAiCreateImage i = new TAiCreateImage();
				TAiModelUtil.parse(res.getValue(), i);
				resultList.add(new TResult<>(TState.SUCCESS, i));
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
