package com.tedros.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.tedros.core.context.TedrosContext;
import com.tedros.util.TStripTagUtil;

/**
 * The Internationalization engine,  return 
 * the value of a key from the resource bundle of an application 
 * by the language selectd at the login stage.
 * 
 * @author Davis Gordon
 * */
public class TLanguage {
	
	public static final String GLOBAL_UUID = "TGLOBAL";  
	private static final Map<String, Map<String, ResourceBundle>> bundles = new HashMap<>();
	private static TStripTagUtil tStripTagUtil = new TStripTagUtil();
	private static List<ResourceDescriptor> resources = new ArrayList<>();
	
	private String currentUUID;
	
	private static class ResourceDescriptor {
		String appuuid;
		String bundle; 
		ClassLoader classLoader;
		
		public ResourceDescriptor(String appuuid, String bundle, ClassLoader classLoader) {
			this.appuuid = appuuid;
			this.bundle = bundle;
			this.classLoader = classLoader;
		}
		
		@Override
		public boolean equals(Object obj) {
			return EqualsBuilder.reflectionEquals(this, obj);
		}
	}
	
	
	
	private TLanguage(String appUUID) {
		this.currentUUID = appUUID;
		TLanguage.tStripTagUtil = new TStripTagUtil();
				
		validUUID();
	}

	private void validUUID() {
		if(currentUUID == null)
			currentUUID = GLOBAL_UUID;
	}
	
	/**
	 * Return an new instance of the TLanguage.
	 * */
	public  static TLanguage getInstance(){
		return new TLanguage(null);
	}
	
	/**
	 * Return an new instance of the TLanguage 
	 * to the application identified by your unique identification.
	 * */
	public  static TLanguage getInstance(String appUID){
		return new TLanguage(appUID);
	}
	
	/**
	 * Return the locale used by the context
	 * */
	public static Locale getLocale() {
		return TedrosContext.getLocale();
	}
	
	/**
	 * Set the current app unique identification in use.
	 * */
	public void setCurrentUUID(String currentUUID) {
		this.currentUUID = currentUUID;
	}
	
	/**
	 * Return the value of the key
	 * */
	public String getString(String key){
		String value = find(currentUUID, key);
		
		if(key.equals(value)){
			for (String uuid : bundles.keySet()) {
				value = find(uuid, key);
				if(!key.equals(value))
					return value;
			}
		}
		
		return value;
	}
	
	/**
	 * Search  the key between the resource bundles and return the value 
	 * */
	public String find(String uuid, String key){
		
		if(!tStripTagUtil.isTagPresent(key))
			return key;
		
		String[] keys = tStripTagUtil.getTags(key);
		
		if(uuid == null)
			uuid = GLOBAL_UUID;
		
		ResourceBundle resourceBundle = null;
		String value = key;
		StringBuffer v;
		for (String k : keys) {
			v = null;			
			for (String bundle : bundles.get(uuid).keySet()) {
				resourceBundle = bundles.get(uuid).get(bundle);
				if(resourceBundle!=null){
					try{
						v = new StringBuffer();
						v.append(resourceBundle.getString(k));
					}catch(MissingResourceException e){ 
						continue; 
					}
				}
				if(v!=null)
					value = tStripTagUtil.replaceTag(value, k, v.toString());
			}
		}	
		
		
		return value;
	}
	
	/**
	 * Returns a formatted string using the value of the key and arguments. 
	 * */
	public String getFormatedString(String key, Object... args){
		return String.format(getString(key), args);
	}
	
	/**
	 * Adds a resource bundle identified by the app unique identifier 
	 * */
	public static void addResourceBundles(String appUUID, ClassLoader classLoader, String... bundles) {
		for (String bundle : bundles) {
			addResourceBundle(appUUID, bundle, classLoader);
		}
	}
	
	/**
	 * Adds a resource bundle identified by the app unique identifier 
	 * */
	public static void addResourceBundle(String appUUID, String bundle, ClassLoader classLoader) {
		
		if(appUUID==null)
			appUUID = GLOBAL_UUID;
		
		ResourceDescriptor resDescriptor = new ResourceDescriptor(appUUID, bundle, classLoader);
		if(!TLanguage.resources.contains(resDescriptor))
			TLanguage.resources.add(resDescriptor);
		
		if(!bundles.containsKey(appUUID))
			bundles.put(appUUID, buildMap(bundle, ResourceBundle.getBundle(bundle, getLocale(), classLoader)));
		else
			bundles.get(appUUID).put(bundle, ResourceBundle.getBundle(bundle, getLocale(), classLoader));
	}
	
	private static Map<String, ResourceBundle> buildMap(String bundle, ResourceBundle resourceBundle){
		Map<String, ResourceBundle> map = new HashMap<>();
		map.put(bundle, resourceBundle);
		return map;
	}
	
	/**
	 * Reloads the resource bundles  
	 * */
	public static void reloadBundles(){
		bundles.clear();
		for (ResourceDescriptor descriptor : resources) {
			addResourceBundle(descriptor.appuuid, descriptor.bundle, descriptor.classLoader);
		}
	}
	
	/**
	 * Return the resouce bundle associated by the given app unique identifier
	 * */
	public static ResourceBundle getLoadedResourceBundle(String appUUID, String bundle){
		
		if(appUUID==null)
			appUUID = GLOBAL_UUID;
		
		return (bundles.containsKey(appUUID) && bundles.get(appUUID).containsKey(bundle) )  ? bundles.get(appUUID).get(bundle) : null;
		
	}

	
}
