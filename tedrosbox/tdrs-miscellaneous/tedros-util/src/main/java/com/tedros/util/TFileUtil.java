package com.tedros.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public final class TFileUtil {

	private TFileUtil() {
	}
	
	public static void saveFile(String content, File file){
        try {
            FileWriter fileWriter = null;
             
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
         
    }
	
	public static String readFile(File file){
	        StringBuilder stringBuffer = new StringBuilder();
	        BufferedReader bufferedReader = null;
	         
	        try {
	 
	            bufferedReader = new BufferedReader(new FileReader(file));
	             
	            String text;
	            while ((text = bufferedReader.readLine()) != null) {
	                stringBuffer.append(text+"\n");
	            }
	 
	        } catch (FileNotFoundException ex) {
	            ex.printStackTrace();
	        } catch (IOException ex) {
	        	ex.printStackTrace();
	        } finally {
	            try {
	                bufferedReader.close();
	            } catch (IOException ex) {
	            	ex.printStackTrace();
	            }
	        } 
	         
	        return stringBuffer.toString();
	}
	
	public static void delete(File file)
	    	throws IOException{
	 
	    	if(file.isDirectory()){
	 
	    		//directory is empty, then delete it
	    		if(file.list().length==0){
	 
	    		   file.delete();
	    		   System.out.println("Directory is deleted : "+ file.getAbsolutePath());
	 
	    		}else{
	 
	    		   //list all the directory contents
	        	   String files[] = file.list();
	 
	        	   for (String temp : files) {
	        	      //construct the file structure
	        	      File fileDelete = new File(file, temp);
	 
	        	      //recursive delete
	        	     delete(fileDelete);
	        	   }
	 
	        	   //check the directory again, if empty then delete it
	        	   if(file.list().length==0){
	           	     file.delete();
	        	     System.out.println("Directory is deleted : " + file.getAbsolutePath());
	        	   }
	    		}
	 
	    	}else{
	    		//if file, then delete it
	    		file.delete();
	    		System.out.println("File is deleted : " + file.getAbsolutePath());
	    	}
	}

	public static String getTedrosFolderPath(){
		return System.getProperty("user.home")+File.separator+".tedros"+File.separator;
	}
	
}
