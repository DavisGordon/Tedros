/**
 * 
 */
package org.somos.module.cozinha;

import org.somos.module.cozinha.model.CozinhaModelView;

import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id="SOMOS_COZINHA_MODULE", appName = "#{somos.name}", 
moduleName = "Administrativo", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class CozinhaModule extends TModule {

	/* (non-Javadoc)
	 * @see com.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		TDynaView<CozinhaModelView> view = new TDynaView<>(this, CozinhaModelView.class);
		tShowView(view);
	}

}
