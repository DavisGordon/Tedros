/**
 * 
 */
package org.tedros.core.ai.model.completion.chat;

/**
 * @author Davis Gordon
 *
 */
public enum TChatRole {
	
    SYSTEM("system"),
    USER("user"),
    ASSISTANT("assistant");

    private final String value;

    private TChatRole(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
    

	public static TChatRole value(String s) {
		for(TChatRole m : values())
			if(m.value().equals(s))
				return m;
		return null;
	}

}
