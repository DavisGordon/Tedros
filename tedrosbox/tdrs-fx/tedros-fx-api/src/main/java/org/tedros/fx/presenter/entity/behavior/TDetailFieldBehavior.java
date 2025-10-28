package org.tedros.fx.presenter.entity.behavior;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.ArrayUtils;
import org.tedros.api.presenter.ITPresenter;
import org.tedros.api.presenter.view.TViewMode;
import org.tedros.fx.annotation.presenter.TDetailTableViewPresenter;
import org.tedros.fx.control.action.TPresenterAction;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.fx.presenter.behavior.TActionType;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.dynamic.behavior.TDetailFieldBaseBehavior;
import org.tedros.fx.presenter.dynamic.behavior.TDynaViewSimpleBaseBehavior;
import org.tedros.fx.presenter.view.group.TGroupPresenter;
import org.tedros.server.entity.ITEntity;

/**
 * The behavior of the table detail view. 
 * This behavior can be applied on detail entities.
 * A TableView is created to list the 
 * details. It can be set using the 
 * {@link TDetailTableViewPresenter} annotation on 
 * the TEntityModelView. 
 * @author Davis Gordon
 *
 * @param <M>
 * @param <E>
 */
@SuppressWarnings({ "rawtypes" })
public class TDetailFieldBehavior<M extends TEntityModelView<E>, E extends ITEntity>
extends TDetailFieldBaseBehavior<M, E> {
	
	@Override
	public void load() {
		super.load();
		initialize();
	}
	
	/**
	 * Initialize the behavior
	 */
	public void initialize() {
		super.configAddButton();
		super.configCleanButton();
		super.configRemoveButton();
		
		try {
			M model = (M) super.getModelViewClass().getConstructor(entityClass).newInstance(entityClass.getDeclaredConstructor().newInstance());
			setModelView(model);
			super.showForm(TViewMode.EDIT);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	@Override
	public void addAction(TPresenterAction action) {
		boolean flag = false;
		if(action!=null && action.getTypes()!=null) { 
			for(TActionType a : new TActionType[] {TActionType.REMOVE, TActionType.CLEAN, TActionType.ADD})
				if(ArrayUtils.contains(action.getTypes(), a)) {
					flag = true;
					break;
				}	
		}else
			flag = true;
		if(flag) {
			super.addAction(action);
		}else {
			final ITPresenter pre = getModulePresenter();
			final TDynaPresenter presenter = pre instanceof TDynaPresenter 
					? (TDynaPresenter) pre
							: (TDynaPresenter) ((TGroupPresenter)pre).getSelectedView().gettPresenter();
			
			final TDynaViewSimpleBaseBehavior behavior = (TDynaViewSimpleBaseBehavior) presenter.getBehavior(); 
			behavior.addAction(action);
		}
	}
	
	/**
	 * Disable all buttons
	 * @param flag
	 */
	public void setDisableModelActionButtons(boolean flag) {
		if(decorator.gettAddButton()!=null)
			decorator.gettAddButton().setDisable(flag);
		if(decorator.gettRemoveButton()!=null)
			decorator.gettRemoveButton().setDisable(flag);
		if(decorator.gettCleanButton()!=null)
			decorator.gettCleanButton().setDisable(flag);
	}


	@Override
	public String canInvalidate() {
		return null;
	}

	@Override
	public void loadModelView(M modelView) {
		super.setModelView(modelView);
	}
}
