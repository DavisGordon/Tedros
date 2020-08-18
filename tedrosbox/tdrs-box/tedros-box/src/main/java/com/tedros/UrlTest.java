package com.tedros;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;

public class UrlTest {

	public static void main(String[] args) {

		
		String p = "C:/Users/Davis Gordon/.tedros/CONF/custom-styles.css";
		try {
			File f = new File(p);
			//URL u = new URL(p);
			
			System.out.println(f.toPath().toUri().toURL().getFile());
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
