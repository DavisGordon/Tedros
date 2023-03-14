/**
 * 
 */
package org.tedros.tools.module.ai;

import org.tedros.core.ai.model.image.TImageSize;
import org.tedros.fx.form.TConverter;

/**
 * @author Davis Gordon
 *
 */
public class TImageSizeConverter extends TConverter<String, TImageSize> {

	@Override
	public TImageSize getOut() {
		return TImageSize.value(getIn());
	}

}
