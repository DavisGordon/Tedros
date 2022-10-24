package org.tedros.chat.cdi.bo;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.chat.cdi.eao.CHATEAO;
import org.tedros.chat.cdi.eao.TCoreUserEao;
import org.tedros.chat.entity.ChatUser;
import org.tedros.core.security.model.TUser;
import org.tedros.server.cdi.bo.TGenericBO;

@RequestScoped
public class ChatUserBO extends TGenericBO<ChatUser> {
	
	@Inject
	private CHATEAO<ChatUser> eao;
	
	@Inject
	private TCoreUserEao coreEao;
	
	@Override
	public CHATEAO<ChatUser> getEao() {
		return eao;
	}
	
	
	public List<ChatUser> validate(List<ChatUser> lst) throws Exception {
		
		List<ChatUser> chat = new ArrayList<>();
		
		for(ChatUser c : lst){
			ChatUser a = new ChatUser();
			a.setUserId(c.getUserId());
			a = eao.find(a);
			
			if(a==null || 
					(!a.getName().equals(c.getName()) 
							|| !a.getProfiles().equals(c.getProfiles()))) {
				a = super.save(c);
			}
			chat.add(a);
		};
		return chat;
	}
	
	@Override
	public List<ChatUser> findAll(ChatUser e, int firstResult, int maxResult, boolean orderByAsc,
			boolean containsAnyKeyWords) throws Exception {
		TUser u = new TUser();
		u.setName(e.getName());
		
		List<TUser> core = coreEao.findAll(u, firstResult, maxResult, orderByAsc, containsAnyKeyWords);
		List<ChatUser> chat = new ArrayList<>();
		core.forEach(c->{
			ChatUser a = new ChatUser();
			a.setUserId(c.getId());
			a.setName(c.getName());
			a.setProfiles(c.getProfilesText());
			chat.add(a);
		});
		return chat;
	}
	
	@Override
	public Integer countFindAll(ChatUser e, boolean containsAnyKeyWords) throws Exception {
		TUser u = new TUser();
		u.setName(e.getName());
		
		return coreEao.countFindAll(u, containsAnyKeyWords);
	}


}
