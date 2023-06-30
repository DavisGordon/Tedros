/**
 * 
 */
package org.tedros.tools.ai.pane;

import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.tools.ai.model.TerosMV;

import javafx.scene.layout.StackPane;

/**
 * @author Davis Gordon
 *
 */
public class TerosPane extends StackPane {

	/**
	 * 
	 */
	public TerosPane() {
		TDynaView<TerosMV> v = new TDynaView<>(TerosMV.class);
    	v.tLoad();
    	v.setMinHeight(400);
		super.getChildren().add(v);
				
		
	}

	

}
