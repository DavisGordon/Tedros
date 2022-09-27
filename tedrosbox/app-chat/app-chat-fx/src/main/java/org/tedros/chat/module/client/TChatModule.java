/**
 * 
 */
package org.tedros.chat.module.client;

import org.tedros.chat.module.client.model.TChatMV;
import org.tedros.core.TModule;
import org.tedros.fx.presenter.dynamic.view.TDynaView;

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
