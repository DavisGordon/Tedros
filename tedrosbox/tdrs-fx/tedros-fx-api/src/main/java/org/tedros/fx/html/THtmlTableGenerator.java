/**
 * 
 */
package org.tedros.fx.html;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.tedros.fx.domain.THtmlConstant;

/**
 * @author Davis Gordon
 */
public class THtmlTableGenerator implements ITHtmlGenerator {
	
	public class TColumn {
		
		private String value;
		private String columnHeaderStyle;
		private String columnHeaderAttribute;
		private String columnStyle;
		private String columnAttribute;
		
		public TColumn(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getColumnHeaderStyle() {
			return columnHeaderStyle;
		}
		public void setColumnHeaderStyle(String columnHeaderStyle) {
			this.columnHeaderStyle = columnHeaderStyle;
		}
		public String getColumnStyle() {
			return columnStyle;
		}
		public void setColumnStyle(String columnStyle) {
			this.columnStyle = columnStyle;
		}

		public String getColumnHeaderAttribute() {
			return columnHeaderAttribute;
		}

		public void setColumnHeaderAttribute(String columnHeaderAttribute) {
			this.columnHeaderAttribute = columnHeaderAttribute;
		}

		public String getColumnAttribute() {
			return columnAttribute;
		}

		public void setColumnAttribute(String columnAttribute) {
			this.columnAttribute = columnAttribute;
		}
		
		@Override
		public String toString() {
			return value;
		}
	}
	
	private static final String TAG_OPEN_TABLE = "<table "+THtmlConstant.STYLE+" "+THtmlConstant.ATTRIBUTE+" >"; 
	private static final String TAG_OPEN_TR = "<tr "+THtmlConstant.STYLE+" "+THtmlConstant.ATTRIBUTE+" >";
	private static final String TAG_OPEN_TH = "<th "+THtmlConstant.STYLE+" "+THtmlConstant.ATTRIBUTE+" >";
	private static final String TAG_OPEN_TD = "<td "+THtmlConstant.STYLE+" "+THtmlConstant.ATTRIBUTE+" >";
	
	private static final String TAG_CLOSE_TABLE = "</table>";
	private static final String TAG_CLOSE_TR = "</tr>";
	private static final String TAG_CLOSE_TH = "</th>";
	private static final String TAG_CLOSE_TD = "</td>";

	private List<List<TColumn>> rows;
	private String tableStyle;
	private String tableAttribute;
	
	private String rowHeaderStyle;
	private String rowHeaderAttribute;
	
	private String rowStyle;
	private String rowAttribute;
	
	private StringBuffer table;
	
	public THtmlTableGenerator() {
		
	}
	
	public String getHtml() {
		build();
		return table.toString();
	}
	
	private String getStyle(String css){
		return (StringUtils.isNotBlank(css)) ? " style='"+css+"' " : "";
	}
	
	private void build(){
		
		int cont = 0;
		
		table = new StringBuffer();
		
		table.append(TAG_OPEN_TABLE.replace(THtmlConstant.STYLE, getStyle(tableStyle)).replace(THtmlConstant.ATTRIBUTE, tableAttribute));
		
		for (List<TColumn> row : rows) {
			cont++;
			
			table.append(TAG_OPEN_TR.replace(THtmlConstant.STYLE, getStyle( (cont==1) 
					? rowHeaderStyle 
							: rowStyle))
							.replace(THtmlConstant.ATTRIBUTE, (cont==1) 
									? rowHeaderAttribute
											: rowAttribute));
			
			for (TColumn column : row) {
				table.append((cont==1) 
						? TAG_OPEN_TH.replace(THtmlConstant.STYLE, getStyle(column.getColumnHeaderStyle()).replace(THtmlConstant.ATTRIBUTE, column.getColumnHeaderAttribute()))  
								: TAG_OPEN_TD.replace(THtmlConstant.STYLE, getStyle(column.getColumnStyle())).replace(THtmlConstant.ATTRIBUTE, column.getColumnAttribute()));
				
				table.append(column.getValue());
				
				table.append((cont==1) ? TAG_CLOSE_TH : TAG_CLOSE_TD);
			}
			table.append(TAG_CLOSE_TR);
		}
		
		table.append(TAG_CLOSE_TABLE);
		
	}

	public List<List<TColumn>> getRows() {
		return rows;
	}

	public void setRows(List<List<TColumn>> rows) {
		this.rows = rows;
	}

	public String getTableStyle() {
		return tableStyle;
	}

	public void setTableStyle(String tableStyle) {
		this.tableStyle = tableStyle;
	}

	public String getRowStyle() {
		return rowStyle;
	}

	public void setRowStyle(String rowStyle) {
		this.rowStyle = rowStyle;
	}

	public String getTableAttribute() {
		return tableAttribute;
	}

	public void setTableAttribute(String tableAttribute) {
		this.tableAttribute = tableAttribute;
	}

	public String getRowHeaderStyle() {
		return rowHeaderStyle;
	}

	public void setRowHeaderStyle(String rowHeaderStyle) {
		this.rowHeaderStyle = rowHeaderStyle;
	}

	public String getRowHeaderAttribute() {
		return rowHeaderAttribute;
	}

	public void setRowHeaderAttribute(String rowHeaderAttribute) {
		this.rowHeaderAttribute = rowHeaderAttribute;
	}

	public String getRowAttribute() {
		return rowAttribute;
	}

	public void setRowAttribute(String rowAttribute) {
		this.rowAttribute = rowAttribute;
	}

	@Override
	public String toString() {
		return table!=null ? table.toString() : super.toString();
	}
}
