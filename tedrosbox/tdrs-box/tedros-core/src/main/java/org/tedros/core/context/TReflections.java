/**
 * 
 */
package org.tedros.core.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.tedros.core.annotation.TApplication;
import org.tedros.core.model.ITModelView;
import org.tedros.util.TLoggerUtil;
import org.tedros.util.TedrosFolder;

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
		loadPackages();
	}

	/**
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public TReflections loadPackages()  {
		try {
			String propFilePath = TedrosFolder.CONF_FOLDER.getFullPath()+APP_PACKAGES_PROPERTIES;
			TLoggerUtil.info(getClass(), "Searching for the application packages to load configured in the file: "+propFilePath);
			if(!(new File(propFilePath).isFile()))
				TReflections.createAppPackagesIndex();
			
			String[] packages = null;
			Properties p = new Properties();
			try(InputStream input = new FileInputStream(propFilePath)){
					p.load(input);
					if(p.containsKey("packages"))
						packages = ((String)p.get("packages")).split(",");
			}
			if(packages!=null) {
				TLoggerUtil.info(getClass(), "App packages to lookup: "+packages);
				repo =  new Reflections(new ConfigurationBuilder()
						.useParallelExecutor()
						.forPackages(packages)); 
			}else{
				TLoggerUtil.warn(getClass(), "Application packages not configured, "
						+ "for best performance list the name of the root packages of "
						+ "each application separated by a comma in the 'packages' "
						+ "property in the "+propFilePath
						+ " file otherwise the lookup by applications will be performed on all system packages.");
				repo = new Reflections();
			}
			
		} catch (IOException e) {
			TLoggerUtil.error(getClass(), e.getMessage(), e);
		}
				
		return this;
	}
	
	/**
	 * Return a {@link Set} of class with this specific annotation type. 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITModelView> getModelViewClass(String modelViewClassName){
		Optional<Class<? extends ITModelView>> op = repo.getSubTypesOf(ITModelView.class)
				.parallelStream()
				.filter(p->{
					return p.getName().equals(modelViewClassName);
				}).findFirst();
		return op.isPresent() 
				? op.get() 
						: null;
	}
	
	/**
	 * Return a {@link Set} of class with this specific annotation type. 
	 * */
	public Set<Class<?>> getClassesAnnotatedWith(Class<? extends Annotation> annotationClass){
		return repo.getTypesAnnotatedWith(annotationClass);
	}
	
	
	/**
	 * Returns a Set of T sub types
	 * @param type
	 * @return Set<Class<? extends T>>
	 */
	public <T> Set<Class<? extends T>>  getSubTypesOf(Class<T> type){
		Set<Class<? extends T>> clss = repo.getSubTypesOf(type);
		return clss;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
			TLoggerUtil.error(TReflections.class, e.getMessage(), e);
		}
	}

	public static TReflections getInstance() {
		if(instance==null)
			instance = new TReflections();
		return instance;
	}
	
}
