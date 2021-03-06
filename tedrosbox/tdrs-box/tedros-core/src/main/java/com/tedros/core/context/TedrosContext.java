package com.tedros.core.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import com.tedros.core.ITedrosBox;
import com.tedros.core.ModalMessage;
import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.TSecurityDescriptor;
import com.tedros.core.annotation.TApplication;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.security.model.TAuthorization;
import com.tedros.core.security.model.TUser;
import com.tedros.core.style.TStyleResourceValue;
import com.tedros.util.TClassUtil;
import com.tedros.util.TFileUtil;
import com.tedros.util.TedrosClassLoader;
import com.tedros.util.TedrosFolderEnum;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
	
	private static TedrosClassLoader tedrosClassLoader;
	private static ObjectProperty<Page> pageProperty;
	private static StringProperty pagePathProperty;
	private static BooleanProperty showModalMessageProperty;
	private static BooleanProperty showModalProperty;
	private static BooleanProperty reloadStyleProperty;
	private static StringProperty contextStringProperty;
	private static StringProperty initializationErrorMessageStringProperty;
	
	//private static Stage stage;
	private static Node currentView;
	
	private static TUser loggedUser;
	
	private static ModalMessage MODAL_MESSAGE;
	private static Node MODAL;
	
	private static boolean collapseMenu;
	private static boolean PAGE_FORCE;
	private static boolean PAGE_ADDHISTORY; 
	private static boolean PAGE_SWAPVIEWS;
	private static boolean showContextInitializationErrorMessages;
	
	private static Reflections annotationDb;
	
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
					+TedrosFolderEnum.CONF_FOLDER.getFolder()+"language.properties")){
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
		updateMessage("Starting context...");
		collapseMenu = true;
		pageProperty = new SimpleObjectProperty<Page>();
		pagePathProperty = new SimpleStringProperty();
		showModalProperty = new SimpleBooleanProperty();
		showModalMessageProperty = new SimpleBooleanProperty();		
		initializationErrorMessageStringProperty = new SimpleStringProperty("");
		reloadStyleProperty = new SimpleBooleanProperty(true);
		MODAL_MESSAGE = new ModalMessage("");
		
		LOGGER.info("Context started!");
		updateMessage("Context started!");
		
	}
	
	/**
	 * Constructor
	 * */
	private TedrosContext(){
	}	
	
	private static void updateMessage(String message){
		contextStringProperty.set(contextStringProperty.get() + sdf.format(Calendar.getInstance().getTime()) +" "+ message+"\n");
	}
	
	protected static void updateInitializationErrorMessage(String message){
		showContextInitializationErrorMessages = true;
		initializationErrorMessageStringProperty.set(initializationErrorMessageStringProperty.get() + sdf.format(Calendar.getInstance().getTime()) + " " + message+"\n");
	}
	
	/**
	 * Return a {@link Set} of class with this specific annotation type. 
	 * */
	public static Set<Class<?>> getClassesAnnotatedWith(Class<? extends Annotation> annotationClass){
		return annotationDb.getTypesAnnotatedWith(annotationClass);
	}
	
	/**
	 * To be executed once at the start phase, search all class 
	 * with {@link TApplication} that describe an app and their modules 
	 * but none app will be loaded. 
	 * */
	public static void searchApps() {
		
		if(annotationDb!=null)
			return;
		
		LOGGER.info("Starting load application list to context.");
		
		try {
			String[] packages = null;
			Properties p = new Properties();
			try(InputStream input = TedrosContext.class.getClassLoader().getResourceAsStream("app-packages.properties")){
					p.load(input);
					if(p.containsKey("packages"))
						packages = ((String)p.get("packages")).split(",");
			}
			
			
			annotationDb = (packages!=null) 
					? new Reflections(new ConfigurationBuilder()
							.forPackages(packages))
						: new Reflections();

			getClassesAnnotatedWith(TApplication.class)
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
		File folder = new File(TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.CONF_FOLDER.getFolder());
		
		String file;
		File[] listOfFiles = folder.listFiles();
		for(int i = 0; i < listOfFiles.length; i++){
			if(listOfFiles[i].isFile()){
				file = listOfFiles[i].getName();
				if (file.endsWith(".properties")){
					file = TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.CONF_FOLDER.getFolder()+file;
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
	 * @param tedrosFolderEnum
	 * @param fileName
	 * 
	 * @return {@link URL}
	 * */
	public static URL getExternalURLFile(TedrosFolderEnum tedrosFolderEnum, String fileName){
		try {
			String path = TFileUtil.getTedrosFolderPath()+tedrosFolderEnum.getFolder()+fileName;
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
		String path = TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.IMAGES_FOLDER.getFolder()+imageName;
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
		String path = TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.BACKGROUND_IMAGES_FOLDER.getFolder()+imageName;
		try {
			return TClassUtil.getFileInputStream(path);
		} catch (FileNotFoundException e) {
			LOGGER.severe(e.toString());
			return null;
		}
	}
	
	/**
	 * Show a modal with the errors at context initialization.
	 * */
	public static void showContextInitializationErrorMessage(){
		if(showContextInitializationErrorMessages){
			MODAL_MESSAGE = new ModalMessage(initializationErrorMessageStringProperty.get());
			showModalMessageProperty.set(true);
		}
	}
	
	/**
	 * Show a modal with the context message.
	 * */
	public static void showContextMessage(){
		MODAL_MESSAGE = new ModalMessage(contextStringProperty.get());
		showModalMessageProperty.set(true);
	}
	
	/**
	 * Open a modal with the message.
	 * 
	 * @param modalMessage
	 * */
	public static void showMessage(ModalMessage modalMessage){
		MODAL_MESSAGE = modalMessage;
		showModalMessageProperty.set(true);
	}
	
	/**
	 * Open a modal with the Node.
	 * 
	 * @param node
	 * */
	public static void showModal(Node node){
				
		MODAL = node;
		showModalProperty.set(true);
	}
	
	/**
	 * Hide the opened modal.
	 * */
	public static void hideModal(){
		MODAL = null;
		showModalProperty.set(false);
	}
	
	/**
	 * Hide the opened modal with message.
	 * */
	public static void hideMessage(){
		showModalMessageProperty.set(false);
		MODAL_MESSAGE = new ModalMessage("");
	}
	
	/**
	 * Return the {@link ModalMessage}
	 * */
	public static ModalMessage getMessage(){
		return MODAL_MESSAGE;
	}
	
	/**
	 * Return the {@link Node} in the modal
	 * */
	public static Node getModal(){
		return MODAL;
	}
	
	/**
	 * show modal property to listen.
	 * */
	public static ReadOnlyBooleanProperty showModalProperty(){
		return showModalProperty;
	}
	
	/**
	 * show modal message property to listen.
	 * */
	public static ReadOnlyBooleanProperty showModalMessageProperty(){
		return showModalMessageProperty;
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
		currentView = view;	
	}
	
	/**
	 * Get the current view
	 * */
	public static final Node getView(){
		return currentView;
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
		TInternationalizationEngine.reloadBundles();
	}

	public static void openDocument(String path) {
		((Application)main).getHostServices().showDocument(path);
		
	}

	public static void logOut() {
		main.hideApps();
		loggedUser = null;
		TedrosContext.showModal(main.buildLogin());
	}
	
	/**
	 * Stop all services and exit program
	 * */
	public static void exit() {
		TedrosProcess.stopAllServices();
        Platform.exit();
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

	/**
	 * @return the collapseMenu
	 */
	public static boolean isCollapseMenu() {
		return collapseMenu;
	}

	/**
	 * @param collapseMenu the collapseMenu to set
	 */
	public static void setCollapseMenu(boolean collapseMenu) {
		TedrosContext.collapseMenu = collapseMenu;
	}
	
}
