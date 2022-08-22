/**
 * 
 */
package org.tedros.core.context;

import org.tedros.core.ITModule;
import org.tedros.core.annotation.TLoadable;
import org.tedros.core.model.ITModelView;
import org.tedros.core.model.TModelViewUtil;
import org.tedros.server.model.ITModel;

/**
 * @author Davis Gordon
 *
 */
public final class TLoader {
	
	private boolean loadable;
	private String message;
	private Class<? extends ITModel> modelType;
	@SuppressWarnings("rawtypes")
	private Class<? extends ITModelView> modelViewType;
	private Class<? extends ITModule> moduleType;
	private ITModel model;
	@SuppressWarnings("rawtypes")
	private ITModelView modelView;
	
	/**
	 * @param loadable
	 * @param message
	 * @param modelType
	 * @param modelViewType
	 * @param moduleType
	 * @param model
	 */
	@SuppressWarnings("rawtypes")
	TLoader(Class<? extends ITModel> modelType,
			Class<? extends ITModelView> modelViewType, Class<? extends ITModule> moduleType, 
			ITModel model, ITModelView modelView) {
		this.modelType = modelType;
		this.modelViewType = modelViewType;
		this.moduleType = moduleType;
		this.model = model;
		this.modelView = modelView;
		this.loadable = true;
		if(this.modelType==null || this.modelViewType==null || this.moduleType==null) {
			this.loadable = false;
			this.message = "The loader cannot found loadable settings for "
			+( (model!=null) ? " model "+model.toString()+" ["+this.model.getClass().getSimpleName()+"], " : "")
			+( (modelView!=null) ? " modelView "+modelView.toString()+" ["+this.modelView.getClass().getSimpleName()+"], " : "")
			+( (modelType!=null) ? " modelType "+this.modelType.getClass().getSimpleName()+", " : "")
			+( (modelViewType!=null) ? " modelViewType "+this.modelViewType.getClass().getSimpleName()+", " : "")
			+( (moduleType!=null) ? " moduleType "+this.moduleType.getClass().getSimpleName()+", " : "")
			+"see "+TLoadable.class.getSimpleName();
		}
		
	}
	/**
	 * 
	 */
	private void validate() {
		if(!this.isLoadable())
			throw new RuntimeException(message);
	}
	
	@SuppressWarnings("rawtypes")
	public void loadInModule() {
		validate();
		ITModelView target = this.modelView==null 
				? this.loadModelView()
					: this.modelView;
		TedrosAppManager.getInstance().loadInModule(moduleType, target);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T extends ITModelView> T loadModelView() {
		validate();
		try {
			if(model==null)
				model = modelType.newInstance();
			TModelViewUtil mvu = new TModelViewUtil(modelViewType, modelType, model);
			T target = (T) mvu.convertToModelView();
			return target;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @return the modelViewType
	 */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITModelView> getModelViewType() {
		return modelViewType;
	}
	/**
	 * @return the moduleType
	 */
	public Class<? extends ITModule> getModuleType() {
		return moduleType;
	}
	/**
	 * @return the model
	 */
	public ITModel getModel() {
		return model;
	}

	/**
	 * @return the loadable
	 */
	public boolean isLoadable() {
		return loadable;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the modelType
	 */
	public Class<? extends ITModel> getModelType() {
		return modelType;
	}

	/**
	 * @return the modelView
	 */
	@SuppressWarnings("rawtypes")
	public ITModelView getModelView() {
		return modelView;
	}
	
	

}
