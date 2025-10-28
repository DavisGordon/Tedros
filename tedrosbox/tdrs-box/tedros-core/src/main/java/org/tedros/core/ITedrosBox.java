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
	
	void showLogo(String logoFileName, String brand, Double brandLeftMargin);
	
	void showDefaultLogo();
	
	Stage getStage();
	
	void clearPageHistory();
    
    void logout();
	
}
