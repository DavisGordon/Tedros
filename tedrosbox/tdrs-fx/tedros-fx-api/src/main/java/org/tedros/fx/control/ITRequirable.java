/**
 * 
 */
package org.tedros.fx.control;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;

/**
 * @author Davis Gordon
 *
 */
public interface ITRequirable extends ITField {
	
	SimpleObjectProperty<Node> tRequiredNodeProperty();

}
