/**
 * 
 */
package org.tedros.fx.control;

/**
 * @author Davis Gordon
 *
 */
public class TListView<T> extends TRequiredListView<T> {

	/**
	 * 
	 */
	public TListView() {
		super();
		super.tRequiredNodeProperty().setValue(this);
	}

}
