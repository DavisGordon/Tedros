package org.tedros.tools.module.notify.table;

import org.tedros.core.TLanguage;
import org.tedros.core.notify.model.TState;
import org.tedros.fx.control.TTableCell;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

@SuppressWarnings("rawtypes")
public class TNotifyLogStateCallBack implements Callback <TableColumn, TableCell> {
	
	@Override
	public TableCell call(TableColumn param) {
		return new TTableCell<TableColumn, TState>() {
			public String processItem(TState item){
				return (item!=null) 
						? TLanguage.getInstance().getString(item.getValue())
								: "";
			}
		};
	}
	
}
