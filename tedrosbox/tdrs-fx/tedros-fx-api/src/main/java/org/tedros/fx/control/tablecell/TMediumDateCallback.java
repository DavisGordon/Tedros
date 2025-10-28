<<<<<<<< HEAD:tedrosbox/app-solidarity/app-solidarity-fx/src/main/java/com/solidarity/module/pessoa/table/TipoContatoCallback.java
/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 25/02/2014
 */
package com.solidarity.module.pessoa.table;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TipoContatoCallback implements Callback<TableColumn, TableCell> {

	

	@Override
	public TableCell call(TableColumn tipo) {
		TipoContatoTableCell t = new TipoContatoTableCell();
		
		return t;
	}
	
}
========
/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 25/02/2014
 */
package org.tedros.fx.control.tablecell;

import java.text.DateFormat;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TMediumDateCallback implements Callback<TableColumn, TableCell> {

	@Override
	public TableCell call(TableColumn tipo) {
		return new TDateTableCell(DateFormat.MEDIUM);
	}
	
}
>>>>>>>> tedrosbox_v17_globalweb:tedrosbox/tdrs-fx/tedros-fx-api/src/main/java/org/tedros/fx/control/tablecell/TMediumDateCallback.java
