/**
 * 
 */
package org.tedros.server.model;

import java.util.List;

/**
 * @author Davis Gordon
 *
 */
public interface ITListModel<T> extends ITModel {

	List<T> getValues();
	
	void setValues(List<T> values);
}
