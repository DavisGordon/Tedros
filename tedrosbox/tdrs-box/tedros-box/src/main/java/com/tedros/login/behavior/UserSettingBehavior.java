package com.tedros.login.behavior;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Logger;

import com.tedros.Main;
import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.context.TedrosContext;
import com.tedros.core.security.model.TProfile;
import com.tedros.core.security.model.TUser;
import com.tedros.core.style.TStyleResourceName;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.fxapi.control.TComboBoxField;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.exception.TValidatorException;
import com.tedros.fxapi.form.ITForm;
import com.tedros.fxapi.form.TBuildFormStatus;
import com.tedros.fxapi.form.TFieldBox;
import com.tedros.fxapi.modal.TMessageBox;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior;
import com.tedros.fxapi.util.TModelViewUtil;
import com.tedros.login.decorator.UserSettingDecorator;
import com.tedros.login.model.UserSetting;
import com.tedros.login.model.UserSettingModelView;
import com.tedros.settings.security.model.TProfileModelView;
import com.tedros.settings.security.process.TUserProcess;
import com.tedros.util.TFileUtil;
import com.tedros.util.TedrosFolderEnum;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.scene.control.Button;

public class UserSettingBehavior extends TDynaViewCrudBaseBehavior<UserSettingModelView, UserSetting> {

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private SimpleObjectProperty<TUser> loggedUserProperty;
	private UserSettingDecorator decorator;
	private Button saveButton;
	private TComboBoxField<String> languageComboBox;
	private TComboBoxField<TProfileModelView> profileComboBox;
	private TInternationalizationEngine iEngine;
	
	@Override
	public void load() {
		
		super.load();
		
		iEngine = TInternationalizationEngine.getInstance(null);
		
		decorator = ((UserSettingDecorator) getPresenter().getDecorator());
		
		saveButton = decorator.gettSaveButton();
		
		setSaveAction(new TPresenterAction<TDynaPresenter<UserSettingModelView>>() {
			
			@Override
			public boolean runBefore(TDynaPresenter<UserSettingModelView> presenter) {
				
				saveButton.setDisable(true);
				
				final ObservableList<String> mensagens = FXCollections.observableArrayList();
				mensagens.addListener(new ListChangeListener<String>(){
					@Override
					public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {
						final TMessageBox tMessageBox = new TMessageBox();
						tMessageBox.tAddMessage(c.getList());
						getView().tShowModal(tMessageBox, true);
					}
				});
				
				
					saveButton.setDisable(false);
					UserSettingModelView modelView = getModelView();
					final ObservableList<UserSettingModelView> modelsViewsList = modelView !=null 
									? FXCollections.observableList(Arrays.asList(modelView)) 
											: null;
					
					try {
						validateModels(modelsViewsList);
						
						TUser user = (TUser) TedrosContext.getLoggedUser();// modelView.getModel().getUser();
						user.setActiveProfile(modelView.getProfile().getValue().getEntity());
						final TUserProcess process  = (TUserProcess) createEntityProcess();
						process.save(user);
						//process.setPassword(password);
						process.stateProperty().addListener(new ChangeListener<State>() {
							@Override
							public void changed(ObservableValue<? extends State> arg0, State arg1, State arg2) {
								if(arg2.equals(State.SUCCEEDED)){
									List<TResult<TUser>> resultados = (List<TResult<TUser>>) process.getValue();
									if(resultados.isEmpty())
										return;
									TResult<TUser> result = resultados.get(0);
									if(result.getResult().getValue() == EnumResult.ERROR.getValue()){
										System.out.println(result.getMessage());
										mensagens.add(result.getMessage());
									}else{
										TUser entity = result.getValue();
										if(entity!=null){
											try {
												loadTedros(entity);
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}
									}
									
								}	
							}
						});
						runProcess(process);
					} catch (TValidatorException e1) {
						getView().tShowModal(new TMessageBox(e1), true);
					}catch(Exception e){
						getView().tShowModal(new TMessageBox(e), true);
						e.printStackTrace();
					} catch (Throwable e1) {
						getView().tShowModal(new TMessageBox(e1), true);
						e1.printStackTrace();
					}
				
				return false;
			}
			
			@Override
			public void runAfter(TDynaPresenter<UserSettingModelView> presenter) {
				
			}
		});
		
		
		configSaveButton();
		
		ChangeListener<TBuildFormStatus> chl = (ob, o, n) -> {
			if(n!=null && n.equals(TBuildFormStatus.FINISHED))
				getControls(super.getForm());
		};
		super.getListenerRepository().add("buildForm", chl);
		super.buildFormStatusProperty().addListener(new WeakChangeListener<>(chl));
		
		UserSettingModelView model = super.getModels().get(0);
		
		setViewMode(TViewMode.EDIT);
		setModelView(model);
	}

	@SuppressWarnings("unchecked")
	private void getControls(ITForm form) {
		
		TFieldBox tFieldBox = form.gettFieldBox("language");// password language
		languageComboBox = (TComboBoxField<String>) tFieldBox.gettControl();
		languageComboBox.getSelectionModel().select(TedrosContext.getLocale().getLanguage());
		TFieldBox profileFieldBox = form.gettFieldBox("profile");//  language
		profileComboBox = (TComboBoxField<TProfileModelView>) profileFieldBox.gettControl();
		
		/*UserSettingModelView model = super.getModelView();*/
		TUser model = TedrosContext.getLoggedUser();
		List<TProfileModelView> profiles  = new TModelViewUtil<TProfileModelView, TProfile>
		(TProfileModelView.class, TProfile.class, new ArrayList<>(model.getProfiles())).convertToModelViewList();
		
		profileComboBox.getItems().addAll(profiles);
		
		TProfileModelView pm = new TProfileModelView(model.getActiveProfile());
		
		profileComboBox.getSelectionModel().select(pm);
	}
	
	@Override
	public void colapseAction() {
	}

	@Override
	public boolean processNewEntityBeforeBuildForm(UserSettingModelView model) {
		return true;
	}

	@Override
	public void remove() {
	}

	private void loadTedros(final TUser user) throws IOException {
		
		UserSetting model = (UserSetting) getModelView().getModel();
		final String language = model.getLanguage();
		saveLanguage(language);
		LOGGER.info("Language setted.");
		
		TedrosContext.setLoggedUser(user);
		LOGGER.info("User "+user.getName()+" setted. ");
		
		TedrosContext.setLocale(new Locale(language));
		//TedrosContext.searchApps();
		Main.getTedros().buildApplicationMenu();
		//TedrosContext.hideModal();
	}
	
	private void saveLanguage(String language) throws IOException{
		String propFilePath = TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.CONF_FOLDER.getFolder()+TStyleResourceName.LANGUAGE;
		FileOutputStream fos = new FileOutputStream(propFilePath);
		Properties prop = new Properties();
		prop.setProperty("language", language);
		prop.store(fos, "custom styles");
	}

}
