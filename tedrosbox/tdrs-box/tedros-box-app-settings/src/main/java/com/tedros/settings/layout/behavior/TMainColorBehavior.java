package com.tedros.settings.layout.behavior;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import com.tedros.core.TModule;
import com.tedros.core.context.TedrosAppManager;
import com.tedros.core.context.TedrosContext;
import com.tedros.core.style.TStyleResourceName;
import com.tedros.core.style.TThemeUtil;
import com.tedros.fxapi.control.TComboBoxField;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior;
import com.tedros.fxapi.presenter.view.group.TGroupPresenter;
import com.tedros.fxapi.process.TModelProcess;
import com.tedros.settings.layout.decorator.TMainColorDecorator;
import com.tedros.settings.layout.model.TBackgroundImage;
import com.tedros.settings.layout.model.TBackgroundImageMV;
import com.tedros.settings.layout.model.TMainColor;
import com.tedros.settings.layout.model.TPanelMV;
import com.tedros.settings.layout.style.TedrosStyleUtil;
import com.tedros.settings.layout.model.TMainColorMV;
import com.tedros.settings.layout.template.TemplatePane;
import com.tedros.util.TedrosFolder;

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
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
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
