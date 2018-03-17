package com.tedros.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public final class TUrlUtil {

	private TUrlUtil(){
		
	}
	//"https://www.google.com.br/"
	/**
	 * Open the url string at default browser.
	 * */
	public static final void openUrlAtDefaultBrowser(String url) throws URISyntaxException, IOException{
		URI u = new URI(url);
		java.awt.Desktop.getDesktop().browse(u);
	}
	
	/**
	 * Return an URL
	 * */
	public static URL getURL(String path) throws MalformedURLException{
		return new URL("file:"+path); 
	}
	
	/**
	 * Return an URI
	 * */
	public static URI getURI(String url) throws URISyntaxException{
		return new URI(url); 
	}
}
