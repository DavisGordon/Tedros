/**
 * 
 */
package org.tedros.test.ai.model;

import org.tedros.location.model.Country;
import org.tedros.server.model.TJsonModel;

/**
 * @author Davis Gordon
 *
 */
public class CountryJson extends TJsonModel<Country> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8991788634063044141L;

	@Override
	public Class<Country> getModelType() {
		return Country.class;
	}

}
