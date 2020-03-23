/**
 * 
 */
package com.tedros.fxapi.builder;

import javafx.scene.control.TableView.ResizeFeatures;
import javafx.util.Callback;

/**
 * @author Davis Gordon
 *
 */
public interface ITColumnResizePolicyCallBackBuilder<T> extends ITGenericBuilder<Callback<ResizeFeatures<T>,Boolean>> {

}
