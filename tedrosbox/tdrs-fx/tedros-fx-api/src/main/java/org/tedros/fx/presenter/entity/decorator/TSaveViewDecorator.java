package org.tedros.fx.presenter.entity.decorator;

import org.apache.commons.lang3.ArrayUtils;
import org.tedros.fx.annotation.TAnnotationDefaultValue;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.fx.presenter.model.TModelView;

import javafx.scene.Node;

@SuppressWarnings("rawtypes")
public class TSaveViewDecorator<M extends TModelView> 
extends TDynaViewCrudBaseDecorator<M> {
	
	private TPresenter tPresenter;
  
    public void decorate() {
    	tPresenter = getPresenter().getPresenterAnnotation();
		configFormSpace();
		configAllButtons();
		configViewTitle();
	}

	

	protected void configAllButtons() {
		TDecorator tDeco = tPresenter.decorator();
		Node[] nodes = new Node[0];
		
		
		if(tDeco.buildSaveButton()) {
			buildSaveButton(null);
			nodes = ArrayUtils.add(nodes, gettSaveButton());
		}
		
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

	protected void configViewTitle() {
		final TPresenter tPresenter = getPresenter().getPresenterAnnotation();
		if(!tPresenter.decorator().viewTitle().equals(TAnnotationDefaultValue.TVIEW_viewTitle))
			setViewTitle(null);
		else {
			((TDynaView)super.getView()).gettLayout().getChildren().remove(0);
		}
	}

	protected void configFormSpace() {
		addItemInTCenterContent(getPresenter().getView().gettFormSpace());
	}
	
	

}
