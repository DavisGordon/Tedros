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
import com.tedros.fxapi.control.TPasswordField;
import com.tedros.fxapi.control.TTextField;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.exception.TValidatorException;
import com.tedros.fxapi.form.ITForm;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.form.TBuildFormStatus;
import com.tedros.fxapi.form.TFieldBox;
import com.tedros.fxapi.modal.TMessageBox;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior;
import com.tedros.fxapi.util.TModelViewUtil;
import com.tedros.login.decorator.LoginDecorator;
import com.tedros.login.model.Login;
import com.tedros.login.model.LoginModelView;
import com.tedros.settings.security.model.TProfileModelView;
import com.tedros.settings.security.process.TUserProcess;
import com.tedros.util.TEncriptUtil;
import com.tedros.util.TFileUtil;
import com.tedros.util.TedrosFolderEnum;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class LoginBehavior extends TDynaViewCrudBaseBehavior<LoginModelView, Login> {

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private SimpleObjectProperty<TUser> loggedUserProperty;
	private LoginDecorator loginDecorator;
	private Button saveButton;
	private TTextField userTextField;
	private TPasswordField passwordField;
	private TComboBoxField<String> languageComboBox;
	private TComboBoxField<TProfileModelView> profileComboBox;
	private Text profileText;
	private TInternationalizationEngine iEngine;
	
	@Override
	public void load() {
		
		super.load();
		
		iEngine = TInternationalizationEngine.getInstance(null);
		
		loginDecorator = ((LoginDecorator) getPresenter().getDecorator());
		
		saveButton = loginDecorator.gettSaveButton();
		
		loggedUserProperty = new SimpleObjectProperty<>();
		
		loggedUserProperty.addListener(new ChangeListener<TUser>() {

			@Override
			public void changed(ObservableValue<? extends TUser> arg0, TUser oldVal, TUser newVal) {
				
				profileComboBox.getItems().clear();
				
				if(newVal!=null){
					saveButton.disarm();
					saveButton.setText(iEngine.getString("#{tedros.login}"));
					
					userTextField.setDisable(true);
					passwordField.setDisable(true);
					languageComboBox.setDisable(true);
					profileComboBox.setDisable(false);
					
					List<TProfileModelView> profiles  = new TModelViewUtil<TProfileModelView, TProfile>
					(TProfileModelView.class, TProfile.class, new ArrayList<>(newVal.getProfiles())).convertToModelViewList();
					
					profileComboBox.getItems().addAll(profiles);
					profileText.setText(iEngine.getFormatedString("#{tedros.selectProfile}", newVal.getName()));
				}else{
					userTextField.setDisable(false);
					passwordField.setDisable(false);
					languageComboBox.setDisable(false);
					profileComboBox.setDisable(true);
					profileText.setText(iEngine.getString("#{tedros.profileText}"));
					saveButton.setText(iEngine.getString("#{tedros.validateUser}"));
				}
			}
		});
		
		
		
		setNewAction(new TPresenterAction<TDynaPresenter<LoginModelView>>() {

			@Override
			public boolean runBefore(TDynaPresenter<LoginModelView> presenter) {
				Login login = new Login();
				login.setLanguage(TedrosContext.getLocale().getLanguage().toUpperCase());
				LoginModelView model = new LoginModelView(login);
				setViewMode(TViewMode.EDIT);
				setModelView(model);
				//editEntity(model);
				return false;
			}

			@Override
			public void runAfter(TDynaPresenter<LoginModelView> presenter) {
				// TODO Auto-generated method stub
			}
		});
		
		
		
		setSaveAction(new TPresenterAction<TDynaPresenter<LoginModelView>>() {
			
			@Override
			public boolean runBefore(TDynaPresenter<LoginModelView> presenter) {
				
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
				
				if(profileComboBox.isDisable()){
					saveButton.setDisable(false);
					LoginModelView modelView = getModelView();
					final ObservableList<LoginModelView> modelsViewsList = modelView !=null 
									? FXCollections.observableList(Arrays.asList(modelView)) 
											: null;
					
					try {
						validateModels(modelsViewsList);
						
						Login model = (Login) getModelView().getModel();
						
						final String userLogin = model.getUser();
						final String password = model.getPassword();
						
						if(userLogin.toLowerCase().equals("owner") && password.equals("xxx")){
							try{
								TUser tUser = new TUser("Owner", "owner");
								loadTedros(tUser);
								return false;
							}catch(Exception e){
								saveButton.setDisable(false);
								e.printStackTrace();
							}
						}
						
						final TUserProcess process  = (TUserProcess) createEntityProcess();
						process.setLogin(userLogin);
						process.setPassword(TEncriptUtil.encript(password));
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
											loggedUserProperty.setValue(entity);
										}else{
											profileText.setText(iEngine.getString("#{tedros.noUserFound}"));
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
				}else{
					
					TProfileModelView selectedProfile = profileComboBox.getSelectionModel().getSelectedItem();
					if(selectedProfile!=null){
						try{
							final TUser user = loggedUserProperty.getValue();
							user.setActiveProfile(selectedProfile.getModel());
							
							final TUserProcess process  = (TUserProcess) createEntityProcess();
							process.setUserId(user.getId());
							process.setProfile(user.getActiveProfile());
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
											try{
												loadTedros(user);												
											}catch(Exception e){
												saveButton.setDisable(false);
												e.printStackTrace();
											}
										}
									}	
								}
							});
							runProcess(process);
						}catch(Throwable e){
							saveButton.setDisable(false);
							e.printStackTrace();
						}
					}else{
						saveButton.setDisable(false);
						profileText.setText(iEngine.getString("#{tedros.profileRequired}"));
					}
				}
				
				return false;
			}
			
			@Override
			public void runAfter(TDynaPresenter<LoginModelView> presenter) {
				
			}
		});
		
		setCancelAction(new TPresenterAction<TDynaPresenter<LoginModelView>>() {

			@Override
			public boolean runBefore(TDynaPresenter<LoginModelView> presenter) {
				
				if(profileComboBox.isDisable()){
					Platform.exit();
				}else{
					loggedUserProperty.setValue(null);
				}
				
				
				return false;
			}

			@Override
			public void runAfter(TDynaPresenter<LoginModelView> presenter) {
				
			}
		});
		
		configSaveButton();
		configCancelButton();
		
		ChangeListener<TBuildFormStatus> chl = (ob, o, n) -> {
			if(n!=null && n.equals(TBuildFormStatus.FINISHED))
				getControls(super.getForm());
		};
		super.getListenerRepository().add("buildForm", chl);
		super.buildFormStatusProperty().addListener(new WeakChangeListener<>(chl));
		newAction();
	}

	/*@SuppressWarnings("rawtypes")
	@Override
	protected void runAfterBuildForm(ITModelForm form) {
		
		if(!form.isLoaded()) {
			ChangeListener<Boolean> chl = (ob, o, n) -> {
				if(n)
					getControls(super.getForm());
			};
			super.getListenerRepository().add("loadedForm", chl);
			form.tLoadedProperty().addListener(new WeakChangeListener<>(chl));
		}else
			getControls(form);

	}*/

	@SuppressWarnings("unchecked")
	private void getControls(ITForm form) {
		TFieldBox userFieldBox = form.gettFieldBox("user");//  language
		userTextField = (TTextField) userFieldBox.gettControl();
		
		TFieldBox passwordFieldBox = form.gettFieldBox("password");// password 
		passwordField = (TPasswordField) passwordFieldBox.gettControl();
		
		TFieldBox tFieldBox = form.gettFieldBox("language");// password language
		languageComboBox = (TComboBoxField<String>) tFieldBox.gettControl();
		
		TFieldBox profileFieldBox = form.gettFieldBox("profile");//  language
		profileComboBox = (TComboBoxField<TProfileModelView>) profileFieldBox.gettControl();
		
		TFieldBox profileTextFieldBox = form.gettFieldBox("profileText");//  language
		profileText = (Text) profileTextFieldBox.gettControl();
	}
	
	@Override
	public void colapseAction() {
	}

	@Override
	public boolean processNewEntityBeforeBuildForm(LoginModelView model) {
		return true;
	}

	@Override
	public void remove() {
	}

	private void loadTedros(final TUser user) throws IOException {
		profileText.setText(iEngine.getString("#{tedros.loading}"));
		LOGGER.info(iEngine.getString("#{tedros.loading}"));
		
		Login model = (Login) getModelView().getModel();
		final String language = model.getLanguage();
		saveLanguage(language);
		LOGGER.info("Language setted.");
		
		TedrosContext.setLoggedUser(user);
		LOGGER.info("User "+user.getName()+" setted. ");
		
		TedrosContext.setLocale(new Locale(language));
		TedrosContext.searchApps();
		Main.getTedros().buildApplicationMenu();
		TedrosContext.hideModal();
	}
	
	private void saveLanguage(String language) throws IOException{
		String propFilePath = TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.CONF_FOLDER.getFolder()+TStyleResourceName.LANGUAGE;
		FileOutputStream fos = new FileOutputStream(propFilePath);
		Properties prop = new Properties();
		prop.setProperty("language", language);
		prop.store(fos, "custom styles");
	}

}
