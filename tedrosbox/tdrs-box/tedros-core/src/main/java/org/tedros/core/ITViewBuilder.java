/**
 * 
 */
package org.tedros.core;

import org.tedros.api.presenter.view.ITView;

/**
 * @author Davis Gordon
 *
 */
public interface ITViewBuilder {

	@SuppressWarnings("rawtypes")
	ITView build(TModule module);
}
