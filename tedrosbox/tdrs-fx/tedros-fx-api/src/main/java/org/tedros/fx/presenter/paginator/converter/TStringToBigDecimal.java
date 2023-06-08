/**
 * 
 */
package org.tedros.fx.presenter.paginator.converter;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.tedros.core.TLanguage;
import org.tedros.fx.TFxKey;
import org.tedros.fx.form.TConverter;

/**
 * @author Davis Gordon
 *
 */
public class TStringToBigDecimal extends TConverter<String, BigDecimal> {

	/* (non-Javadoc)
	 * @see org.tedros.fx.form.TConverter#getOut()
	 */
	@Override
	public BigDecimal getOut() {
		
		if(StringUtils.isNotBlank(getIn())) {
			if(NumberUtils.isParsable(getIn()))
				return new BigDecimal(getIn());
			throw new IllegalArgumentException(TLanguage.getInstance()
					.getString(TFxKey.VALIDATOR_ONLY_DECIMAL_NUMBER));
		}
		return null;
	}

}
