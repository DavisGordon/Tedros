package org.tedros.fx.layout;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class TGridPane extends GridPane {

	private int x = 0;
	private int y = 0;
	private int rows = 0;
	private int cols = 0;
	
	public TGridPane(){
		
	}
	
	public TGridPane(int totalColumns, int totalRows, double spaceBetweenColumns, double spaceBetweenRows) {
		rows = totalRows;
		cols = totalColumns;
		setVgap(spaceBetweenRows);
		setHgap(spaceBetweenColumns);
		
	}
	
	public void setTotalColumns(int total){
		cols = total;
	}
	
	public void setTotalRows(int total){
		rows = total;
	}
	
	
	public void setContent(Node node){
		if(x==rows && y==cols) rows++;
		add(node, y, x);
		if(y==(cols-1)){
			y=0;
			x++;
		}else
			y++;
	}
}
