package org.tedros.fx.presenter.view;

import java.net.URL;

import org.tedros.fx.model.TEntityModelView;
import org.tedros.fx.presenter.TCrudPresenter;


@SuppressWarnings("rawtypes")
public abstract class TCrudView<P extends TCrudPresenter, M extends TEntityModelView> 
extends TView<P> implements ITCrudView<M>{
	
	public TCrudView(P presenter) {
		super(presenter);
	}
	
	public TCrudView(P presenter, URL fxmlURL) {
		super(presenter, fxmlURL);
	}
	
	@Override
	public P getCrudPresenter(){
		return super.gettPresenter();
	}
	

	
	
}
