package org.tedros.tools.logged.user;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.security.model.TUser;
import org.tedros.core.style.TStyleResourceName;
import org.tedros.fx.control.action.TPresenterAction;
import org.tedros.fx.presenter.behavior.TActionType;
import org.tedros.fx.presenter.entity.behavior.TSaveViewBehavior;
import org.tedros.util.TFileUtil;
import org.tedros.util.TedrosFolder;

public class TUserSettingBehavior extends TSaveViewBehavior<TUserSettingModelView, TUser> {

	private String language;
	
	@Override
	public void load() {
		
		super.load();
		
		iEngine = TLanguage.getInstance(null);
		
		super.addAction(new TPresenterAction(TActionType.SAVE) {

			@Override
			public boolean runBefore() {
				TUserSettingModelView model = getModelView();
				language = model.getLanguage().getValue();
				return true;
			}

			@Override
			public void runAfter() {
				try {
					loadTedros();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			
		});
		
		super.setShoWMessages(false);
		getView().gettProgressIndicator().setMargin(5);
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
		boolean reload = false;
		TUserSettingModelView model = getModelView();
		if(!TedrosContext.getLocale().getLanguage().equals(language)) {
			saveLanguage(language);
			TedrosContext.setLocale(new Locale(language));
			reload = true;
		}
		TedrosContext.setLoggedUser(model.getModel());
		
		if(reload) {
			TedrosContext.getApplication().buildSettingsPane();
			TedrosContext.getApplication().buildApplicationMenu();
		}
	}
	
	private void saveLanguage(String language) throws IOException{
		String propFilePath = TFileUtil.getTedrosFolderPath()+TedrosFolder.CONF_FOLDER.getFolder()+TStyleResourceName.LANGUAGE;
		FileOutputStream fos = new FileOutputStream(propFilePath);
		Properties prop = new Properties();
		prop.setProperty("language", language);
		prop.store(fos, "custom styles");
	}

}
