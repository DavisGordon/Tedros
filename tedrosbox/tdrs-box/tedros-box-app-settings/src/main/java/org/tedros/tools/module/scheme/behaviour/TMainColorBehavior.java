package org.tedros.tools.module.scheme.behaviour;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import org.tedros.api.form.ITModelForm;
import org.tedros.core.TModule;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.style.TStyleResourceName;
import org.tedros.core.style.TThemeUtil;
import org.tedros.fx.control.TComboBoxField;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior;
import org.tedros.fx.presenter.view.group.TGroupPresenter;
import org.tedros.fx.process.TModelProcess;
import org.tedros.tools.module.scheme.decorator.TMainColorDecorator;
import org.tedros.tools.module.scheme.model.TBackgroundImage;
import org.tedros.tools.module.scheme.model.TBackgroundImageMV;
import org.tedros.tools.module.scheme.model.TMainColor;
import org.tedros.tools.module.scheme.model.TMainColorMV;
import org.tedros.tools.module.scheme.model.TPanelMV;
import org.tedros.tools.module.scheme.style.TedrosStyleUtil;
import org.tedros.tools.module.scheme.template.TemplatePane;
import org.tedros.util.TedrosFolder;

import javafx.application.Platform;

public class TMainColorBehavior extends TDynaViewCrudBaseBehavior<TMainColorMV, TMainColor> {

	@Override
	public boolean runWhenModelProcessSucceeded(TModelProcess<TMainColor> modelProcess) {
		TedrosStyleUtil.applyChanges();
		return true;
	}
	
	public void load(){
		super.load();
		configSaveButton();
		configNewButton();
		setSkipChangeValidation(true);
		setSkipRequiredValidation(true);
		
		newAction();
	}
	

	@SuppressWarnings({ "rawtypes"})
	@Override
	protected void runAfterBuildForm(ITModelForm form) {
		
		
		
		((TMainColorMV)getModelView()).loadSavedValues();
		
		TMainColorDecorator decorator =  (TMainColorDecorator) getPresenter().getDecorator();
		form.tAddAssociatedObject(TemplatePane.class.getSimpleName(), decorator.getTemplate());
		TComboBoxField<String> themes = decorator.getThemes();
		
		File tf = new File(TedrosFolder.THEME_FOLDER.getFullPath());
		if(tf.isDirectory()) {
			for(File f : tf.listFiles()) {
				if(f.isDirectory()) {
					themes.getItems().add(f.getName());
				}
			}
		}
		String ct = TThemeUtil.getCurrentTheme();
		themes.getSelectionModel().select(ct);
		themes.getSelectionModel().selectedItemProperty().addListener((a,o,n)->{
			try {
				String propFilePath = TedrosFolder.CONF_FOLDER.getFullPath()+TStyleResourceName.THEME;
				FileOutputStream fos = new FileOutputStream(propFilePath);
				Properties prop = new Properties();
				prop.setProperty("apply", n);
				prop.store(fos, "current theme");
				fos.close();
				Platform.runLater(() ->{
					TedrosContext.reloadStyle();
					((TMainColorMV)getModelView()).loadSavedValues();
					TGroupPresenter gp = (TGroupPresenter) TedrosAppManager.getInstance()
					.getModuleContext((TModule)TedrosContext.getView()).getCurrentViewContext()
					.getPresenter();
					//(TGroupPresenter) ((ITModule)TedrosContext.getView()).gettPresenter();
					gp.getGroupViewItemList().forEach(i -> {
						if(i.getModelViewClass()==TBackgroundImageMV.class && i.isViewInitialized()) {
							((TDynaPresenter)i.getViewInstance(null).gettPresenter())
							.getBehavior().setModelView(new TBackgroundImageMV(new TBackgroundImage()));
						}else if(i.getModelViewClass()==TPanelMV.class && i.isViewInitialized()) {
							((TPanelMV)((TDynaPresenter)i.getViewInstance(null).gettPresenter())
							.getBehavior().getModelView()).loadSavedValues();
						}
					});
					
				});
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
	}
	
	
	
	@Override
	public void colapseAction() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean processNewEntityBeforeBuildForm(TMainColorMV model) {
		return true;

	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

}
