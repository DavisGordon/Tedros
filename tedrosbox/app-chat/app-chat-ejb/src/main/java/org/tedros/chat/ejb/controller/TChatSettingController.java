/**
 * 
 */
package org.tedros.chat.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;
import org.tedros.chat.domain.DomainApp;
import org.tedros.chat.ejb.controller.IChatSettingController;
import org.tedros.chat.ejb.service.CHATService;
import org.tedros.chat.entity.ChatSetting;

/**
 * The controller bean
 * 
 * @author Davis Dun
 *
 */
@TSecurityInterceptor
@Stateless(name="IChatSettingController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.CHAT_SETTING_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TChatSettingController extends TSecureEjbController<ChatSetting> implements IChatSettingController, ITSecurity  {

	@EJB
	private CHATService<ChatSetting> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<ChatSetting> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}
