/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 18/08/2014
 */
package org.tedros.tools.logged.user;

import org.tedros.fx.control.trigger.TTrigger;
import org.tedros.fx.form.TFieldBox;

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
