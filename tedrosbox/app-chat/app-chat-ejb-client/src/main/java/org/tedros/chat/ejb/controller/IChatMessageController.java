package org.tedros.chat.ejb.controller;

import javax.ejb.Remote;

import org.tedros.chat.entity.ChatMessage;
import org.tedros.server.controller.ITSecureEjbController;

@Remote
public interface IChatMessageController extends ITSecureEjbController<ChatMessage>{
	

	static final String JNDI_NAME = "IChatMessageControllerRemote";
}
