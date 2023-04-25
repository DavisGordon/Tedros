package org.tedros.core.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.tedros.api.presenter.view.ITView;
import org.tedros.core.ITedrosBox;
import org.tedros.core.TLanguage;
import org.tedros.core.TModule;
import org.tedros.core.annotation.TApplication;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.controller.ITLoginController;
import org.tedros.core.controller.TPropertieController;
import org.tedros.core.domain.TSystemPropertie;
import org.tedros.core.message.TMessage;
import org.tedros.core.message.TMessageType;
import org.tedros.core.security.model.TAuthorization;
import org.tedros.core.security.model.TUser;
import org.tedros.core.service.remote.ServiceLocator;
import org.tedros.core.style.TStyleResourceValue;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.util.TClassUtil;
import org.tedros.util.TFileUtil;
import org.tedros.util.TedrosClassLoader;
import org.tedros.util.TedrosFolder;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * Tedros Context
 * 
 * The main context, responsible to loading apps, process, 
 * user, messages, language and security settings. 
 * 
 * @author Davis Gordon
 * */
@SuppressWarnings({ "rawtypes"})
public final class TedrosContext {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private static final String DEFAULT_COUNTRY_ISO2 = "BR";
	private static final int DEFAULT_TOTAL_PAGE_HISTORY = 3;
	private static final int MAX_TOTAL_PAGE_HISTORY = 10;
	
	private static TedrosClassLoader tedrosClassLoader;
	
	private static ObjectProperty<Page> pageProperty;
	private static StringProperty pagePathProperty;
	private static BooleanProperty showModalProperty;
	private static BooleanProperty reloadStyleProperty;
	private static BooleanProperty artificialIntelligenceEnabledProperty;
	private static StringProperty contextStringProperty;
	private static IntegerProperty totalPageHistoryProperty;
	private static StringProperty countryIso2Property;
	private static StringProperty initializationErrorMessageStringProperty;
	private static ObservableList<TMessage> messageListProperty;
	private static ObservableList<TMessage> infoListProperty;
	
	//private static Stage stage;
	private static SimpleObjectProperty<Node> currentViewProperty;
	
	private static TUser loggedUser;
	
	private static Node MODAL;
	
	//private static boolean collapseMenu;
	private static boolean PAGE_FORCE;
	private static boolean PAGE_ADDHISTORY; 
	private static boolean PAGE_SWAPVIEWS;
	private static boolean showContextInitializationErrorMessages;
	
	private static SimpleDateFormat sdf;
	
	private static Locale locale;	
	
	private static ITedrosBox main;
	
	/**
	 * Start the context
	 * */
	static{
		
		LOGGER.setLevel(Level.ALL);
		
		LOGGER.info("Start load properties files to classpath:");
		addPropertiesFilesToClassPath();
		LOGGER.info("Finish load Properties files.");
		
		LOGGER.info("Start load language definition.");
		Properties languageProp = new Properties();
		
		try {
			try(FileInputStream input = new FileInputStream(TFileUtil.getTedrosFolderPath()
					+TedrosFolder.CONF_FOLDER.getFolder()+"language.properties")){
				languageProp.load(input);
				locale = new Locale(languageProp.getProperty("language"));
			}
		}catch(IOException e){
				LOGGER.severe(e.toString());
				locale = Locale.ENGLISH;
		}
		
		LOGGER.info("Finish load language definition.");
		
		sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		contextStringProperty = new SimpleStringProperty("");
		
		LOGGER.info("Starting context...");

		currentViewProperty = new SimpleObjectProperty<>();
		pageProperty = new SimpleObjectProperty<Page>();
		pagePathProperty = new SimpleStringProperty();
		showModalProperty = new SimpleBooleanProperty();	
		initializationErrorMessageStringProperty = new SimpleStringProperty("");
		reloadStyleProperty = new SimpleBooleanProperty(true);
		artificialIntelligenceEnabledProperty = new SimpleBooleanProperty(false);
		totalPageHistoryProperty = new SimpleIntegerProperty(DEFAULT_TOTAL_PAGE_HISTORY);
		countryIso2Property = new SimpleStringProperty(DEFAULT_COUNTRY_ISO2);
		messageListProperty = FXCollections.observableArrayList();
		infoListProperty = FXCollections.observableArrayList();
		
		initializationErrorMessageStringProperty.addListener((a,o,n)->{
			if(showContextInitializationErrorMessages){
				messageListProperty.add(new TMessage(TMessageType.ERROR, n));
			}
		});
		
		contextStringProperty.addListener((a,o,n)->{
			messageListProperty.add(new TMessage(TMessageType.INFO, n));
		});
		
		LOGGER.info("Context started!");
		
	}
	
	/**
	 * Constructor
	 * */
	private TedrosContext(){
	}	
	
	protected static void updateInitializationErrorMessage(String message){
		showContextInitializationErrorMessages = true;
		initializationErrorMessageStringProperty.set(initializationErrorMessageStringProperty.get() + sdf.format(Calendar.getInstance().getTime()) + " " + message+"\n");
	}
	
	public static void loadCustomProperties() {
		
		LOGGER.info("Starting load custom system properties.");
		
		ServiceLocator loc = ServiceLocator.getInstance();
		try {
			TPropertieController serv = loc.lookup(TPropertieController.JNDI_NAME);
			TResult<String> res = serv.getValue(TedrosContext.loggedUser.getAccessToken(), 
					TSystemPropertie.DEFAULT_COUNTRY_ISO2.getValue());
			if(res.getState().equals(TState.SUCCESS) && StringUtils.isNotBlank(res.getValue())) {
				setCountryIso2(res.getValue());
				LOGGER.info("- Propertie "+TSystemPropertie.DEFAULT_COUNTRY_ISO2.getValue()+" loaded.");
			}else{
				setCountryIso2(DEFAULT_COUNTRY_ISO2);
				LOGGER.info("- Propertie "+TSystemPropertie.DEFAULT_COUNTRY_ISO2.getValue()+" set to default value "+DEFAULT_COUNTRY_ISO2);
			}
			res = serv.getValue(TedrosContext.loggedUser.getAccessToken(), 
					TSystemPropertie.TOTAL_PAGE_HISTORY.getValue());
			if(res.getState().equals(TState.SUCCESS) && StringUtils.isNotBlank(res.getValue())) {
				if(NumberUtils.isCreatable(res.getValue())) {
					setTotalPageHistory(Integer.valueOf(res.getValue()));
					LOGGER.info("- Propertie "+TSystemPropertie.TOTAL_PAGE_HISTORY.getValue()+" loaded.");
				}else {
					LOGGER.info("- The Propertie "+TSystemPropertie.DEFAULT_COUNTRY_ISO2.getValue()
					+" not loaded because the value "+res.getValue()+" cant be converted to integer number.");
				}
			}else{
				setTotalPageHistory(DEFAULT_TOTAL_PAGE_HISTORY);
				LOGGER.info("- Propertie "+TSystemPropertie.TOTAL_PAGE_HISTORY.getValue()+" set to default value "+DEFAULT_TOTAL_PAGE_HISTORY);
			}
			res = serv.getValue(TedrosContext.loggedUser.getAccessToken(), 
					TSystemPropertie.AI_ENABLED.getValue());
			if(res.getState().equals(TState.SUCCESS) && StringUtils.isNotBlank(res.getValue())) {
				setArtificialIntelligenceEnabled(BooleanUtils.toBoolean(res.getValue()));
				LOGGER.info("- Propertie "+TSystemPropertie.AI_ENABLED.getValue()+" loaded.");
			}else{
				setArtificialIntelligenceEnabled(false);
				LOGGER.info("- Propertie "+TSystemPropertie.AI_ENABLED.getValue()+" set to default value false");
			}
		} catch (NamingException e) {
			LOGGER.severe("Error loading custom system properties: "+ e.toString());
			e.printStackTrace();
		}finally {
			loc.close();
			LOGGER.info("Finish load custom system properties.");
		}
	}
	
	/**
	 * To be executed once at the start phase, search all class 
	 * with {@link TApplication} that describe an app and their modules 
	 * but none app will be loaded. 
	 * */
	public static void searchApps() {
		
		LOGGER.info("Starting load application list to context.");
		
		try {
			TReflections.getInstance().getClassesAnnotatedWith(TApplication.class)
			.forEach(c -> TedrosAppManager.getInstance().addApplication(c));
		} catch (Exception e1 ) {
			e1.printStackTrace();
			updateInitializationErrorMessage(e1.getMessage());
			LOGGER.severe(e1.toString());
		}
	}

	/**
	 * Check if the logged user are allowed with the TAuthorizationType for the app point TSecurityDescriptor
	 * 
	 * @param securityDescriptor - The descriptor from a point in an app to be checked
	 * @param authorizationTypeToCheck - The authorization type to be checked
	 * 
	 * @return {@link Boolean}
	 * */
	public static synchronized boolean isUserAuthorized(TSecurityDescriptor securityDescriptor, TAuthorizationType authorizationTypeToCheck) {
		boolean flag = false;
		if(loggedUser!=null && loggedUser.getActiveProfile()!=null && loggedUser.getActiveProfile().getAutorizations()!=null){
			for(TAuthorization userAuthorization : loggedUser.getActiveProfile().getAutorizations()){
				if(!userAuthorization.getSecurityId().equals(securityDescriptor.getId()))
					continue;
				for(TAuthorizationType definedType : securityDescriptor.getAllowedAccesses()){
					if(definedType.name().equals(authorizationTypeToCheck.name()) 
							&& userAuthorization.getType().equals(authorizationTypeToCheck.name())){
						flag = true;
					}
				}
			}
		}else
			flag = true;
		return flag;
	}
	
	/**
	 * Return the logged user 
	 * 
	 * @return {@link TUser}
	 */
	public static TUser getLoggedUser() {
		return loggedUser;
	}
	
	/**
	 * Set the user
	 * 
	 * @param loggedUser
	 * */
	public static void setLoggedUser(TUser loggedUser) {
		TedrosContext.loggedUser = loggedUser;
	}
	
	/**
	 * Return a class from the tedros class loader
	 * */
	public static Class loadClass(String classe) throws MalformedURLException, ClassNotFoundException {
		return TClassUtil.loadClass(tedrosClassLoader, classe);
	}
	
	/**
	 * Return the tedros class loader
	 * 
	 * @return {@link TedrosClassLoader}
	 * */
	public static TedrosClassLoader getClassLoader(){
		return tedrosClassLoader;
	}
	
	private static void addPropertiesFilesToClassPath(){
		File folder = new File(TedrosFolder.CONF_FOLDER.getFullPath());
		
		String file;
		File[] listOfFiles = folder.listFiles();
		for(int i = 0; i < listOfFiles.length; i++){
			if(listOfFiles[i].isFile()){
				file = listOfFiles[i].getName();
				if (file.endsWith(".properties")){
					file = TedrosFolder.CONF_FOLDER.getFullPath()+file;
					LOGGER.config("Loading file: "+file);
					try {
						
						if(tedrosClassLoader==null)
							tedrosClassLoader = TClassUtil.getLoader(file);
						else
							TClassUtil.addFileAtClassPath(tedrosClassLoader, file);
						
					} catch (MalformedURLException e) {
						LOGGER.severe(e.toString());
					}
				}
			}
		}
	}
	
	/**
	 * Return an {@link URL} for a file in the Tedros folder at filesystem
	 * @param tedrosFolder
	 * @param fileName
	 * 
	 * @return {@link URL}
	 * */
	public static URL getExternalURLFile(TedrosFolder tedrosFolder, String fileName){
		try {
			String path = TFileUtil.getTedrosFolderPath()+tedrosFolder.getFolder()+fileName;
			return new File(path).toPath().toUri().toURL();//TUrlUtil.getURL(path);
		} catch (MalformedURLException e) {
			LOGGER.severe(e.toString());
			return null;
		}
	}
	
	/**
	 * Return an {@link InputStream} object for an image in the tedros image folder at filesystem
	 * */
	public static InputStream getImageInputStream(String imageName) {
		String path = TFileUtil.getTedrosFolderPath()+TedrosFolder.IMAGES_FOLDER.getFolder()+imageName;
		try {
			return TClassUtil.getFileInputStream(path);
		} catch (FileNotFoundException e) {
			LOGGER.severe(e.toString());
			return null;
		}
	}
	
	/**
	 * Return an {@link InputStream} object for the background image in the tedros image folder at filesystem
	 * */
	public static InputStream getBackGroundImageInputStream(String imageName) {
		String path = TFileUtil.getTedrosFolderPath()+TedrosFolder.BACKGROUND_IMAGES_FOLDER.getFolder()+imageName;
		try {
			return TClassUtil.getFileInputStream(path);
		} catch (FileNotFoundException e) {
			LOGGER.severe(e.toString());
			return null;
		}
	}
	
	/**
	 * Push the message to info pop over
	 * 
	 * @param modalMessage
	 * */
	public static void pushInfo(TMessage message){
		if(StringUtils.isNotBlank(message.getValue()))
			infoListProperty.add(message);
	}

	/**
	 * Open a modal with the message.
	 * 
	 * @param modalMessage
	 * */
	public static void showMessage(TMessage... message){
		messageListProperty.addAll(message);
	}
	
	/**
	 * Open a modal with the Node.
	 * 
	 * @param node
	 * */
	public static void showModal(Node node){
		if(MODAL!=null)
			hideModal();
		MODAL = node;
		showModalProperty.set(true);
	}
	
	/**
	 * Hide the opened modal.
	 * */
	public static void hideModal(){
		if(MODAL instanceof ITView)
        	((ITView)MODAL).gettPresenter().invalidate();
		else if(MODAL instanceof TModule)
			((TModule)MODAL).tStop();
		MODAL = null;
		showModalProperty.set(false);
	}
	
	/**
	 * Return the {@link Node} in the modal
	 * */
	public static Node getModal(){
		return MODAL;
	}
	
	/**
	 * Clear the page history
	 * */
	public static void clearPageHistory() {
		main.clearPageHistory();
	}
	
	/**
	 * Set the total page history.
	 * */
	public static void setTotalPageHistory(int total){
		if(total>=MAX_TOTAL_PAGE_HISTORY ) {
			total = MAX_TOTAL_PAGE_HISTORY;
			LOGGER.info("- Propertie "+TSystemPropertie.TOTAL_PAGE_HISTORY.getValue()
			+" cant be greater than "+MAX_TOTAL_PAGE_HISTORY+" set to default value "+DEFAULT_TOTAL_PAGE_HISTORY);
		}
		totalPageHistoryProperty.setValue(total);;
	}
	
	/**
	 * Set the country iso2.
	 * */
	public static void setCountryIso2(String iso2){
		countryIso2Property.setValue(iso2);
	}
	/**
	 * Set the artificial intelligence status
	 * */
	public static void setArtificialIntelligenceEnabled(boolean status){
		artificialIntelligenceEnabledProperty.setValue(status);;
	}
	
	/**
	 * Get the artificial intelligence status
	 * */
	public static Boolean getArtificialIntelligenceEnabled(){
		return artificialIntelligenceEnabledProperty.get();
	}

	/**
	 * Get the artificial intelligence property
	 * */
	public static ReadOnlyBooleanProperty artificialIntelligenceEnabledProperty(){
		return artificialIntelligenceEnabledProperty;
	}
	/**
	 * Get the total page history.
	 * */
	public static Integer getTotalPageHistory(){
		return totalPageHistoryProperty.get();
	}
	
	/**
	 * Get the country iso2.
	 * */
	public static String getCountryIso2(){
		return countryIso2Property.get();
	}
	
	/**
	 * total page history property to listen.
	 * */
	public static ReadOnlyIntegerProperty totalPageHistoryProperty(){
		return totalPageHistoryProperty;
	}
	
	/**
	 * country iso2 property to listen.
	 * */
	public static ReadOnlyStringProperty countryIso2Property(){
		return countryIso2Property;
	}
	
	/**
	 * show modal property to listen.
	 * */
	public static ReadOnlyBooleanProperty showModalProperty(){
		return showModalProperty;
	}
	
	/**
	 * message list property
	 * */
	public static ObservableList messageListProperty(){
		return messageListProperty;
	}
	
	/**
	 * info list property
	 * */
	public static ObservableList infoListProperty(){
		return infoListProperty;
	}
	
	/**
	 * context string property to listen, used to show context message
	 * */
	public static ReadOnlyStringProperty contextStringProperty(){
		return contextStringProperty;
	}
	
	/**
	 * page property to listen, used to change views
	 * */
	public static ReadOnlyObjectProperty<Page> pageProperty() {
		return pageProperty;
	}
	
	/**
	 * property to listen, used to listen when the tedros css styles are reloaded
	 * */
	public static ReadOnlyBooleanProperty reloadStyleProperty() {
		return reloadStyleProperty;
	}
	
	
	public static void reloadStyle(){
		reloadStyleProperty.set(!reloadStyleProperty.get());
		TStyleResourceValue.loadCustomValues(true);
	}
	
	/**
	 * Set a page to be load
	 * */
	public static void setPageProperty(Page page, boolean addHistory, boolean force, boolean swapViews){
		PAGE_ADDHISTORY = addHistory;
		PAGE_SWAPVIEWS = swapViews;
		PAGE_FORCE = force;
		if(pageProperty.getValue()!=null && pageProperty.getValue()==page)
			pageProperty.set(null);
		pageProperty.set(page);
	}
	
	/**
	 * page path property
	 * */
	public static ReadOnlyStringProperty pagePathProperty() {
		return pagePathProperty;
	}
	
	/**
	 * Set a page path to be load
	 * */
	public static void setPagePathProperty(String pagePath, boolean addHistory, boolean force, boolean swapViews){
		PAGE_ADDHISTORY = addHistory;
		PAGE_SWAPVIEWS = swapViews;
		PAGE_FORCE = force;
		if(StringUtils.equals(pagePathProperty.get(), pagePath))
			pagePathProperty.set("");
		pagePathProperty.set(pagePath);
	}
	
	/**
	 * Return true if was set to the current page need to be reload/built again
	 * */
	public static boolean isPageForce(){
		return PAGE_FORCE;
	}
	
	/**
	 * Return true if was set to the current page need to keep their history
	 * */
	public static boolean isPageAddHistory(){
		return PAGE_ADDHISTORY;
	}
	
	/**
	 * Return true if was set to the current page to be swap
	 * */
	public static boolean isPageSwapViews(){
		return PAGE_SWAPVIEWS;
	}
	
	/**
	 * Set the app main Stage
	 * *//*
	public static void setStage(Stage s){
		stage = s;
	}*/
	
	/**
	 * Get the app main Stage
	 * */
	public static Stage getStage(){
		return main.getStage();
	}
	
	/**
	 * Set the current view
	 * */
	public static final void setView(Node view){
		currentViewProperty.setValue(view);	
	}
	
	/**
	 * Get the current view
	 * */
	public static final Node getView(){
		return currentViewProperty.getValue();
	}
	
	public static final ReadOnlyObjectProperty viewProperty() {
		return currentViewProperty;
	}
	
	
	
	/**
	 * Get the {@link Locale}
	 * */
	public static Locale getLocale(){
		return locale;
	}
	
	/**
	 * Set the {@link Locale} and reload the bundles
	 * */
	public static void setLocale(Locale locale) {
		TedrosContext.locale = locale;
		TLanguage.reloadBundles();
	}

	public static void openDocument(String path) throws Exception {
		//((Application)main).getHostServices().showDocument(path);
		if(path==null)
			throw new IllegalArgumentException("path cannot be null");
		File f = new File(path);
		if(!f.isFile())
			throw new Exception(path+" is not a file!");
		if(!TFileUtil.open(f)) 
			throw new Exception(TLanguage.getInstance(null).getString("#{tedros.fxapi.message.os.not.support.operation}"));
	}

	public static void logOut() {
		logout();
		main.logout();
		loggedUser = null;
		TedrosContext.showModal(main.buildLogin());
	}
	
	/**
	 * Stop all services and exit program
	 * */
	public static void exit() {
		logout();
        Platform.exit();
	}
	
	public static void logout() {
		TedrosAppManager.getInstance().stopAll();
		infoListProperty.clear();
		if(loggedUser!=null && loggedUser.getAccessToken()!=null) {
			ServiceLocator loc = ServiceLocator.getInstance();
			try {
				ITLoginController serv = loc.lookup("ITLoginControllerRemote");
				serv.logout(loggedUser.getAccessToken());
			} catch (NamingException e) {
				e.printStackTrace();
			}finally {
				loc.close();
				loggedUser = null;
			}
		}
	}
	
	/**
	 * @param main the main to set
	 */
	public static void setApplication(ITedrosBox main) {
		TedrosContext.main = main;
	}
	
	public static ITedrosBox getApplication() {
		return main;
	}

	
}
