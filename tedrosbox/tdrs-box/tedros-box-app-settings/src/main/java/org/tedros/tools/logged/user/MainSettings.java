/**
 * 
 */
package org.tedros.tools.logged.user;

import java.util.Date;
import java.util.List;

import org.tedros.server.entity.ITEntity;

/**
 * @author Davis Gordon
 *
 */
public class MainSettings implements ITEntity {

	private static final long serialVersionUID = -6136496020963798031L;
	
	private Long id;
	
	private String clearHistory;

	private String logout;
	
	private boolean collapseMenu;
	
	
	/**
	 * 
	 */
	public MainSettings() {
		// TODO Auto-generated constructor stub
	}


	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	@Override
	public boolean isNew() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer getVersionNum() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVersionNum(Integer versionNum) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Date getLastUpdate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastUpdate(Date lastUpdate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Date getInsertDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInsertDate(Date insertDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getOrderBy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addOrderBy(String fieldName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOrderBy(List<String> orders) {
		// TODO Auto-generated method stub
		
	}


	/**
	 * @return the logout
	 */
	public String getLogout() {
		return logout;
	}


	/**
	 * @param logout the logout to set
	 */
	public void setLogout(String logout) {
		this.logout = logout;
	}


	/**
	 * @return the collapseMenu
	 */
	public boolean isCollapseMenu() {
		return collapseMenu;
	}


	/**
	 * @param collapseMenu the collapseMenu to set
	 */
	public void setCollapseMenu(boolean collapseMenu) {
		this.collapseMenu = collapseMenu;
	}


	/**
	 * @return the clearHistory
	 */
	public String getClearHistory() {
		return clearHistory;
	}


	/**
	 * @param clearHistory the clearHistory to set
	 */
	public void setClearHistory(String clearHistory) {
		this.clearHistory = clearHistory;
	}


}
