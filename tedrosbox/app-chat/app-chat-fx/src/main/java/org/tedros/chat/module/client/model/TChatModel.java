/**
 * 
 */
package org.tedros.chat.module.client.model;

import java.util.List;

import org.tedros.core.security.model.TUser;
import org.tedros.server.model.ITModel;

/**
 * @author Davis Gordon
 *
 */
public class TChatModel implements ITModel {

	private static final long serialVersionUID = 1L;
	
	private List<TUser> users;

	/**
	 * 
	 */
	public TChatModel() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the users
	 */
	public List<TUser> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<TUser> users) {
		this.users = users;
	}

}
