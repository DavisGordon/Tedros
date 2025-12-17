package org.tedros.core.ejb.controller;

import org.apache.commons.lang3.math.NumberUtils;
import org.tedros.common.model.TFileEntity;
import org.tedros.core.controller.TFileEntityController;
import org.tedros.core.controller.TPropertieController;
import org.tedros.core.domain.DomainApp;
import org.tedros.core.domain.TSystemPropertie;
import org.tedros.core.ejb.service.TFileEntityService;
import org.tedros.core.ejb.service.TPropertieService;
import org.tedros.core.ejb.timer.TNotifyTimer;
import org.tedros.core.setting.model.TPropertie;
import org.tedros.core.setting.model.TReportPropertie;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

@TSecurityInterceptor
@Stateless(name="TPropertieController")
@TBeanSecurity(@TBeanPolicie(id=DomainApp.PROPERTIE_FORM_ID, 
policie= {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS}))
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TPropertieControllerImpl extends TSecureEjbController<TPropertie> implements TPropertieController, ITSecurity {

	@EJB
	private TFileEntityService fileService;
	
	@EJB
	private TPropertieService serv;
	
	@EJB
	private TNotifyTimer timer;
	
	@EJB
	private ITSecurityController securityController;

	@Override
	public ITEjbService<TPropertie> getService() {
		return serv;
	}

	/**
	 * @return the securityController
	 */
	public ITSecurityController getSecurityController() {
		return securityController;
	}
	
	@Override
	public TResult<TPropertie> save(TAccessToken token, TPropertie e) {

		if(e.getKey().equals(TSystemPropertie.NOTIFY_INTERVAL_TIMER.getValue()))
			if(e.getValue()!=null && NumberUtils.isCreatable(e.getValue())) {
				timer.start(e.getValue());
			}else
				timer.stop();
		
		return super.save(token, e);
	}

	@Override
	public TResult<String> getValue(TAccessToken token, String key) {
		try {
			String v = serv.getValue(key);
			return new TResult<>(TState.SUCCESS, "", v);
		} catch (Exception e) {
			return super.processException(token, null, e);
		}
	}

	@Override
	public TResult<TFileEntity> getFile(TAccessToken token, String key) {
		try {
			TFileEntity v = serv.getFile(key);
			return new TResult<>(TState.SUCCESS, v);
		} catch (Exception e) {
			return super.processException(token, null, e);
		}
	}

	@Override
	public TResult<TReportPropertie> getReportProperties() {
		try {
			
			String organization = null;
			byte[] logotype = null;
			
			TPropertie e = new TPropertie();
			e.setKey(TSystemPropertie.ORGANIZATION.getValue());
			e = serv.find(e);
			
			organization = e!=null 
					? e.getValue() 
							: null;
			
			e = new TPropertie();
			e.setKey(TSystemPropertie.REPORT_LOGOTYPE.getValue());
			e = serv.find(e);
			
			if(e!=null && e.getFile()!=null) {
				TFileEntity file = e.getFile();
				fileService.loadBytes(file);
				logotype = file.getByteEntity().getBytes();
			}	
			
			
			return new TResult<>(TState.SUCCESS, new TReportPropertie(organization, logotype));
			
			
		}catch (Exception e) {
			return super.processException(null, null, e);
		}
	}	
	
}
