package com.tedros.settings.security.behavior;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.TLanguage;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
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
import com.tedros.fxapi.presenter.entity.behavior.TMasterCrudViewBehavior;
import com.tedros.fxapi.presenter.paginator.TPagination;
import com.tedros.settings.security.model.TAuthorizationModelView;
import com.tedros.settings.security.process.TAuthorizationProcess;

import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker.State;

@SuppressWarnings({ "rawtypes" })
public class TAuthorizationBehavior
extends TMasterCrudViewBehavior<TAuthorizationModelView, TAuthorization> {
	
	@Override
	public void load() {
		super.load();
	}
		
	@SuppressWarnings("unchecked")
	public void initialize() {
		super.initialize();
			addAction(new TPresenterAction(TActionType.NEW) {
				@Override
				public boolean runBefore() {
					List<TAuthorization> authorizations = new ArrayList<>();
					TLanguage iEngine = TLanguage.getInstance(null);
					for (Class clazz : TedrosContext.getClassesAnnotatedWith(TSecurity.class) ) {
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
					
					try {
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
											getView().tShowModal(tMessageBox, true);
										}else{
											TMessageBox tMessageBox = new TMessageBox(res.getMessage());
											getView().tShowModal(tMessageBox, true);
										}
										TPagination pag = new TPagination(null, "appName", true, 0, 50);
										try {
											paginate(pag);
										} catch (TException e) {
											e.printStackTrace();
										}
									}else {
										String msg = res.getMessage();
										System.out.println(msg);
										switch(res.getResult()) {
											case ERROR:
												addMessage(new TMessage(TMessageType.ERROR, msg));
												break;
											default:
												addMessage(new TMessage(TMessageType.WARNING, msg));
												break;
										}
									}
								}
							}
						};
						process.stateProperty().addListener(prcl);
						process.process(authorizations);
						runProcess(process);
						
					} catch (TProcessException e) {
						e.printStackTrace();
					}
					
					return false;
				}

				@Override
				public void runAfter() {
				}
			});
			
	}
	
	
		
}
