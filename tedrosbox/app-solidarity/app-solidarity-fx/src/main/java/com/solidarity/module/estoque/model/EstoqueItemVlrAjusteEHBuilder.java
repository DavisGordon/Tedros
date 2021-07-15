/**
 * 
 */
package com.solidarity.module.estoque.model;

import com.tedros.fxapi.builder.ITEventHandlerBuilder;
import com.tedros.fxapi.descriptor.TComponentDescriptor;

import javafx.event.EventHandler;
import javafx.scene.control.TableColumn.CellEditEvent;

/**
 * @author Davis Gordon
 *
 */
public class EstoqueItemVlrAjusteEHBuilder implements ITEventHandlerBuilder<CellEditEvent> {

	/**
	 * 
	 */
	public EstoqueItemVlrAjusteEHBuilder() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public EventHandler build() {
		return new EventHandler<CellEditEvent>() {
	        @Override
	        public void handle(CellEditEvent t) {
	        	EstoqueItemModelView mv = (EstoqueItemModelView) t.getTableView().getItems().get(
	                t.getTablePosition().getRow());
	        	
	        	mv.getQtdAjuste().setValue((Number) t.getNewValue());
	            
	        }
	    };
	}

	@Override
	public TComponentDescriptor getComponentDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setComponentDescriptor(TComponentDescriptor componentDescriptor) {
		// TODO Auto-generated method stub
		
	}

}
