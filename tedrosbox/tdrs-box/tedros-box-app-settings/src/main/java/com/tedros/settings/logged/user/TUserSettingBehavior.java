package com.tedros.settings.logged.user;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.context.TedrosContext;
import com.tedros.core.security.model.TProfile;
import com.tedros.core.security.model.TUser;
import com.tedros.core.style.TStyleResourceName;
import com.tedros.fxapi.control.TComboBoxField;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.form.ITForm;
import com.tedros.fxapi.form.TBuildFormStatus;
import com.tedros.fxapi.form.TFieldBox;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TSaveViewBehavior;
import com.tedros.fxapi.util.TModelViewUtil;
import com.tedros.settings.security.model.TProfileModelView;
import com.tedros.util.TFileUtil;
import com.tedros.util.TedrosFolderEnum;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;

public class TUserSettingBehavior extends TSaveViewBehavior<TUserSettingModelView, TUser> {

	private TComboBoxField<String> languageComboBox;
	private TComboBoxField<TProfileModelView> profileComboBox;
	
	@Override
	public void load() {
		
		super.load();
		
		iEngine = TInternationalizationEngine.getInstance(null);
		
		super.setSaveAction(new TPresenterAction<TDynaPresenter<TUserSettingModelView>>() {

			@Override
			public boolean runBefore(TDynaPresenter<TUserSettingModelView> presenter) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public void runAfter(TDynaPresenter<TUserSettingModelView> presenter) {
				try {
					loadTedros();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		
		ChangeListener<TBuildFormStatus> chl = (ob, o, n) -> {
			if(n!=null && n.equals(TBuildFormStatus.FINISHED))
				getControls(super.getForm());
		};
		super.getListenerRepository().add("buildForm", chl);
		super.buildFormStatusProperty().addListener(new WeakChangeListener<>(chl));
		super.setShoWMessages(false);
		getView().gettProgressIndicator().setMargin(5);
	}

	@SuppressWarnings("unchecked")
	private void getControls(ITForm form) {
		
		TFieldBox tFieldBox = form.gettFieldBox("language");// password language
		languageComboBox = (TComboBoxField<String>) tFieldBox.gettControl();
		languageComboBox.getSelectionModel().select(TedrosContext.getLocale().getLanguage());
		TFieldBox profileFieldBox = form.gettFieldBox("activeProfile");
		profileComboBox = (TComboBoxField<TProfileModelView>) profileFieldBox.gettControl();
		
		/*UserSettingModelView model = super.getModelView();*/
		TUser model = TedrosContext.getLoggedUser();
		if(model.getProfiles()!=null) {
			List<TProfileModelView> profiles  = new TModelViewUtil<TProfileModelView, TProfile>
			(TProfileModelView.class, TProfile.class, new ArrayList<>(model.getProfiles())).convertToModelViewList();
			profileComboBox.getItems().clear();
			profileComboBox.getItems().addAll(profiles);
			
			TProfileModelView pm = new TProfileModelView(model.getActiveProfile());
			
			profileComboBox.getSelectionModel().select(pm);
		}
	}
	
	@Override
	public void colapseAction() {
	}

	@Override
	public boolean processNewEntityBeforeBuildForm(TUserSettingModelView model) {
		return true;
	}

	@Override
	public void remove() {
	}

	private void loadTedros() throws IOException {
		TUserSettingModelView model = getModelView();
		boolean reload = !this.profileComboBox.getSelectionModel().getSelectedItem()
				.getModel().getId().equals(model.getModel().getActiveProfile().getId());
		
		final String language = this.languageComboBox.getSelectionModel().getSelectedItem();
		if(!TedrosContext.getLocale().getLanguage().equals(language)) {
			saveLanguage(language);
			TedrosContext.setLocale(new Locale(language));
			reload = true;
		}
		TedrosContext.setLoggedUser(model.getModel());
		
		if(reload)
			TedrosContext.getApplication().buildApplicationMenu();
	}
	
	private void saveLanguage(String language) throws IOException{
		String propFilePath = TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.CONF_FOLDER.getFolder()+TStyleResourceName.LANGUAGE;
		FileOutputStream fos = new FileOutputStream(propFilePath);
		Properties prop = new Properties();
		prop.setProperty("language", language);
		prop.store(fos, "custom styles");
	}

}
