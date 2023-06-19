/**
 * 
 */
package org.tedros.fx.presenter.page.converter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.tedros.core.TLanguage;
import org.tedros.fx.TFxKey;
import org.tedros.fx.converter.TConverter;

/**
 * @author Davis Gordon
 *
 */
public class TStringToLong extends TConverter<String, Long> {

	/* (non-Javadoc)
	 * @see org.tedros.fx.form.TConverter#getOut()
	 */
	@Override
	public Long getOut() {
		
		if(StringUtils.isNotBlank(getIn())) {
			if(NumberUtils.isParsable(getIn()))
				return Long.valueOf(getIn());
			throw new IllegalArgumentException(TLanguage.getInstance()
					.getString(TFxKey.VALIDATOR_ONLY_REAL_NUMBER));
		}
		return null;
	}

}
