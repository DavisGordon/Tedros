/**
 * 
 */
package com.tedros.fxapi.control;

import com.tedros.core.ITModule;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.fxapi.annotation.presenter.TDetailListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TDetailTableViewPresenter;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.presenter.dynamic.view.TDynaGroupView;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.scene.Node;

/**<pre>
 * The detail field component opens a view to add and remove items.
 * The TEntityModelView class must configured with {@link TDetailTableViewPresenter} 
 * or  {@link TDetailListViewPresenter}
 * </pre>
 * @author Davis Gordon
 *
 */
public class TDetailListField extends TRequiredDetailField {

	@SuppressWarnings("rawtypes")
	private TDynaGroupView view;
	@SuppressWarnings("rawtypes")
	private ITObservableList<TModelView> tSelectedItems;
	
	@SuppressWarnings({ "rawtypes"})
	public TDetailListField(Class<? extends TEntityModelView> tEntityViewClass,
			Class<? extends ITEntity> entityClass, ITObservableList<TModelView> details) {
	
		this(null,tEntityViewClass, entityClass, details);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TDetailListField(ITModule module, 
			Class<? extends TEntityModelView> tEntityViewClass,
			Class<? extends ITEntity> entityClass, ITObservableList<TModelView> details) {
		
		this.tSelectedItems = details;
		view = (module==null) 
				? new TDynaGroupView(tEntityViewClass, entityClass, details)
						: new TDynaGroupView(module, tEntityViewClass, entityClass, details) ;
		getChildren().add(view);
		view.tLoad();
	}
	
	
	/**
	 * @return the view
	 */
	@SuppressWarnings("rawtypes")
	public Node gettComponent() {
		return view;
	}
	/**
	 * @return the tSelectedItems
	 */
	@SuppressWarnings("rawtypes")
	public ITObservableList<TModelView> gettSelectedItems() {
		return tSelectedItems;
	}

	
}
