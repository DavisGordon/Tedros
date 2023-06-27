/**
 * 
 */
package org.tedros.ai.function.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * @author Davis Gordon
 *
 */
public class ViewCatalog {

	@JsonPropertyDescription("The Tedros system apps menu")
	List<ViewInfo> menu;

	/**
	 * @param systemViewCatalog
	 */
	public ViewCatalog() {
		super();
		this.menu = new ArrayList<>();
	}

	public void add(String app, String mod, String title, String desc, String path, 
			String appAccess, String modAccess, String viewAccess) {
		this.menu.add(new ViewInfo(app, mod, title, desc, path, appAccess, modAccess, viewAccess));
	}

	/**
	 * @return the menu
	 */
	public List<ViewInfo> getMenu() {
		return menu;
	}

	/**
	 * @param menu the menu to set
	 */
	public void setMenu(List<ViewInfo> menu) {
		this.menu = menu;
	}
	

}
