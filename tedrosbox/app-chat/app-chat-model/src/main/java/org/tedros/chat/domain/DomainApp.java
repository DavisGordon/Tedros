/**
 * 
 */
package org.tedros.chat.domain;

/**
 * @author Davis Gordon
 *
 */
public interface DomainApp {

	static final String SEPARATOR = "_";
	static final String VIEW = "VIEW";
	static final String MODULE = "MODULE";
	static final String FORM = "FORM";
	
	static final String CHAT_SETTING = "CHAT_SETTING";
	static final String CHAT = "CHAT";
	static final String SEP = SEPARATOR;
	
	static final String MNEMONIC = "CHAT";

	static final String CHAT_SETTING_MODULE_ID = MNEMONIC + SEP + CHAT_SETTING + SEP + MODULE;
	static final String CHAT_SETTING_FORM_ID = MNEMONIC + SEP + CHAT_SETTING + SEP + FORM;
	static final String CHAT_SETTING_VIEW_ID = MNEMONIC + SEP + CHAT_SETTING + SEP + VIEW;
	

	static final String CHAT_MODULE_ID = MNEMONIC + SEP + CHAT + SEP + MODULE;
	static final String CHAT_FORM_ID = MNEMONIC + SEP + CHAT + SEP + FORM;
	static final String CHAT_VIEW_ID = MNEMONIC + SEP + CHAT + SEP + VIEW;
	

}
