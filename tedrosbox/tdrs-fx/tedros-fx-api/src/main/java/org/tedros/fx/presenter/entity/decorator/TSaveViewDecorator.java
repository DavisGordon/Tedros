package org.tedros.fx.presenter.entity.decorator;

import org.apache.commons.lang3.ArrayUtils;
import org.tedros.api.presenter.view.ITDynaView;
import org.tedros.fx.annotation.TDefaultValue;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import org.tedros.fx.presenter.dynamic.view.TDynaView;

import javafx.geometry.Pos;
import javafx.scene.Node;

/**
 * The save view decorator. 
 * This decorator can be applied to save an entity. 
 * Only the save button is configured.
 * @author Davis Gordon
 *
 * @param <M>
 */
@SuppressWarnings("rawtypes")
public class TSaveViewDecorator<M extends TModelView> 
extends TDynaViewCrudBaseDecorator<M> {
	
	private TPresenter tPresenter;
  
	@Override
    public void decorate() {
    	tPresenter = getPresenter().getPresenterAnnotation();
		configFormSpace();
		configAllButtons();
		configViewTitle();
	}

	/**
	 * Config the save button based on 
	 * the {@link TDecorator} defined in the TEntityModelView
	 */
	protected void configAllButtons() {
		TDecorator tDeco = tPresenter.decorator();
		Node[] nodes = new Node[0];
		
		
		if(tDeco.buildSaveButton()) {
			buildSaveButton(null);
			nodes = ArrayUtils.add(nodes, gettSaveButton());
		}
		
		final ITDynaView<M> view = getView();
		view.gettHeaderHorizontalLayout().setAlignment(Pos.CENTER_LEFT);
		
		if(nodes.length>0)
			addItemInTHeaderToolBar(nodes);
		else 
			((TDynaView)super.getView()).gettLayout().getChildren().remove(2);
		
		
		// add the mode radio buttons
		if(tDeco.buildModesRadioButton()) {
			buildModesRadioButton(null, null);
			addItemInTHeaderHorizontalLayout(gettEditModeRadio(), gettReadModeRadio());
		}
		
		
		// set padding at rigth in left content pane
		addPaddingInTLeftContent(0, 4, 0, 0);
	}

	/**
	 * Config the view title based on 
	 * the {@link TPresenter} defined in the TEntityModelView
	 */
	protected void configViewTitle() {
		final TPresenter tPresenter = getPresenter().getPresenterAnnotation();
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
