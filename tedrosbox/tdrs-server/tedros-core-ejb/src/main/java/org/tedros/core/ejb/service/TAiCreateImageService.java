/**
 * 
 */
package org.tedros.core.ejb.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import org.tedros.core.ai.model.TAiCreateImage;
import org.tedros.core.ai.model.image.TCreateImageRequest;
import org.tedros.core.ai.model.image.TImageResult;
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
@Stateless(name="TAiCreateImageService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TAiCreateImageService extends TEjbService<TAiCreateImage> {

	@Inject
	private TOpenAiBO aiBo;
	
	@Inject
	private TCoreBO<TAiCreateImage> bo;
	
	
	public TImageResult createImage(TCreateImageRequest req) throws TBusinessException {
		return aiBo.createImage(req);
	}
	
	@Override
	public ITGenericBO<TAiCreateImage> getBussinesObject() {
		return bo;
	}

}
