/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 18/08/2014
 */
package com.tedros.tools.logged.user;

import com.tedros.core.context.TedrosContext;
import com.tedros.fxapi.control.trigger.TTrigger;
import com.tedros.fxapi.form.TFieldBox;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class CollapseMenuTrigger extends TTrigger<Boolean> {

	
	
	public CollapseMenuTrigger(TFieldBox source, TFieldBox target) {
		super(source, target);
	}

	@Override
	public void run(Boolean value) {
		//TedrosContext.setCollapseMenu(value);
	}
	
}
