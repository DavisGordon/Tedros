/**
 * 
 */
package org.tedros.tools.module.notify.converter;

import org.tedros.core.notify.model.TAction;
import org.tedros.fx.converter.TConverter;

/**
 * @author Davis Gordon
 *
 */
public class ActionConverter extends TConverter<String, TAction> {

	@Override
	public TAction getOut() {
		for(TAction e : TAction.values())
			if(super.getIn()!=null && e.getValue().equals(super.getIn()))
				return e;
		return null;
	}

}
