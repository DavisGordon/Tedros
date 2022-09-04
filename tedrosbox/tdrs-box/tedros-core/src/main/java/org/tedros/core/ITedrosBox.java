/**
 * 
 */
package org.tedros.core;

import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * @author Davis Gordon
 *
 */
public interface ITedrosBox {

	Node buildLogin();
	
	void buildApplicationMenu();
	
	void buildSettingsPane();
	
	Stage getStage();
	
	void clearPageHistory();
    
    void logout();
	
}
