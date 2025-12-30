package org.tedros.core.ux;

import javafx.scene.Node;
import javafx.stage.Stage;

public interface ITWindow {
	
	public static final String SCENE_ID = "OPENED_TWINDOW_SCENE";

	Node getView();

	Stage getStage();

	double getXStage();

	double getYStage();

	void close();

	void reloadStyle();

}