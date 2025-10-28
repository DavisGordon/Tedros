package org.tedros.chat.cdi.bo;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import org.tedros.chat.cdi.eao.ChatTPropertieEao;
import org.tedros.chat.domain.ChatPropertie;
import org.tedros.common.model.TFileEntity;
import org.tedros.core.setting.model.TPropertie;
import org.tedros.server.cdi.bo.TGenericBO;

@RequestScoped
public class ChatTPropertieBO extends TGenericBO<TPropertie> {
	
	@Inject
	private ChatTPropertieEao eao;
	
	@Override
	public ChatTPropertieEao getEao() {
		return eao;
	}

	public String getValue(String key) {
		return eao.getValue(key);
	}
	
	public TFileEntity getFile(String key){
		return eao.getFile(key);
	}
	
	public void buildProperties() throws Exception {
		for(ChatPropertie p : ChatPropertie.values()) {
			if(!eao.exist(p.getValue())) {
				TPropertie e = new TPropertie();
				e.setName(p.name());
				e.setKey(p.getValue());
				eao.persist(e);
			}
		}
	}

}
