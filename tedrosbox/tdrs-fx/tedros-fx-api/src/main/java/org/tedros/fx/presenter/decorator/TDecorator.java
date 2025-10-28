package org.tedros.fx.presenter.decorator;

import org.tedros.api.presenter.ITPresenter;
import org.tedros.api.presenter.decorator.ITDecorator;
import org.tedros.api.presenter.view.ITView;
import org.tedros.core.ITModule;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosAppManager;

import javafx.scene.layout.StackPane;

/***
 * The root view decorator.
 * @author Davis Gordon
 *
 * @param <P>
 */
@SuppressWarnings("rawtypes")
public abstract class TDecorator<P extends ITPresenter> 
implements ITDecorator<P> {

	private P presenter;
	private StackPane screenSaverPane;
	
	protected TLanguage iEngine = TLanguage.getInstance(null); 

	@Override
	public StackPane getScreenSaverPane() {
		if(screenSaverPane==null){
			screenSaverPane = new StackPane();
			screenSaverPane.setId("t-screen-saver");
		}
		return screenSaverPane;
	}
	
	@Override
	public void showScreenSaver() {
		getView().gettFormSpace().getChildren().add(getScreenSaverPane());
	}

	@Override
	public P getPresenter() {
		return presenter;
	}

	@Override
	public void setPresenter(P presenter) {
		this.presenter = presenter;
		iEngine.setCurrentUUID(getApplicationUUID());
	}

	@Override
	@SuppressWarnings("unchecked")
	public <V extends ITView> V getView() {
		return (V) this.presenter.getView();
	}	
	
	@Override
	public String getApplicationUUID() {
		String uuid = null;
		ITModule module = getPresenter().getModule();
		if(module!=null){
			uuid = TedrosAppManager.getInstance().getModuleContext(module).getModuleDescriptor().getApplicationUUID();
		}
		return uuid;
	}
	
}
