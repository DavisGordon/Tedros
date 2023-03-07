/**
 * 
 */
package org.tedros.tools.module.ai;

import org.tedros.core.TModule;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.tools.module.ai.model.CompletionMV;

/**
 * @author Davis Gordon
 *
 */
public class TAiModule extends TModule {

	
	@Override
	public void tStart() {
		tShowView(new TDynaView<CompletionMV>(this, CompletionMV.class));
	}
}
