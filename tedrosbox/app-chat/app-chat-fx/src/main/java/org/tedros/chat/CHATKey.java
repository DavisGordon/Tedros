/**
 * 
 */
package org.tedros.chat;

/**
 * The language  resource key domain
 * 
 * Run the org.tedros.fx.util.LanguageKeysBuilder to create the constants from your language resource.
 * Type the resource name without the locale, for example to read the App_en.properties just  
 * type App
 * 
 * @author Davis Dun
 *
 */
public interface CHATKey {
	
	static final String APP_CHAT = "#{app.chat}";
	static final String ERROR_SERVER_OUT = "#{error.server.out}";
	static final String ERROR_SERVER_PREFERENCE = "#{error.server.preference}";
	static final String ERROR_USER_NOT_FOUND = "#{error.user.not.found}";
	static final String CLEAR = "#{label.clear}";
	static final String RECIPIENTS = "#{label.recipients}";
	static final String SEND = "#{label.send}";
	static final String MENU_CLIENT = "#{menu.client}";
	static final String MENU_SERVER = "#{menu.server}";
	static final String MODULE_DESC_CLIENT = "#{module.desc.client}";
	static final String MODULE_DESC_SERVER = "#{module.desc.server}";
	static final String MODULE_MESSAGES = "#{module.messages}";
	static final String MODULE_SERVER_SETTINGS = "#{module.server.settings}";
	static final String MSG_CHAT_REMOVED = "#{msg.chat.removed}";
	static final String MSG_ERROR = "#{msg.error}";
	static final String MSG_ONLY_OWNER_DELETE = "#{msg.only.owner.delete}";
	static final String MSG_OWNER_REMOVED_CHAT = "#{msg.owner.removed.chat}";
	static final String MSG_SELECT_RECIPIENT = "#{msg.select.recipient}";
	static final String VIEW_CLIENT_MESSAGES = "#{view.client.messages}";
	static final String VIEW_SERVER_SETTINGS = "#{view.server.settings}";
}
