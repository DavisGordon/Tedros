package org.tedros.fx.presenter.model.decorator;

import org.tedros.fx.annotation.TDefaultValue;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.dynamic.decorator.TDynaViewSimpleBaseDecorator;
import org.tedros.fx.presenter.dynamic.view.TDynaView;

/**
 * The decoraror of the simple view.
 * No buttons and actions.
 * @author Davis Gordon
 *
 * @param <M>
 */
@SuppressWarnings("rawtypes")
public class TViewDecorator<M extends TModelView> 
extends TDynaViewSimpleBaseDecorator<M> {
	
	private TPresenter tPresenter;
  
	@Override
    public void decorate() {
    	tPresenter = getPresenter().getPresenterAnnotation();
		configFormSpace();
		configViewTitle();
	}

	/**
	 * Config the view title based on 
	 * the {@link TPresenter} defined in the TEntityModelView
	 */
	protected void configViewTitle() {
		if(!tPresenter.decorator().viewTitle().equals(TDefaultValue.TVIEW_viewTitle))
			setViewTitle(null);
		else {
			((TDynaView)super.getView()).gettLayout().getChildren().remove(0);
		}
	}

	/**
	 * Config the form space
	 */
	protected void configFormSpace() {
		addItemInTCenterContent(getPresenter().getView().gettFormSpace());
	}
	
	

}
