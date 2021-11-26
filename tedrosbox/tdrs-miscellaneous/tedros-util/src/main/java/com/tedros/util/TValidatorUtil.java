/**
 * 
 */
package com.tedros.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Davis Gordon
 *
 */
public class TValidatorUtil {
	
	public static boolean isEmailAddress(String email) {
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

}
