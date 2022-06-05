/**
 * 
 */
package com.tedros.settings.util;

import com.tedros.common.model.TFileEntity;
import com.tedros.core.context.TedrosContext;
import com.tedros.core.domain.TSystemPropertie;
import com.tedros.core.ejb.controller.TPropertieController;
import com.tedros.core.security.model.TUser;
import com.tedros.core.service.remote.ServiceLocator;
import com.tedros.ejb.base.result.TResult;

/**
 * @author Davis Gordon
 *
 */
public final class TOwnerProperties {
	
	private String owner;
	private String organization;
	private TFileEntity logotype;

	public TOwnerProperties() {
		this.owner = this.getValue(TSystemPropertie.OWNER.getValue());
		this.organization = this.getValue(TSystemPropertie.ORGANIZATION.getValue());
		this.logotype = this.getFile(TSystemPropertie.REPORT_LOGOTYPE.getValue());
	}
	
	private String getValue(String key)  {
		String v = null;
		ServiceLocator loc = ServiceLocator.getInstance();
		try {
			TUser user = TedrosContext.getLoggedUser();
			TPropertieController serv = loc.lookup(TPropertieController.JNDI_NAME);
			TResult<String> r = serv.getValue(user.getAccessToken(), key);
			v = r.getValue();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			loc.close();
		}
		
		return v;
	}
	
	private TFileEntity getFile(String key) {
		TFileEntity v = null;
		ServiceLocator loc = ServiceLocator.getInstance();
		try {
			TUser user = TedrosContext.getLoggedUser();
			TPropertieController serv = loc.lookup(TPropertieController.JNDI_NAME);
			TResult<TFileEntity> r = serv.getFile(user.getAccessToken(), key);
			v = r.getValue();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			loc.close();
		}
		
		return v;
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * @return the logotype
	 */
	public TFileEntity getLogotype() {
		return logotype;
	}

}
