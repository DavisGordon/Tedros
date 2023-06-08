/**
 * 
 */
package org.tedros.fx.control;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Davis Gordon
 *
 */
public class TRequiredValidation {

	public static boolean isRequired(Object o) {
		if(o == null)
			return true;
		else if(o instanceof String )
			return StringUtils.isBlank((String)o);
		else if(o instanceof Boolean)
			return !(Boolean) o;
		else if(o instanceof Collection) 
			return ((Collection)o).isEmpty();
		else if(o instanceof TItem) {
			return ((TItem)o).getValue()==null;
		}
		
		return false;
	}
	
}
