/**
 * 
 */
package org.tedros.test.ai.model;

import org.tedros.server.model.TJsonModel;
import org.tedros.stock.entity.EntryType;

/**
 * @author Davis Gordon
 *
 */
public class EntryTypeJson extends TJsonModel<EntryType> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7922013152212122265L;

	@Override
	public Class<EntryType> getModelType() {
		return EntryType.class;
	}

}
