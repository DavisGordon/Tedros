/**
 * 
 */
package com.tedros.fxapi.control;

import javafx.beans.Observable;

/**
 * @author Davis Gordon
 *
 */
public interface ITTriggeredable {
	
	<T extends Observable> T tValueProperty();

}
