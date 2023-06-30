package org.tedros.fx.presenter.modal.behavior;

import java.lang.reflect.InvocationTargetException;

import org.tedros.api.presenter.view.TViewMode;
import org.tedros.fx.annotation.presenter.TSelectionModalPresenter;
import org.tedros.fx.control.action.TPresenterAction;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.behavior.TActionType;
import org.tedros.fx.presenter.dynamic.behavior.TDynaViewSelectionBaseBehavior;
import org.tedros.fx.presenter.modal.decorator.TSelectionModalDecorator;
import org.tedros.server.entity.ITEntity;

/**
 * The behavior of the open modal view 
 * to select an entity.
 * It can be set using the 
 * {@link TSelectionModalPresenter} annotation on 
 * the TEntityModelView. 
 * @author Davis Gordon
 *
 * @param <M>
 * @param <E>
 */
public class TSelectionModalBehavior<M extends TModelView<E>, E extends ITEntity>
extends TDynaViewSelectionBaseBehavior<M, E> {
	
	private TSelectionModalDecorator<M> decorator;
		
	@Override
	public void load() {
		super.load();
		this.decorator = (TSelectionModalDecorator<M>) getPresenter().getDecorator();
		initialize();
	}
	
	/**
	 * Initialize the behavior
	 */
	public void initialize() {
		configCleanButton();
		configSearchButton();
		configCancelButton();
		configCloseButton();
		configAddButton();
		configSelectAllButton();
		configSelectedListView();
		final Class<E> entityClass = getModelClass();
		
		addAction(new TPresenterAction(TActionType.SEARCH) {
			@Override
			public boolean runBefore() {
				return true;
			}

			@Override
			public void runAfter() {
				decorator.expandResultPane();
			}
			
		});
		
		addAction(new TPresenterAction(TActionType.CLEAN, TActionType.CANCEL) {
			@Override
			public boolean runBefore() {
				return true;
			}

			@Override
			public void runAfter() {
				decorator.expandFilterPane();
			}
			
		});
		
		M model;
		try {
			model = (M) getModelViewClass().getConstructor(entityClass).newInstance(entityClass.newInstance());
			setModelView(model);
			showForm(TViewMode.EDIT);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public String canInvalidate() {
		return null;
	}


}
