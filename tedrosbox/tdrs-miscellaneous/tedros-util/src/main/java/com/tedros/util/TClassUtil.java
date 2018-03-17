package com.tedros.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author Davis Gordon
 * */
public class TClassUtil {
	
	private static TedrosClassLoader TEDROS_CLASS_LOADER;
	
	public static InputStream getFileInputStream(String path) throws FileNotFoundException{
		return new FileInputStream(path);
	}
	
	@SuppressWarnings("rawtypes")
	public static Class loadClass(TedrosClassLoader loader, String packageClassName)throws MalformedURLException, ClassNotFoundException{
		return loader.loadClass(packageClassName);
	}
	
	public static void addFileAtClassPath(TedrosClassLoader loader, String url) throws MalformedURLException{
		loader.addURL(TUrlUtil.getURL(url));
	}


	public static TedrosClassLoader getLoader(String url) throws MalformedURLException {
		if(TEDROS_CLASS_LOADER==null){		
			final URLClassLoader loader = (URLClassLoader) TClassUtil.class.getClassLoader();
			TEDROS_CLASS_LOADER = new TedrosClassLoader(new URL[]{TUrlUtil.getURL(url)}, loader);
		}
		return TEDROS_CLASS_LOADER;
	}

}
