/**
 * 
 */
package com.tedros.core.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.Properties;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import com.tedros.core.annotation.TApplication;
import com.tedros.util.TedrosFolder;

/**
 * @author Davis Gordon
 *
 */
public final class TReflections {

	public static final String APP_PACKAGES_PROPERTIES = "app-packages.properties";
	private static TReflections instance;
	private  Reflections repo;
	/**
	 * 
	 */
	private TReflections() {
		try {
			String[] packages = null;
			String propFilePath = TedrosFolder.CONF_FOLDER.getFullPath()+APP_PACKAGES_PROPERTIES;
			if(!(new File(propFilePath).isFile()))
				TReflections.createAppPackagesIndex();
			
			Properties p = new Properties();
			try(InputStream input = new FileInputStream(propFilePath)){
					p.load(input);
					if(p.containsKey("packages"))
						packages = ((String)p.get("packages")).split(",");
			}
			
			repo = (packages!=null) 
					? new Reflections(new ConfigurationBuilder()
							.forPackages(packages))
						: new Reflections();
					
					
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}
	
	/**
	 * Return a {@link Set} of class with this specific annotation type. 
	 * */
	public Set<Class<?>> getClassesAnnotatedWith(Class<? extends Annotation> annotationClass){
		return repo.getTypesAnnotatedWith(annotationClass);
	}
	
	@SuppressWarnings("unchecked")
	public static void createAppPackagesIndex() {
		try {
			Reflections ref = new Reflections();
			Set<Class<?>> clss = ref.getTypesAnnotatedWith(TApplication.class);
			String n = "";
			for(Class c : clss) {
				TApplication a = (TApplication) c.getAnnotation(TApplication.class);
				n += "".equals(n) ? a.packageName() : ","+a.packageName();
			}
			
			String propFilePath = TedrosFolder.CONF_FOLDER.getFullPath()+APP_PACKAGES_PROPERTIES;
			FileOutputStream fos = new FileOutputStream(propFilePath);
			Properties prop = new Properties();
			prop.setProperty("packages", n);
			prop.store(fos, "App packages");
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static TReflections getInstance() {
		if(instance==null)
			instance = new TReflections();
		return instance;
	}
	
}
