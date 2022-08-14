package org.tedros.core.context;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.presenter.ITPresenter;
import org.tedros.core.presenter.view.ITView;

/**
 * The view Context.
 * 
 * @author Davis Gordon
 * */
@SuppressWarnings("rawtypes")
public final class TViewContext {

	private ITView view;
	private TModuleContext moduleContext;
	
	private TSecurityDescriptor securityDescriptor;
	
	private TEntry<Object> tEntry;
	
	
	public TViewContext(TModuleContext moduleContext, ITView view){
		this.view = view;
		this.tEntry = new TEntry<>();
		this.moduleContext = moduleContext;
		if(!view.gettPresenter().isViewLoaded())
			view.tLoad();
	}
	
	public ITView getView(){
		return view;
	}
	
	public boolean stop(){
		if(getPresenter().invalidate()) {
			moduleContext = null;
			view = null;
			tEntry.clear();
			return true;
		} else
			return false;
	}
	
	public TModuleContext getModuleContext() {
		return moduleContext;
	}
	
	public ITPresenter getPresenter() {
		ITView view = getView();
		
		return (view!=null) 
				? view.gettPresenter()
						: null;
	}
	
	public Object getEntry(String key){
		return (StringUtils.isNotBlank(key) && tEntry!=null) 
				? tEntry.getEntry(key)
						: null;
	}
	
	public Object setEntry(String key, Object value){
		if(StringUtils.isNotBlank(key) && value!=null && tEntry!=null) 
			tEntry.addEntry(key, value);
		return null;
	}

	public TSecurityDescriptor getSecurityDescriptor() {
		return securityDescriptor;
	}

	public String canStop() {
		return getPresenter().canInvalidate();
	}
}
