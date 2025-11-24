package org.tedros.core.control;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.scene.layout.Pane;

public interface ITProgressIndicator {
	
	void initialize(Pane pane);

	/**
	 * @param pane
	 */
	void setMargin(double val);

	void bind(BooleanBinding bb);

	void bind(ReadOnlyBooleanProperty bb);

	void removeBind();

	void setSmallLogo();

	void setMediumLogo();

	void setLogo();

}