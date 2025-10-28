/**
 * 
 */
package org.tedros.core.domain;

/**
 * @author Davis Gordon
 *
 */
public interface DomainApp {

	static final String SEPARATOR = "_";
	static final String VIEW = "VIEW";
	static final String MODULE = "MODULE";
	static final String FORM = "FORM";
	static final String AUTHORIZATION = "AUTHORIZATION";
	static final String PROFILE = "PROFILE";
	static final String USER = "USER";
	static final String LAYOUT = "LAYOUT";
	static final String PROPERTIE = "PROPERTIE";
	static final String MIMETYPE = "MIMETYPE";
	static final String NOTIFY = "NOTIFY";
	static final String SETTINGS = "SETTINGS";
	static final String MESSAGE = "MESSAGE";
	static final String TEROS = "TEROS";
	static final String ASK_TEROS = "ASK_TEROS";
	static final String CHAT_TEROS = "CHAT_TEROS";
	static final String CR_IMAGE_TEROS = "CR_IMAGE_TEROS";
	
	static final String SEP = SEPARATOR;
	
	static final String MNEMONIC = "TCORE";
	
	static final String TEROS_MODULE_ID = MNEMONIC + SEP + TEROS + SEP + MODULE;

	static final String ASK_TEROS_FORM_ID = MNEMONIC + SEP + ASK_TEROS + SEP + FORM;
	static final String ASK_TEROS_VIEW_ID = MNEMONIC + SEP + ASK_TEROS + SEP + VIEW;
	static final String CHAT_TEROS_FORM_ID = MNEMONIC + SEP + CHAT_TEROS + SEP + FORM;
	static final String CHAT_TEROS_VIEW_ID = MNEMONIC + SEP + CHAT_TEROS + SEP + VIEW;
	static final String CR_IMAGE_TEROS_FORM_ID = MNEMONIC + SEP + CR_IMAGE_TEROS + SEP + FORM;
	static final String CR_IMAGE_TEROS_VIEW_ID = MNEMONIC + SEP + CR_IMAGE_TEROS + SEP + VIEW;


	static final String SETTINGS_MODULE_ID = MNEMONIC + SEP + SETTINGS + SEP + MODULE;

	static final String AUTHORIZATION_FORM_ID = MNEMONIC + SEP + AUTHORIZATION + SEP + FORM;
	static final String AUTHORIZATION_VIEW_ID = MNEMONIC + SEP + AUTHORIZATION + SEP + VIEW;
	static final String AUTHORIZATION_MODULE_ID = MNEMONIC + SEP + AUTHORIZATION + SEP + MODULE;

	static final String PROFILE_FORM_ID = MNEMONIC + SEP + PROFILE + SEP + FORM;
	static final String PROFILE_VIEW_ID = MNEMONIC + SEP + PROFILE + SEP + VIEW;
	static final String PROFILE_MODULE_ID = MNEMONIC + SEP + PROFILE + SEP + MODULE;

	static final String USER_FORM_ID = MNEMONIC + SEP + USER + SEP + FORM;
	static final String USER_VIEW_ID = MNEMONIC + SEP + USER + SEP + VIEW;
	static final String USER_MODULE_ID = MNEMONIC + SEP + USER + SEP + MODULE;

	static final String LAYOUT_FORM_ID = MNEMONIC + SEP + LAYOUT + SEP + FORM;
	static final String LAYOUT_VIEW_ID = MNEMONIC + SEP + LAYOUT + SEP + VIEW;
	static final String LAYOUT_MODULE_ID = MNEMONIC + SEP + LAYOUT + SEP + MODULE;

	static final String PROPERTIE_FORM_ID = MNEMONIC + SEP + PROPERTIE + SEP + FORM;
	static final String PROPERTIE_VIEW_ID = MNEMONIC + SEP + PROPERTIE + SEP + VIEW;
	static final String PROPERTIE_MODULE_ID = MNEMONIC + SEP + PROPERTIE + SEP + MODULE;

	static final String NOTIFY_FORM_ID = MNEMONIC + SEP + NOTIFY + SEP + FORM;
	static final String NOTIFY_VIEW_ID = MNEMONIC + SEP + NOTIFY + SEP + VIEW;
	static final String NOTIFY_MODULE_ID = MNEMONIC + SEP + NOTIFY + SEP + MODULE;

	static final String MESSAGE_FORM_ID = MNEMONIC + SEP + MESSAGE + SEP + FORM;
	static final String MESSAGE_VIEW_ID = MNEMONIC + SEP + MESSAGE + SEP + VIEW;
	static final String MESSAGE_MODULE_ID = MNEMONIC + SEP + MESSAGE + SEP + MODULE;
	

	static final String MIMETYPE_FORM_ID = MNEMONIC + SEP + MIMETYPE + SEP + FORM;
	static final String MIMETYPE_VIEW_ID = MNEMONIC + SEP + MIMETYPE + SEP + VIEW;
	static final String MIMETYPE_MODULE_ID = MNEMONIC + SEP + MIMETYPE + SEP + MODULE;



}
