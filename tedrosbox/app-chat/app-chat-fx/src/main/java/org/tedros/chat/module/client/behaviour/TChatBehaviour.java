/**
 * 
 */
package org.tedros.chat.module.client.behaviour;

import java.util.UUID;

import org.tedros.chat.entity.Chat;
import org.tedros.chat.entity.ChatMessage;
import org.tedros.chat.module.client.model.TChatMV;
import org.tedros.chat.module.client.setting.ChatClient;
import org.tedros.chat.module.client.setting.ChatUtil;
import org.tedros.core.context.TedrosContext;
import org.tedros.fx.presenter.entity.behavior.TMasterCrudViewBehavior;

import javafx.beans.value.ChangeListener;

/**
 * @author Davis Gordon
 *
 */
public class TChatBehaviour extends TMasterCrudViewBehavior<TChatMV, Chat> {

	private ChatUtil util;
	
	
	@Override
	public void load() {
		super.load();
		util = new ChatUtil();
		ChangeListener<Object> ch0 = (a,o,n)->{
			if(n instanceof ChatMessage) {
				
			}
		};
		
		
	}
	
	@Override
	public boolean processNewEntityBeforeBuildForm(TChatMV model) {
		try {
			model.getModel().setCode(UUID.randomUUID().toString());
			model.getOwner().setValue(ChatClient.getInstance().getOwner());
			Chat c = util.saveChat(TedrosContext.getLoggedUser().getAccessToken(), model.getModel());
			model.reload(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.processNewEntityBeforeBuildForm(model);
	}
	
}
