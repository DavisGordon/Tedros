package org.tedros.core.ejb.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.core.controller.TProfileChartController;
import org.tedros.core.domain.DomainApp;
import org.tedros.core.ejb.service.TCoreService;
import org.tedros.core.security.model.TProfile;
import org.tedros.core.security.model.TUser;
import org.tedros.server.controller.TParam;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.model.ITChartModel;
import org.tedros.server.model.TChartModel;
import org.tedros.server.model.TChartSerie;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

@TSecurityInterceptor
@Stateless(name="TProfileChartController")
@TBeanSecurity(@TBeanPolicie(id=DomainApp.PROFILE_FORM_ID, 
policie= {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS}))
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TProfileChartControllerImpl extends TSecureEjbController<TProfile> 
implements TProfileChartController, ITSecurity {

	@EJB
	private TCoreService<TProfile> serv;
	
	@EJB
	private TCoreService<TUser> uServ;
	
	@EJB
	private ITSecurityController securityController;

	@Override
	public ITEjbService<TProfile> getService() {
		return serv;
	}

	/**
	 * @return the securityController
	 */
	public ITSecurityController getSecurityController() {
		return securityController;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public  TResult<ITChartModel> process(TAccessToken token, TParam... params) {
		try {
			List<TProfile> p = serv.listAll(TProfile.class);
			List<TUser> u = uServ.listAll(TUser.class);
			
			TChartModel<String, Long> cm = new TChartModel<>();
			TChartSerie<String,Long> s = new TChartSerie<>(null);
			cm.addSerie(s);
			p.stream().forEach(e->{
				long t = u.stream().filter(x->{
					return  x.getProfiles().contains(e);
				}).count();
				s.addData(e.getName(), t);
			});
			return new TResult<>(TState.SUCCESS, cm);
		} catch (Exception e) {
			return super.processException(token, null, e);
		}
	}
	
}
