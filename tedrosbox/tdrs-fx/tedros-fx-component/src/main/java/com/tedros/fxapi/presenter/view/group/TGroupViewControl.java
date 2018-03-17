/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 22/04/2014
 */
package com.tedros.fxapi.presenter.view.group;

import java.util.List;

import com.tedros.core.presenter.view.ITGroupViewItem;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public abstract class TGroupViewControl {
	
	public abstract void initializeView(final ITGroupViewItem item, final List<TViewItem> itens);

}
