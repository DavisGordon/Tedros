/**
 * 
 */
package com.tedros.fxapi.builder;

import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 * @author Davis Gordon
 *
 */
public interface ITRowFactoryCallBackBuilder<S> extends ITGenericBuilder<Callback<TableView<S>,TableRow<S>>> {

}
