package com.tedros.settings.security.action;

import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.settings.security.model.TProfileModelView;

public class TProfileCompleteTableViewAction extends TPresenterAction<TDynaPresenter<TProfileModelView>> {

	@Override
	public boolean runBefore(TDynaPresenter<TProfileModelView> presenter) {
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void runAfter(TDynaPresenter<TProfileModelView> presenter) {
		
		/*TProfileModelView tProfileModelView = (TProfileModelView) presenter.getBehavior().getModelView();
		final ObservableList<TAuthorizationTableView> authorizations = tProfileModelView.getAutorizations();
			
		TInternationalizationEngine iEngine = TInternationalizationEngine.getInstance(TConstant.UUI);
		
				
		for (Class clazz : TedrosContext.getClassesAnnotatedWith(TSecurity.class) ) {
			try {
				
				TSecurity tSecurity = (TSecurity) clazz.getAnnotation(TSecurity.class);
					
				for(TAuthorizationType authorizationType : tSecurity.allowedAccesses()){
					
					TAuthorizationTableView authorization = null;
					
					for(TAuthorizationTableView tAuthorization : authorizations){
						if(tSecurity.id().equals(tAuthorization.getSecurityId().getValue()) 
								&& authorizationType.name().equals(tAuthorization.getType().getValue()))
						{
							authorization = tAuthorization;
							break;
						}
					}
					
					if(authorization==null){
						authorization = new TAuthorizationTableView(new TAuthorization());
						authorization.getType().set(authorizationType.name());
						authorization.getSecurityId().set(tSecurity.id());
						authorization.getEnabled().set(true);
						authorizations.add(authorization);
					}
					
					authorization.getAppName().set(iEngine.getString(tSecurity.appName()));
					authorization.getModuleName().set(iEngine.getString(tSecurity.moduleName()));
					authorization.getViewName().set(iEngine.getString(tSecurity.viewName()));
					
					authorization.getTypeDescription().set( iEngine.getString(TAuthorizationType.getFromName(authorizationType.name()).getValue()));
					
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}*/
	}
}
