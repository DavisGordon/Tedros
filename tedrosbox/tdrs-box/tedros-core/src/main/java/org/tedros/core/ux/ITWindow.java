package org.tedros.core.ux;

import javafx.scene.Node;
import javafx.stage.Stage;

public interface ITWindow {

	Node getView();

	Stage getStage();

	double getXStage();

	double getYStage();

	void close();

	void reloadStyle();

}