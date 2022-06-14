/**
 * 
 */
package com.tedros.tools.module.notify.converter;

import com.tedros.core.notify.model.TAction;
import com.tedros.fxapi.form.TConverter;

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
