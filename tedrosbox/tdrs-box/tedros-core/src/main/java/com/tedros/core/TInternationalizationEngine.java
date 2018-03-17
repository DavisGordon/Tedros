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

public class TInternationalizationEngine {
	
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
	
	
	
	private TInternationalizationEngine(String appUUID) {
		this.currentUUID = appUUID;
		TInternationalizationEngine.tStripTagUtil = new TStripTagUtil();
				
		validUUID();
	}

	private void validUUID() {
		if(currentUUID == null)
			currentUUID = GLOBAL_UUID;
	}
	
	public  static TInternationalizationEngine getInstance(String appUID){
		return new TInternationalizationEngine(appUID);
	}
	
	public static Locale getLocale() {
		return TedrosContext.getLocale();
	}
	
	public void setCurrentUUID(String currentUUID) {
		this.currentUUID = currentUUID;
	}
	
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
	
	
	public String getFormatedString(String key, Object... args){
		return String.format(getString(key), args);
	}
	

	public static void addResourceBundles(String appUUID, ClassLoader classLoader, String... bundles) {
		for (String bundle : bundles) {
			addResourceBundle(appUUID, bundle, classLoader);
		}
	}
	
	
	public static void addResourceBundle(String appUUID, String bundle, ClassLoader classLoader) {
		
		if(appUUID==null)
			appUUID = GLOBAL_UUID;
		
		ResourceDescriptor resDescriptor = new ResourceDescriptor(appUUID, bundle, classLoader);
		if(!TInternationalizationEngine.resources.contains(resDescriptor))
			TInternationalizationEngine.resources.add(resDescriptor);
		
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
	
	public static void reloadBundles(){
		bundles.clear();
		for (ResourceDescriptor descriptor : resources) {
			addResourceBundle(descriptor.appuuid, descriptor.bundle, descriptor.classLoader);
		}
	}
	
	public static ResourceBundle getLoadedResourceBundle(String appUUID, String bundle){
		
		if(appUUID==null)
			appUUID = GLOBAL_UUID;
		
		return (bundles.containsKey(appUUID) && bundles.get(appUUID).containsKey(bundle) )  ? bundles.get(appUUID).get(bundle) : null;
		
	}

	
}
