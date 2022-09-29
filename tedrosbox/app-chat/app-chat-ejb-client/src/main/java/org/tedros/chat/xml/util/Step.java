/**
 * 
 */
package org.tedros.chat.xml.util;

/**
 * @author Davis Gordon
 *
 */
public enum Step {
	
	AUTHENTICATION,
	AUTHENTICATION_RESULT,
	SEND_MSG,
	RECEIVE_MSG;
	
	public static Step get(String name) {
		if(name!=null)
			for(Step e : values())
				if(e.name().equals(name))
					return e;
		return null;
	}

}
