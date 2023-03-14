/**
 * 
 */
package org.tedros.core.controller;

import javax.ejb.Remote;

import org.tedros.core.ai.model.TAiCreateImage;
import org.tedros.core.ai.model.image.TCreateImageRequest;
import org.tedros.core.ai.model.image.TImageResult;
import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.security.TAccessToken;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface TAiCreateImageController extends ITSecureEjbController<TAiCreateImage> {

	static final String JNDI_NAME = "TAiCreateImageControllerRemote";
	
	TResult<TImageResult> createImage(TAccessToken token, TCreateImageRequest request);
}
