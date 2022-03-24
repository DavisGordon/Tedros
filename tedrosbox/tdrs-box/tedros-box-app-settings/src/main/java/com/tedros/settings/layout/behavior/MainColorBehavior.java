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
import com.tedros.settings.layout.TedrosStyle;
import com.tedros.settings.layout.decorator.MainColorDecorator;
import com.tedros.settings.layout.model.BackgroundImageModel;
import com.tedros.settings.layout.model.BackgroundImageModelView;
import com.tedros.settings.layout.model.MainColor;
import com.tedros.settings.layout.model.PainelModelView;
import com.tedros.settings.layout.model.TMainColorModelView;
import com.tedros.settings.layout.template.TemplatePane;
import com.tedros.util.TedrosFolder;

import javafx.application.Platform;

public class MainColorBehavior extends TDynaViewCrudBaseBehavior<TMainColorModelView, MainColor> {

	@Override
	public boolean runWhenModelProcessSucceeded(TModelProcess<MainColor> modelProcess) {
		TedrosStyle.applyChanges();
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
		
		
		
		((TMainColorModelView)getModelView()).loadSavedValues();
		
		MainColorDecorator decorator =  (MainColorDecorator) getPresenter().getDecorator();
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
					((TMainColorModelView)getModelView()).loadSavedValues();
					TGroupPresenter gp = (TGroupPresenter) TedrosAppManager.getInstance()
					.getModuleContext((TModule)TedrosContext.getView()).getCurrentViewContext()
					.getPresenter();
					//(TGroupPresenter) ((ITModule)TedrosContext.getView()).gettPresenter();
					gp.getGroupViewItemList().forEach(i -> {
						if(i.getModelViewClass()==BackgroundImageModelView.class && i.isViewInitialized()) {
							((TDynaPresenter)i.getViewInstance(null).gettPresenter())
							.getBehavior().setModelView(new BackgroundImageModelView(new BackgroundImageModel()));
						}else if(i.getModelViewClass()==PainelModelView.class && i.isViewInitialized()) {
							((PainelModelView)((TDynaPresenter)i.getViewInstance(null).gettPresenter())
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
	public boolean processNewEntityBeforeBuildForm(TMainColorModelView model) {
		return true;

	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

}
