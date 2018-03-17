package com.tedros.fxapi.annotation.parser;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

import org.apache.commons.lang3.StringUtils;

import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableNestedColumn;
import com.tedros.fxapi.annotation.control.TTableSubColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.presenter.model.TModelView;

@SuppressWarnings("rawtypes")
public class TTableViewParser extends TAnnotationParser<TTableView, TableView> {

	private static TTableViewParser instance;
	
	private TTableViewParser(){
		
	}
	
	public static  TTableViewParser getInstance(){
		if(instance==null)
			instance = new TTableViewParser();
		return instance;	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void parse(TTableView annotation, TableView tableView, String... byPass) throws Exception {
		
		TTableColumn[] columns = annotation.columns();
		
		for (final TTableColumn tTableColumn : columns) {
			final TableColumn tableColumn = new TableColumn<>();
			TTableSubColumn[] parenteColumns = tTableColumn.columns();
			
			boolean clnVf = false;
			for (final TTableSubColumn tTableSubColumn : parenteColumns) {
				final TableColumn tableSubColumn = new TableColumn<>();
				tableColumn.getColumns().add(tableSubColumn);
				
				TTableNestedColumn[] nestedColumns = tTableSubColumn.columns();
				boolean sbClnVf = false;
				for (final TTableNestedColumn tTableNestedColumn : nestedColumns) {
					final TableColumn tableNestedColumn = new TableColumn<>();
					tableSubColumn.getColumns().add(tableNestedColumn);
					if(StringUtils.isNotBlank(tTableNestedColumn.cellValue())){
						tableNestedColumn.setCellValueFactory(
								new PropertyValueFactory(tTableNestedColumn.cellValue()){   
									 @Override
									 public ObservableValue call(CellDataFeatures p) {
									  return new ReadOnlyObjectWrapper(((ObservableValue)((TModelView) p.getValue()).getProperty(tTableNestedColumn.cellValue())).getValue());
									 }
									});
						sbClnVf = true;
					}
					final TComponentDescriptor descriptor = new TComponentDescriptor(getComponentDescriptor(), null);
					callParser(tTableNestedColumn, tableNestedColumn, descriptor);
				}
				
				if(!sbClnVf){
					if(StringUtils.isNotBlank(tTableSubColumn.cellValue())){
						tableSubColumn.setCellValueFactory(
								new PropertyValueFactory(tTableSubColumn.cellValue()){   
									 @Override
									 public ObservableValue call(CellDataFeatures p) {
									  return new ReadOnlyObjectWrapper(((ObservableValue)((TModelView) p.getValue()).getProperty(tTableSubColumn.cellValue())).getValue());
									 }
									});
						clnVf = true;
					}
				}
				final TComponentDescriptor descriptor = new TComponentDescriptor(getComponentDescriptor(), null);
				callParser(tTableSubColumn, tableSubColumn, descriptor);
			}
			
			if(!clnVf){
				if(StringUtils.isNotBlank(tTableColumn.cellValue())){
					tableColumn.setCellValueFactory(
							new PropertyValueFactory(tTableColumn.cellValue()){   
								 @Override
								 public ObservableValue call(CellDataFeatures p) {
								  return new ReadOnlyObjectWrapper( ((ObservableValue)((TModelView) p.getValue()).getProperty(tTableColumn.cellValue())).getValue());
								 }
								});
					clnVf = true;
				}
			}
			final TComponentDescriptor descriptor = new TComponentDescriptor(getComponentDescriptor(), null);
			callParser(tTableColumn, tableColumn, descriptor);
			
			tableView.getColumns().add(tableColumn);
		}
		
		
		// *******************
		
		for (final TTableColumn tTableColumn : columns) {
			final TableColumn tableColumn = getColumn(tableView, tTableColumn.text());
			TTableSubColumn[] parenteColumns = tTableColumn.columns();
			
			boolean clnVf = false;
			for (final TTableSubColumn tTableSubColumn : parenteColumns) {
				final TableColumn tableSubColumn = getColumn(tableView, tTableSubColumn.text());
				//tableColumn.getColumns().add(tableSubColumn);
				
				TTableNestedColumn[] nestedColumns = tTableSubColumn.columns();
				boolean sbClnVf = false;
				for (final TTableNestedColumn tTableNestedColumn : nestedColumns) {
					final TableColumn tableNestedColumn = getColumn(tableView, tTableNestedColumn.text());
					//tableSubColumn.getColumns().add(tableNestedColumn);
					if(StringUtils.isNotBlank(tTableNestedColumn.cellValue())){
						if(tTableNestedColumn.cellFactory().callBack() != Callback.class){
							Callback callback = tTableNestedColumn.cellFactory().callBack().newInstance();
							tableNestedColumn.setCellFactory(callback);
						}else{
							
							if(tTableNestedColumn.cellFactory().tableCell()==CheckBoxTableCell.class) 
								tableNestedColumn.setCellFactory(CheckBoxTableCell.forTableColumn(tableNestedColumn));
							else
							if(tTableNestedColumn.cellFactory().tableCell()==ChoiceBoxTableCell.class) 
								tableNestedColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(tableNestedColumn));
							else						
							if(tTableNestedColumn.cellFactory().tableCell()==ComboBoxTableCell.class) 
								tableNestedColumn.setCellFactory(ComboBoxTableCell.forTableColumn(tableNestedColumn));
							else						
							if(tTableNestedColumn.cellFactory().tableCell()==ProgressBarTableCell.class) 
								tableNestedColumn.setCellFactory(ProgressBarTableCell.forTableColumn());
							else						
							if(tTableNestedColumn.cellFactory().tableCell()==TextFieldTableCell.class)
								tableNestedColumn.setCellFactory(TextFieldTableCell.forTableColumn());
						}
						tableNestedColumn.setEditable(true);
						sbClnVf = true;
					}
//					final TComponentDescriptor descriptor = new TComponentDescriptor(getComponentDescriptor(), null);
//					callParser(tTableNestedColumn, tableNestedColumn, descriptor);
				}
				
				if(!sbClnVf){
					if(tTableSubColumn.cellFactory().parse()){
						
						if(tTableSubColumn.cellFactory().callBack() != Callback.class){
							Callback callback = tTableSubColumn.cellFactory().callBack().newInstance();
							tableSubColumn.setCellFactory(callback);
						}else{
							
							if(tTableSubColumn.cellFactory().tableCell()==CheckBoxTableCell.class) 
								tableSubColumn.setCellFactory(CheckBoxTableCell.forTableColumn(tableSubColumn));
							else
							if(tTableSubColumn.cellFactory().tableCell()==ChoiceBoxTableCell.class) 
								tableSubColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(tableSubColumn));
							else						
							if(tTableSubColumn.cellFactory().tableCell()==ComboBoxTableCell.class) 
								tableSubColumn.setCellFactory(ComboBoxTableCell.forTableColumn(tableSubColumn));
							else						
							if(tTableSubColumn.cellFactory().tableCell()==ProgressBarTableCell.class) 
								tableSubColumn.setCellFactory(ProgressBarTableCell.forTableColumn());
							else						
							if(tTableSubColumn.cellFactory().tableCell()==TextFieldTableCell.class)
								tableSubColumn.setCellFactory(TextFieldTableCell.forTableColumn());
						}
						tableSubColumn.setEditable(true);
						clnVf = true;
					}
				}
//				final TComponentDescriptor descriptor = new TComponentDescriptor(getComponentDescriptor(), null);
//				callParser(tTableSubColumn, tableSubColumn, descriptor);
			}
			
			if(!clnVf){
				if(tTableColumn.cellFactory().parse()){
					
					if(tTableColumn.cellFactory().callBack() != Callback.class){
						Callback callback = tTableColumn.cellFactory().callBack().newInstance();
						tableColumn.setCellFactory(callback);
					}else{
						
						if(tTableColumn.cellFactory().tableCell()==CheckBoxTableCell.class) 
							tableColumn.setCellFactory(CheckBoxTableCell.forTableColumn(tableColumn));
						else
						if(tTableColumn.cellFactory().tableCell()==ChoiceBoxTableCell.class) 
							tableColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(tableColumn));
						else						
						if(tTableColumn.cellFactory().tableCell()==ComboBoxTableCell.class) 
							tableColumn.setCellFactory(ComboBoxTableCell.forTableColumn(tableColumn));
						else						
						if(tTableColumn.cellFactory().tableCell()==ProgressBarTableCell.class) 
							tableColumn.setCellFactory(ProgressBarTableCell.forTableColumn());
						else						
						if(tTableColumn.cellFactory().tableCell()==TextFieldTableCell.class)
							tableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
					}
					tableColumn.setEditable(true);
					clnVf = true;
				}
			}
//			final TComponentDescriptor descriptor = new TComponentDescriptor(getComponentDescriptor(), null);
//			callParser(tTableColumn, tableColumn, descriptor);
		}
		
		
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		tableView.setEditable(true);
				
		super.parse(annotation, tableView, "columns");
	}
	
	private TableColumn getColumn(Object obj, String text){
		TableColumn tableColumn = null;
		if(obj instanceof TableView){
			TableView tbv = (TableView) obj;
			for(Object o : tbv.getColumns()){
				TableColumn tc = (TableColumn) o;
				if(tc.getText().equals(text))
					tableColumn = tc;
				if(tc.getColumns()!=null && tc.getColumns().size()>0)
					tableColumn = getColumn(tc, text);
			}
		}else{
			TableColumn tbc = (TableColumn) obj;
			for(Object o : tbc.getColumns()){
				TableColumn tc = (TableColumn) o;
				if(tc.getText().equals(text))
					tableColumn = tc;
				if(tc.getColumns()!=null && tc.getColumns().size()>0)
					tableColumn = getColumn(tc, text);
			}
		}
		
		return tableColumn;
	}
	
}
