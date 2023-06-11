/**
 * 
 */
package org.tedros.fx.control;

import org.tedros.core.ITModule;
import org.tedros.fx.annotation.presenter.TDetailListViewPresenter;
import org.tedros.fx.annotation.presenter.TDetailTableViewPresenter;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.dynamic.view.TDynaGroupView;
import org.tedros.server.entity.ITEntity;

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
		super.tRequiredNodeProperty().setValue(this);
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
