/**
 * 
 */
package com.tedros.settings.security.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.TLanguage;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.context.TReflections;
import com.tedros.core.context.TedrosContext;
import com.tedros.core.security.model.TAuthorization;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.exception.TException;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.modal.TMessage;
import com.tedros.fxapi.modal.TMessageBox;
import com.tedros.fxapi.modal.TMessageType;
import com.tedros.fxapi.presenter.behavior.TActionType;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewSimpleBaseBehavior;
import com.tedros.fxapi.presenter.paginator.TPagination;
import com.tedros.settings.security.behavior.TAuthorizationBehavior;
import com.tedros.settings.security.process.TAuthorizationProcess;

import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker.State;

/**
 * @author Davis Gordon
 *
 */
public class TAuthorizationLoadAction extends TPresenterAction {

	/**
	 * @param tActionType
	 */
	public TAuthorizationLoadAction() {
		super(TActionType.NEW);
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.control.action.TPresenterAction#runBefore()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean runBefore() {
		TReflections.createAppPackagesIndex();
		List<TAuthorization> authorizations = getAppsSecurityPolicie( 
				TReflections.getInstance()
				.loadPackages()
				.getClassesAnnotatedWith(TSecurity.class));
		
		try {
			TDynaViewSimpleBaseBehavior behavior =  (TDynaViewSimpleBaseBehavior) ((TDynaPresenter)super.getPresenter()).getBehavior();
			TAuthorizationProcess process = new TAuthorizationProcess();
			ChangeListener<State> prcl = (arg0, arg1, arg2) -> {
				
				if(arg2.equals(State.SUCCEEDED)){
					
					List<TResult<TAuthorization>> lst =  process.getValue();
					
					if(lst != null) {
						TResult res = lst.get(0);
						if(res.getResult().equals(EnumResult.SUCESS)) {
							List<String> msg = (List<String>) res.getValue();
							if(!msg.isEmpty()) {
								TMessageBox tMessageBox = new TMessageBox(msg);
								behavior.getView().tShowModal(tMessageBox, true);
							}else{
								TMessageBox tMessageBox = new TMessageBox(res.getMessage());
								behavior.getView().tShowModal(tMessageBox, true);
							}
							if(behavior instanceof TAuthorizationBehavior) {
								TPagination pag = new TPagination(null, "appName", true, 0, 50);
								try {
									((TAuthorizationBehavior)behavior).paginate(pag);
								} catch (TException e) {
									e.printStackTrace();
								}
							}
						}else {
							String msg = res.getMessage();
							System.out.println(msg);
							switch(res.getResult()) {
								case ERROR:
									behavior.addMessage(new TMessage(TMessageType.ERROR, msg));
									break;
								default:
									behavior.addMessage(new TMessage(TMessageType.WARNING, msg));
									break;
							}
						}
					}
				}
			};
			process.stateProperty().addListener(prcl);
			process.process(authorizations);
			behavior.runProcess(process);
			
		} catch (TProcessException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	/**
	 * @param iEngine
	 * @return
	 */
	public static List<TAuthorization> getAppsSecurityPolicie(Set<Class<?>> classes) {

		TLanguage iEngine = TLanguage.getInstance(null);
		List<TAuthorization> authorizations = new ArrayList<>();
		for (Class clazz : classes ) {
			try {
				
				TSecurity tSecurity = (TSecurity) clazz.getAnnotation(TSecurity.class);
					
				for(TAuthorizationType authorizationType : tSecurity.allowedAccesses()){
					
					TAuthorization authorization = new TAuthorization();;
					authorization.setType(authorizationType.name());
					authorization.setSecurityId(tSecurity.id());
					authorization.setEnabled("Sim");
					
					authorization.setAppName(iEngine.getString(tSecurity.appName()));
					
					if(StringUtils.isNotBlank(tSecurity.moduleName()))
						authorization.setModuleName(iEngine.getString(tSecurity.moduleName()));
					
					if(StringUtils.isNotBlank(tSecurity.viewName()))
						authorization.setViewName(iEngine.getString(tSecurity.viewName()));
					
					authorization.setTypeDescription(iEngine.getString(TAuthorizationType.getFromName(authorizationType.name()).getValue()));
					authorizations.add(authorization);
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return authorizations;
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.control.action.TPresenterAction#runAfter()
	 */
	@Override
	public void runAfter() {
		// TODO Auto-generated method stub

	}

}
