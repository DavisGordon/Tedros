/**
 * 
 */
package org.tedros.core.controller;

import javax.ejb.Remote;

import org.tedros.core.ai.model.TCompletionRequest;
import org.tedros.core.ai.model.TCompletionResult;
import org.tedros.core.ai.model.TCreateImageRequest;
import org.tedros.core.ai.model.TImageResult;
import org.tedros.server.controller.ITBaseController;
import org.tedros.server.result.TResult;
import org.tedros.server.security.TAccessToken;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface TAiController extends ITBaseController {

	
	TResult<TCompletionResult> completion(TAccessToken token, TCompletionRequest request);
	

	TResult<TImageResult> createImage(TAccessToken token, TCreateImageRequest request);
}
