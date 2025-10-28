/**
 * 
 */
package org.tedros.test.ai.model;

import org.tedros.core.security.model.TProfile;
import org.tedros.server.model.TJsonModel;

/**
 * @author Davis Gordon
 *
 */
public class ProfileJson extends TJsonModel<TProfile> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8991788634063044141L;

	@Override
	public Class<TProfile> getModelType() {
		return TProfile.class;
	}

}
