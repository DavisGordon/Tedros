package org.tedros.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TEncriptUtil {
	     
	    public static String encript(String stringToHash) 
	    {
	        String encriptedString = null;
	        try {
	            // Create MessageDigest instance for MD5
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            //Add password bytes to digest
	            md.update(stringToHash.getBytes());
	            //Get the hash's bytes 
	            byte[] bytes = md.digest();
	            //This bytes[] has bytes in decimal format;
	            //Convert it to hexadecimal format
	            StringBuilder sb = new StringBuilder();
	            for(int i=0; i< bytes.length ;i++)
	            {
	                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	            }
	            //Get complete hashed password in hex format
	            encriptedString = sb.toString();
	        } 
	        catch (NoSuchAlgorithmException e) 
	        {
	        	TLoggerUtil.error(TEncriptUtil.class, e.getMessage(), e);
	        }
	        return encriptedString;
	    }
	     
	}