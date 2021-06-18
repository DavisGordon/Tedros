/**
 * 
 */
package com.tedros.core;

import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * @author Davis Gordon
 *
 */
public interface ITedrosBox {

	public Node buildLogin();
	
	public void buildApplicationMenu();
	
	public void buildSettingsPane();
	
	public Stage getStage();
	
	public void showApps();
    
    public void hideApps();
	
}
