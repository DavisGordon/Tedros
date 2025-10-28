/**
 * 
 */
package org.tedros.fx.builder;

import java.util.List;

import org.tedros.core.TLanguage;
import org.tedros.fx.TFxKey;

import javafx.beans.binding.Bindings;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 * @author Davis Gordon
 *
 */
public abstract class TContextMenuRowFactoryCallBackBuilder<S> extends TRowFactoryCallBackBuilder<S> {

	/**
	 * 
	 */
	public TContextMenuRowFactoryCallBackBuilder() {
		
	}
	
	abstract List<MenuItem> getMenuItems(TableView<S> table, TableRow<S> row);

	/* (non-Javadoc)
	 * @see org.tedros.fx.builder.ITGenericBuilder#build()
	 */
	@Override
	public Callback<TableView<S>, TableRow<S>> build() {
		return (tblVw) -> {
			final TableRow<S> row = new TableRow<>();
	        final ContextMenu rowMenu = new ContextMenu();
	        ContextMenu tableMenu = tblVw.getContextMenu();
	        if (tableMenu != null) 
	          rowMenu.getItems().addAll(tableMenu.getItems());
	        
	        if(rowMenu.getItems().size()>0)
	          rowMenu.getItems().add(new SeparatorMenuItem());
	        	
	        rowMenu.getItems().addAll(getMenuItems(tblVw, row));
	        
	        if(tblVw.getSelectionModel().getSelectionMode().equals(SelectionMode.MULTIPLE)) {
		        if(rowMenu.getItems().size()>0)
			          rowMenu.getItems().add(new SeparatorMenuItem());
		        final MenuItem selAll = new MenuItem(TLanguage.getInstance().getString(TFxKey.BUTTON_SELECTALL));
				selAll.setOnAction(e->{
					tblVw.getSelectionModel().selectAll();
				});
				rowMenu.getItems().add(selAll);
	        }
	        
	        row.contextMenuProperty().bind(
	            Bindings.when(Bindings.isNotNull(row.itemProperty()))
	            .then(rowMenu)
	            .otherwise((ContextMenu) null));
	        return row;
		};
	}
	
	
	

}
