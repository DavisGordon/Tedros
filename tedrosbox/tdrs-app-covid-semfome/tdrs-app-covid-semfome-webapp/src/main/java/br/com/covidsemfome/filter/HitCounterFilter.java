package br.com.covidsemfome.filter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.tedros.util.TResourceUtil;

//@WebFilter("/index.html")
public class HitCounterFilter /*implements Filter */{
    
	private ResourceBundle res;
	

   //@Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
           throws IOException, ServletException {

	   String hitFilePath = res.getString("location");
       File hitFile = new File(hitFilePath);
        
       long currentHit = readHitCounterFromFile(hitFile);

       updateHitCounterFile(++currentHit, hitFile);
         
       chain.doFilter(request, response);
       
   }

  
   private long readHitCounterFromFile(File hitFile) throws NumberFormatException, IOException {
	    if (!hitFile.exists()) {
	        return 0;
	    }
	     
	    try (BufferedReader reader = new BufferedReader(new FileReader(hitFile));) {
	         
	        long hit = Integer.parseInt(reader.readLine());
	         
	        return hit;
	    }  
	}
   
   private void updateHitCounterFile(long hit, File hitFile) throws IOException {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(hitFile));) {
	        writer.write(String.valueOf(hit));
	    }
	}

//@Override
public void init(FilterConfig filterConfig) throws ServletException {
	// TODO Auto-generated method stub
	res = TResourceUtil.getResourceBundle("hit-file");
}


//@Override
public void destroy() {
	// TODO Auto-generated method stub
	res.clearCache();
}
   
   
}