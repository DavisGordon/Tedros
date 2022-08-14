/**
 * 
 */
package org.tedros.fx.control;

import org.tedros.core.TLanguage;
import org.tedros.core.context.TReflections;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.model.ITModelView;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.fx.util.TModelViewUtil;
import org.tedros.fx.util.TReflectionUtil;

import javafx.scene.Node;
import javafx.scene.control.Hyperlink;

/**
 * @author Davis Gordon
 *
 */
public class TIntegratedLink extends Hyperlink {

	private Long tEntityId;
	private String tModulePath;
	private String tEntityClassName;
	private String tModelViewClassName;
	
	/**
	 * @param arg0
	 */
	public TIntegratedLink(String path) {
		super(TLanguage.getInstance().getString(path));
		this.tModulePath = path;
		init();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public TIntegratedLink(String path, Node arg1) {
		super(TLanguage.getInstance().getString(path), arg1);
		this.tModulePath = path;
		init();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void init(){
		super.setOnAction(e->{
			Class<? extends ITModelView> mvCls = TReflections.getInstance().getModelViewClass(tModelViewClassName);
			Class mCls = TReflectionUtil.getGenericParamClass(mvCls, 0);
			
			if(mvCls!=null && mCls!=null) {
				ITModelView mv = TModelViewUtil.buildModelView(mvCls, mCls);
				((TEntityModelView)mv).getId().setValue(tEntityId);
				TedrosAppManager.getInstance().loadInModule(tModulePath, mv);
			}
		});
	}

	/**
	 * @return the tEntityId
	 */
	public Long gettEntityId() {
		return tEntityId;
	}

	/**
	 * @param tEntityId the tEntityId to set
	 */
	public void settEntityId(Long tEntityId) {
		this.tEntityId = tEntityId;
	}

	/**
	 * @return the tModulePath
	 */
	public String gettModulePath() {
		return tModulePath;
	}

	/**
	 * @param tModulePath the tModulePath to set
	 */
	public void settModulePath(String tModulePath) {
		this.tModulePath = tModulePath;
	}

	/**
	 * @return the tEntityClassName
	 */
	public String gettEntityClassName() {
		return tEntityClassName;
	}

	/**
	 * @param tEntityClassName the tEntityClassName to set
	 */
	public void settEntityClassName(String tEntityClassName) {
		this.tEntityClassName = tEntityClassName;
	}

	/**
	 * @return the tModelViewClassName
	 */
	public String gettModelViewClassName() {
		return tModelViewClassName;
	}

	/**
	 * @param tModelViewClassName the tModelViewClassName to set
	 */
	public void settModelViewClassName(String tModelViewClassName) {
		this.tModelViewClassName = tModelViewClassName;
	}

}
