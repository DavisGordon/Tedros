package org.tedros.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

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
			ClassLoader loader = TClassUtil.class.getClassLoader();
			TEDROS_CLASS_LOADER = new TedrosClassLoader(new URL[]{TUrlUtil.getURL(url)}, loader);
		}
		return TEDROS_CLASS_LOADER;
	}
	
	public static String abbreviateClassName(String name, int length) {
        if (name.length() <= length) {
            return name;
        }

        String[] parts = name.split("\\.");
        int totalLength = name.length();
        int index = 0;

        // Keep abbreviating each part until the total length is within the desired length
        while (totalLength > length && index < parts.length - 1) {
            if (parts[index].length() > 1) {
                int reduction = parts[index].length() - 1;
                parts[index] = parts[index].substring(0, 1);
                totalLength -= reduction;
            }
            index++;
        }

        // Join the parts back into the abbreviated class name
        StringBuilder abbreviatedName = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) {
                abbreviatedName.append(".");
            }
            abbreviatedName.append(parts[i]);
        }

        // If the length is still greater than the desired length, handle the class name part
        if (abbreviatedName.length() > length) {
            String classNamePart = parts[parts.length - 1];
            int classNamePartLength = length - (abbreviatedName.length() - classNamePart.length());
            abbreviatedName.setLength(length - classNamePart.length());
            abbreviatedName.append(classNamePart.substring(0, classNamePartLength));
        }

        return abbreviatedName.toString();
    }
	
}
