/**
 * 
 */
package org.tedros.test.ai.helper;

import java.util.List;

import org.tedros.core.controller.ITLoginController;
import org.tedros.core.security.model.TProfile;
import org.tedros.core.security.model.TUser;
import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.server.entity.TEntity;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.test.serv.ServiceLocator;
import org.tedros.util.TEncriptUtil;

/**
 * @author Davis Gordon
 *
 */
public class ServiceHelper {

	public static TUser user;
	
	static {
		ServiceLocator loc = ServiceLocator.getInstance();
		try {
			ITLoginController lServ = loc.lookup(ITLoginController.JNDI_NAME);
			System.out.println("Logon at server... ");
			TResult<TUser> res = lServ.login("dav", TEncriptUtil.encript("dav"));
			if(res.getState().equals(TState.SUCCESS)) {
				user = res.getValue();
				if(user.getActiveProfile()==null && user.getProfiles()!=null 
						&& !user.getProfiles().isEmpty()) {
					Object[] arr = user.getProfiles().toArray();
					TProfile p = (TProfile) arr[0];
					res = lServ.saveActiveProfile(user.getAccessToken(), p, user.getId());
					user = res.getValue();
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally {
			loc.close();
		}
	}
	

	@SuppressWarnings("unchecked")
	public static <T extends TEntity> List<T> listAll(String jndi, Class<T> type) {
		ServiceLocator loc = ServiceLocator.getInstance();
		try {
			ITSecureEjbController<T> serv = loc.lookup(jndi);
			TResult<List<T>> res = serv.listAll(user.getAccessToken(), type);
			if(res.getState().equals(TState.SUCCESS))
				return res.getValue();
			else
				throw new Exception(res.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally {
			loc.close();
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends TEntity> T find(String jndi, T example) {
		ServiceLocator loc = ServiceLocator.getInstance();
		try {
			ITSecureEjbController<T> serv = loc.lookup(jndi);
			TResult<T> res = serv.find(user.getAccessToken(), example);
			if(res.getState().equals(TState.SUCCESS))
				return res.getValue();
			else
				throw new Exception(res.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally {
			loc.close();
		}
	}

}
