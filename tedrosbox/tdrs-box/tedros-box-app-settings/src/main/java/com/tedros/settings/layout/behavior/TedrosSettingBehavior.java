package com.tedros.settings.layout.behavior;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import com.tedros.core.ITModule;
import com.tedros.core.TModule;
import com.tedros.core.context.TedrosAppManager;
import com.tedros.core.context.TedrosContext;
import com.tedros.core.style.TStyleResourceName;
import com.tedros.core.style.TThemeUtil;
import com.tedros.fxapi.control.TComboBoxField;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.form.TDefaultForm;
import com.tedros.fxapi.form.TProgressIndicatorForm;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior;
import com.tedros.fxapi.presenter.view.group.TGroupPresenter;
import com.tedros.fxapi.presenter.view.group.TGroupView;
import com.tedros.fxapi.process.TModelProcess;
import com.tedros.settings.layout.CustomizarModule;
import com.tedros.settings.layout.TedrosStyle;
import com.tedros.settings.layout.decorator.TedrosSettingDecorator;
import com.tedros.settings.layout.model.BackgroundImageModel;
import com.tedros.settings.layout.model.BackgroundImageModelView;
import com.tedros.settings.layout.model.PainelModel;
import com.tedros.settings.layout.model.PainelModelView;
import com.tedros.util.TedrosFolderEnum;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class TedrosSettingBehavior extends TDynaViewCrudBaseBehavior<PainelModelView, PainelModel> {
	
	@Override
	public boolean runWhenModelProcessSucceeded(TModelProcess<PainelModel> modelProcess) {
		TedrosStyle.applyChanges();
		return true;
	}
	

	@Override
	public void load() {
		
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
		TedrosSettingDecorator decorator = (TedrosSettingDecorator) getPresenter().getDecorator();
		
		@SuppressWarnings("unchecked")
		final TDefaultForm<PainelModelView> defaultForm = (TDefaultForm<PainelModelView>) ((TProgressIndicatorForm)form).gettForm();
		defaultForm.setId(null);
		defaultForm.setAlignment(Pos.TOP_LEFT);
		defaultForm.setPadding(new Insets(0));
		defaultForm.tAddAssociatedObject(ExampleBehavior.class.getSimpleName(), decorator.getExamplePresenter().getBehavior());
		((PainelModelView)getModelView()).loadSavedValues();
		
		TComboBoxField<String> themes = decorator.getThemes();
		
		File tf = new File(TedrosFolderEnum.THEME_FOLDER.getFullPath());
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
				String propFilePath = TedrosFolderEnum.CONF_FOLDER.getFullPath()+TStyleResourceName.THEME;
				FileOutputStream fos = new FileOutputStream(propFilePath);
				Properties prop = new Properties();
				prop.setProperty("apply", n);
				prop.store(fos, "current theme");
				fos.close();
				Platform.runLater(() ->{
					((PainelModelView)getModelView()).loadSavedValues();
				
					TedrosContext.reloadStyle();
					TGroupPresenter gp = (TGroupPresenter) TedrosAppManager.getInstance()
					.getModuleContext((TModule)TedrosContext.getView()).getCurrentViewContext()
					.getPresenter();
					//(TGroupPresenter) ((ITModule)TedrosContext.getView()).gettPresenter();
					gp.getGroupViewItemList().forEach(i -> {
						if(i.getModelViewClass()==BackgroundImageModelView.class && i.isViewInitialized()) {
							
							((TDynaPresenter)i.getViewInstance(null).gettPresenter())
							.getBehavior().setModelView(new BackgroundImageModelView(new BackgroundImageModel()));
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
	public void remove() {
		// TODO Auto-generated method stub

	}


	@Override
	public boolean processNewEntityBeforeBuildForm(PainelModelView model) {
		return true;
	}

}
