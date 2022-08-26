/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 22/04/2014
 */
package org.tedros.fx.presenter.view.group;

import java.util.List;

import org.tedros.api.presenter.view.ITGroupViewItem;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public abstract class TGroupViewControl {
	
	public abstract void initializeView(final ITGroupViewItem item, final List<TViewItem> itens);

}
