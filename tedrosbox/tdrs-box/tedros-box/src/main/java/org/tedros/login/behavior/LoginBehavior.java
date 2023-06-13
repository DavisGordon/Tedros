package org.tedros.login.behavior;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Logger;

import org.tedros.TedrosBox;
import org.tedros.api.form.ITFieldBox;
import org.tedros.api.form.ITForm;
import org.tedros.api.presenter.view.TViewMode;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.message.TMessage;
import org.tedros.core.message.TMessageType;
import org.tedros.core.model.TModelViewUtil;
import org.tedros.core.security.model.TProfile;
import org.tedros.core.security.model.TUser;
import org.tedros.core.style.TStyleResourceName;
import org.tedros.core.style.TThemeUtil;
import org.tedros.fx.control.TComboBoxField;
import org.tedros.fx.control.TPasswordField;
import org.tedros.fx.control.TTextField;
import org.tedros.fx.control.TVRadioGroup;
import org.tedros.fx.control.action.TPresenterAction;
import org.tedros.fx.exception.TValidatorException;
import org.tedros.fx.form.TBuildFormStatus;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.presenter.behavior.TActionType;
import org.tedros.fx.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior;
import org.tedros.login.decorator.LoginDecorator;
import org.tedros.login.model.Login;
import org.tedros.login.model.LoginModelView;
import org.tedros.login.model.TLoginProcess;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.tools.module.user.model.TProfileMV;
import org.tedros.util.TEncriptUtil;
import org.tedros.util.TFileUtil;
import org.tedros.util.TedrosFolder;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class LoginBehavior extends TDynaViewCrudBaseBehavior<LoginModelView, Login> {

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private SimpleObjectProperty<TUser> loggedUserProperty;
	private SimpleBooleanProperty serverOkProperty;
	private SimpleBooleanProperty sysWithoutUserProperty;
	private LoginDecorator loginDecorator;
	private Button saveButton;
	private TTextField userTextField;
	private TPasswordField passwordField;
	private TTextField nameField;
	private TVRadioGroup languageField;
	private TComboBoxField<TProfileMV> profileComboBox;
	private Text profileText;
	private TLanguage iEngine;
	private TTextField ipTextField;
	private TTextField urlTextField;

	private TComboBoxField<String> themeComboBox;
	
	@Override
	public void load() {
		super.load();
		iEngine = TLanguage.getInstance(null);
		loginDecorator = ((LoginDecorator) getPresenter().getDecorator());
		saveButton = loginDecorator.gettSaveButton();
		loggedUserProperty = new SimpleObjectProperty<>();
		sysWithoutUserProperty = new SimpleBooleanProperty(false);
		serverOkProperty = new SimpleBooleanProperty(true);
		
		ChangeListener<Boolean> sOkChl = (a,o,n)->{
			//nameField.setDisable(!n);
			userTextField.setDisable(!n);
			passwordField.setDisable(!n);
			profileComboBox.setDisable(!n);
			loginDecorator.gettSaveButton().setDisable(!n);
			String msg = (!n) 
					? iEngine.getString("#{tedros.server.out.text}") 
							: (this.sysWithoutUserProperty.getValue()) 
							? iEngine.getString("#{tedros.sys.without.user}")
								: iEngine.getString("#{tedros.profileText}");
			this.profileText.setText(msg);
			Region f = (Region) super.getForm();
			f.layout();
		};
		super.getListenerRepository().add("sOkChl", sOkChl);
		this.serverOkProperty.addListener(new WeakChangeListener<>(sOkChl));
		
		
		ChangeListener<Boolean> swouChl = (a,o,n)->{
			nameField.setDisable(!n);
			String msg = (n) 
							? iEngine.getString("#{tedros.sys.without.user}")
								: iEngine.getString("#{tedros.profileText}");
			this.profileText.setText(msg);
			Region f = (Region) super.getForm();
			f.layout();
		};
		super.getListenerRepository().add("swouChl", swouChl);
		this.sysWithoutUserProperty.addListener(new WeakChangeListener<>(swouChl));
		
		ChangeListener<TUser> usrChl = (a, oldVal, newVal)-> {
				
				profileComboBox.getItems().clear();
				
				if(newVal!=null){
					saveButton.disarm();
					saveButton.setText(iEngine.getString("#{tedros.login}"));
					nameField.setText(newVal.getName());
					nameField.setDisable(true);
					userTextField.setDisable(true);
					passwordField.setDisable(true);
					languageField.setDisable(true);
					ipTextField.setDisable(true);
					urlTextField.setDisable(true);
					profileComboBox.setDisable(false);
					
					
					List<TProfileMV> profiles  = new TModelViewUtil<TProfileMV, TProfile>
					(TProfileMV.class, TProfile.class, new ArrayList<>(newVal.getProfiles())).convertToModelViewList();
					
					profileComboBox.getItems().addAll(profiles);
					profileText.setText(iEngine.getFormatedString("#{tedros.selectProfile}", newVal.getName()));
					Region f = (Region) super.getForm();
					f.layout();
				}else{
					userTextField.setDisable(false);
					passwordField.setDisable(false);
					languageField.setDisable(false);
					ipTextField.setDisable(false);
					urlTextField.setDisable(false);
					profileComboBox.setDisable(true);
					profileText.setText(iEngine.getString("#{tedros.profileText}"));
					saveButton.setText(iEngine.getString("#{tedros.validateUser}"));
					Region f = (Region) super.getForm();
					f.layout();
				}
			};
		super.getListenerRepository().add("usrChl", usrChl);
		loggedUserProperty.addListener(new WeakChangeListener<>(usrChl));
		
		addAction(new TPresenterAction(TActionType.NEW) {

			@Override
			public boolean runBefore() {
				Login login = new Login();
				login.setLanguage(TedrosContext.getLocale().getLanguage().toLowerCase());
				LoginModelView model = new LoginModelView(login);
				setViewMode(TViewMode.EDIT);
				setModelView(model);
				//editEntity(model);
				return false;
			}

			@Override
			public void runAfter() {
			}
		});
		
		addAction(new TPresenterAction(TActionType.SAVE) {
			
			@Override
			public boolean runBefore() {
				
				saveButton.setDisable(true);
				
				if(profileComboBox.isDisable()){
					saveButton.setDisable(false);
					LoginModelView modelView = getModelView();
					final ObservableList<LoginModelView> modelsViewsList = modelView !=null 
									? FXCollections.observableList(Arrays.asList(modelView)) 
											: null;
					
					try {
						validateModels(modelsViewsList);
						
						Login model = (Login) getModelView().getModel();
						final String name = model.getName();
						final String userLogin = model.getUser();
						final String password = model.getPassword();
						
						TUser u = new TUser();
						u.setName(name);
						u.setLogin(userLogin);
						u.setPassword(TEncriptUtil.encript(password));
						
						final TLoginProcess process  = (TLoginProcess) createEntityProcess();
						if(sysWithoutUserProperty.getValue())
							process.firstUser(u);
						else
							process.login(u);
						
						process.stateProperty().addListener(new ChangeListener<State>() {
							@Override
							public void changed(ObservableValue<? extends State> arg0, State arg1, State arg2) {
								if(arg2.equals(State.SUCCEEDED)){
									List<TResult<TUser>> resultados = (List<TResult<TUser>>) process.getValue();
									if(resultados.isEmpty())
										return;
									TResult<TUser> result = resultados.get(0);
									if(result.getState().getValue() == TState.ERROR.getValue()){
										System.out.println(result.getMessage());
										addMessage(new TMessage(TMessageType.ERROR, result.getMessage()));
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
					
					TProfileMV selectedProfile = profileComboBox.getSelectionModel().getSelectedItem();
					if(selectedProfile!=null){
						try{
							final TUser user = loggedUserProperty.getValue();
							user.setActiveProfile(selectedProfile.getModel());
							
							final TLoginProcess process  = (TLoginProcess) createEntityProcess();
							process.setActiveProfile(user);
							process.stateProperty().addListener(new ChangeListener<State>() {
								@Override
								public void changed(ObservableValue<? extends State> arg0, State arg1, State arg2) {
									if(arg2.equals(State.SUCCEEDED)){
										List<TResult<TUser>> resultados = (List<TResult<TUser>>) process.getValue();
										if(resultados.isEmpty())
											return;
										
										TResult<TUser> result = resultados.get(0);
			
										if(result.getState().getValue() == TState.ERROR.getValue()){
											System.out.println(result.getMessage());
											addMessage(new TMessage(TMessageType.ERROR, result.getMessage()));
										}else{
											try{
												loadTedros(result.getValue());												
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
			public void runAfter() {
				
			}
		});
		
		addAction(new TPresenterAction(TActionType.CANCEL) {
			@Override
			public boolean runBefore() {
				if(profileComboBox.isDisable()){
					Platform.exit();
				}else{
					loggedUserProperty.setValue(null);
				}
				return false;
			}

			@Override
			public void runAfter() {
				
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

	/**
	 * 
	 */
	@SuppressWarnings("incomplete-switch")
	private void verifySysUsers() {
		try {
			final TLoginProcess process  = (TLoginProcess) createEntityProcess();
			process.isSystemWithoutUsers();
			ChangeListener<State> swouProcChl = (a,o,n)->{
				if(n.equals(State.SUCCEEDED)) {
					List<TResult<TUser>> lst = process.getValue();
					TResult<TUser> res = lst.get(0);
					switch(res.getState()) {
					case ERROR:
						this.sysWithoutUserProperty.setValue(false);
						if(res.getMessage().equals("SERVER_FAIL"))
							this.serverOkProperty.setValue(false);
						else
							addMessage(new TMessage(TMessageType.ERROR, res.getMessage()));
						break;
					case SUCCESS:
						this.sysWithoutUserProperty.setValue(true);
						this.serverOkProperty.setValue(true);
						break;
					case WARNING:
						this.sysWithoutUserProperty.setValue(false);
						this.serverOkProperty.setValue(true);
						break;
					}
					super.getListenerRepository().remove("swouProcChl");
				}
			};
			super.getListenerRepository().add("swouProcChl", swouProcChl);
			process.stateProperty().addListener(new WeakChangeListener<>(swouProcChl));
			super.runProcess(process);
		} catch (Throwable e2) {
			e2.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void getControls(ITForm form) {
		ITFieldBox nameFieldBox = form.gettFieldBox("name");//  name
		nameField = (TTextField) nameFieldBox.gettControl();
		nameField.setDisable(true);
		
		EventHandler<ActionEvent> ev1 = e ->{
			super.saveAction();
		};
		super.getListenerRepository().add("valUserPassEvh", ev1);
		
		ITFieldBox userFieldBox = form.gettFieldBox("user");//  user
		userTextField = (TTextField) userFieldBox.gettControl();
		userTextField.setOnAction(new WeakEventHandler<>(ev1));
		
		ITFieldBox passwordFieldBox = form.gettFieldBox("password");// password 
		passwordField = (TPasswordField) passwordFieldBox.gettControl();	
		passwordField.setOnAction(new WeakEventHandler<>(ev1));
		
		ITFieldBox tFieldBox = form.gettFieldBox("language");// password language
		languageField = (TVRadioGroup) tFieldBox.gettControl();
		
		ITFieldBox profileFieldBox = form.gettFieldBox("profile");//  language
		profileComboBox = (TComboBoxField<TProfileMV>) profileFieldBox.gettControl();
		
		ITFieldBox profileTextFieldBox = form.gettFieldBox("profileText");//  language
		profileText = (Text) profileTextFieldBox.gettControl();
		
		ITFieldBox ipTextFieldBox = form.gettFieldBox("serverIp");//  ip
		ipTextField = (TTextField) ipTextFieldBox.gettControl();

		ITFieldBox urlTextFieldBox = form.gettFieldBox("url");//  url
		urlTextField = (TTextField) urlTextFieldBox.gettControl();
		
		ITFieldBox themeFieldBox = form.gettFieldBox("theme");//  theme
		themeComboBox = (TComboBoxField<String>) themeFieldBox.gettControl();
		this.buildConfigEvents();
		verifySysUsers();
	}
	
	private void buildConfigEvents() {
		ChangeListener<Toggle> lanChl = (a,o,n)->{
			try {
				final String language = (String) n.getUserData();
				saveLanguage(language);
				LOGGER.info("Language setted.");
				TedrosContext.setLocale(new Locale(language));
				TedrosContext.logOut();
			} catch (IOException e1) {
				e1.printStackTrace();
				super.addMessage(new TMessage(TMessageType.ERROR, e1.getMessage()));
			}
			
		};
		super.getListenerRepository().add("lanChl", lanChl);
		languageField.selectedToggleProperty().addListener(new WeakChangeListener<>(lanChl));
		
		EventHandler<ActionEvent> ev = e ->{
			saveRemoteConfig();
		};
		super.getListenerRepository().add("ipevh", ev);
		ipTextField.setOnAction(new WeakEventHandler<>(ev));
		
		EventHandler<ActionEvent> ev1 = e ->{
			saveRemoteConfig();
		};
		super.getListenerRepository().add("urlevh", ev1);
		urlTextField.setOnAction(new WeakEventHandler<>(ev1));
		
		File tf = new File(TedrosFolder.THEME_FOLDER.getFullPath());
		if(tf.isDirectory()) {
			for(File f : tf.listFiles()) {
				if(f.isDirectory()) {
					themeComboBox.getItems().add(f.getName());
				}
			}
		}
		String ct = TThemeUtil.getCurrentTheme();
		themeComboBox.getSelectionModel().select(ct);
		themeComboBox.getSelectionModel().selectedItemProperty().addListener((a,o,n)->{
			try {
				String propFilePath = TedrosFolder.CONF_FOLDER.getFullPath()+TStyleResourceName.THEME;
				FileOutputStream fos = new FileOutputStream(propFilePath);
				Properties prop = new Properties();
				prop.setProperty("apply", n);
				prop.store(fos, "current theme");
				fos.close();
				Platform.runLater(() ->{
					TedrosContext.reloadStyle();
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
	}

	/**
	 * 
	 */
	protected void saveRemoteConfig() {
		try {
			
			Login m = (Login) super.getModelView().getModel();
			String propFilePath = TedrosFolder.CONF_FOLDER.getFullPath()+"remote-config.properties";
			FileOutputStream fos = new FileOutputStream(propFilePath);
			Properties prop = new Properties();
			prop.setProperty("url", m.getUrl());
			prop.setProperty("server_ip", m.getServerIp());
			prop.store(fos, "Url for lookup jndi");
			fos.close();
			
			this.verifySysUsers();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
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
		TedrosContext.setLoggedUser(user);
		LOGGER.info("ChatUser "+user.getName()+" setted. ");
		TedrosContext.loadCustomProperties();
		TedrosContext.searchApps();
		TedrosBox.getTedros().buildSettingsPane();
		TedrosBox.getTedros().buildApplicationMenu();
		
		TedrosContext.hideModal();
	}
	
	private void saveLanguage(String language) throws IOException{
		String propFilePath = TFileUtil.getTedrosFolderPath()+TedrosFolder.CONF_FOLDER.getFolder()+TStyleResourceName.LANGUAGE;
		FileOutputStream fos = new FileOutputStream(propFilePath);
		Properties prop = new Properties();
		prop.setProperty("language", language);
		prop.store(fos, "custom styles");
	}

}
