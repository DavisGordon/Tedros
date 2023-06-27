/**
 * 
 */
package org.tedros.core.context;

import org.tedros.core.TLanguage;
import org.tedros.core.model.ITModelView;
import org.tedros.server.model.ITModel;

/**
 * @author Davis Gordon
 *
 */
public class TViewDescriptor implements Comparable<TViewDescriptor> {

	private String title;
	private String description;
	private Class<? extends ITModel> model;
	@SuppressWarnings("rawtypes")
	private Class<? extends ITModelView> modelView;
	private TModuleDescriptor moduleDescriptor;
	private final TSecurityDescriptor securityDescriptor;
	private TLanguage iEngine;

	/**
	 * @param moduleDescriptor
	 * @param title
	 * @param description
	 * @param model
	 * @param modelView
	 * @param securityDescriptor
	 */
	@SuppressWarnings("rawtypes")
	public TViewDescriptor(TModuleDescriptor moduleDescriptor, String title, String description, Class<? extends ITModel> model,
			Class<? extends ITModelView> modelView, TSecurityDescriptor securityDescriptor) {
		super();
		this.iEngine = TLanguage.getInstance();
		this.moduleDescriptor = moduleDescriptor;
		this.title = title;
		this.description = description;
		this.model = model;
		this.modelView = modelView;
		this.securityDescriptor = securityDescriptor;
	}

	@Override
	public int compareTo(TViewDescriptor o) {
		String thisStr = moduleDescriptor.getApplicationName() + " " 
				+ moduleDescriptor.getMenu() + " " + moduleDescriptor.getModuleName()+" "+getTitle();
		String objStr = o.moduleDescriptor.getApplicationName() + " " 
				+ o.moduleDescriptor.getMenu() + " " + o.moduleDescriptor.getModuleName()+" "+o.getTitle();
		return thisStr.compareTo(objStr);
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return iEngine.getString(title);
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return iEngine.getString(description);
	}

	/**
	 * @return the model
	 */
	public Class<? extends ITModel> getModel() {
		return model;
	}

	/**
	 * @return the modelView
	 */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITModelView> getModelView() {
		return modelView;
	}
	
	/**
	 * @return the securityDescriptor
	 */
	public TSecurityDescriptor getSecurityDescriptor() {
		return securityDescriptor;
	}

	public String getPathKeys() {
		return moduleDescriptor.getPathKeys()+"!"+this.title;
	}
	
	public String getPath() {
		return TLanguage.getInstance().getString(getPathKeys());
	}

}
