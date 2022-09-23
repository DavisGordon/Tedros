/**
 * 
 */
package org.tedros.tools.module.message;

import org.tedros.core.TModule;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.tools.module.message.model.TChatMV;

/**
 * @author Davis Gordon
 *
 */
public class TChatModule extends TModule {

	@Override
	public void tStart() {
		super.tShowView(new TDynaView<>(this, TChatMV.class));
	}
}
