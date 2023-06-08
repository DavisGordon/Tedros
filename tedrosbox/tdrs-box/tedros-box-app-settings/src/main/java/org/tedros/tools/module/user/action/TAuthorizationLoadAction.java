/**
 * 
 */
package org.tedros.tools.module.user.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.TLanguage;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.message.TMessage;
import org.tedros.core.message.TMessageType;
import org.tedros.core.security.model.TAuthorization;
import org.tedros.fx.control.action.TPresenterAction;
import org.tedros.fx.exception.TException;
import org.tedros.fx.exception.TProcessException;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.presenter.behavior.TActionType;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.dynamic.behavior.TDynaViewSimpleBaseBehavior;
import org.tedros.fx.presenter.paginator.TPagination;
import org.tedros.server.query.TSelect;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.tools.module.user.behaviour.TAuthorizationBehavior;
import org.tedros.tools.module.user.process.TAuthorizationProcess;

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
	 * @see org.tedros.fx.control.action.TPresenterAction#runBefore()
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean runBefore() {
		/*TReflections.createAppPackagesIndex();
		List<TAuthorization> authorizations = getAppsSecurityPolicie( 
				TReflections.getInstance()
				.loadPackages()
				.getClassesAnnotatedWith(TSecurity.class));
		*/
		try {
			TDynaViewSimpleBaseBehavior behavior =  (TDynaViewSimpleBaseBehavior) ((TDynaPresenter)super.getPresenter()).getBehavior();
			TAuthorizationProcess process = new TAuthorizationProcess();
			ChangeListener<State> prcl = (arg0, arg1, arg2) -> {
				
				if(arg2.equals(State.SUCCEEDED)){
					
					List<TResult<TAuthorization>> lst =  process.getValue();
					
					if(lst != null) {
						TResult res = lst.get(0);
						if(res.getState().equals(TState.SUCCESS)) {
							List<String> msg = (List<String>) res.getValue();
							if(!msg.isEmpty()) {
								TMessageBox tMessageBox = new TMessageBox(msg, TMessageType.GENERIC);
								behavior.getView().tShowModal(tMessageBox, true);
							}else{
								TMessageBox tMessageBox = new TMessageBox(res.getMessage());
								behavior.getView().tShowModal(tMessageBox, true);
							}
							if(behavior instanceof TAuthorizationBehavior) {
								TPagination pag = new TPagination(null, null, "appName", 
										TSelect.ALIAS, true, 0, 50);
								try {
									((TAuthorizationBehavior)behavior).paginate(pag);
								} catch (TException e) {
									e.printStackTrace();
								}
							}
						}else {
							String msg = res.getMessage();
							System.out.println(msg);
							switch(res.getState()) {
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
			//process.process(authorizations);
			process.savePolicies();
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
	 * @see org.tedros.fx.control.action.TPresenterAction#runAfter()
	 */
	@Override
	public void runAfter() {
		// TODO Auto-generated method stub

	}

}
