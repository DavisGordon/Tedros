package org.tedros.util;

import java.net.URL;
import java.net.URLClassLoader;

public class TedrosClassLoader extends URLClassLoader {

	public TedrosClassLoader(URL[] arg0, ClassLoader arg1) {
		super(arg0, arg1);
	}
	
	public TedrosClassLoader(URL[] arg0) {
		super(arg0);		
	}
	
	@Override  
    /** 
     * add ckasspath to the loader. 
     */  
    public void addURL(URL url) {  
        super.addURL(url);  
    }  

}