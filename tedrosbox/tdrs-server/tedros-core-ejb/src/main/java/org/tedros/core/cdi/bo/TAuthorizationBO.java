package org.tedros.core.cdi.bo;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.commons.collections4.ListUtils;
import org.tedros.core.cdi.eao.TCoreEAO;
import org.tedros.core.security.model.TAuthorization;
import org.tedros.core.security.model.TProfile;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.TGenericEAO;

@RequestScoped
public class TAuthorizationBO extends TGenericBO<TAuthorization> {
	
	@Inject
	private TCoreEAO<TAuthorization> eao;
	
	@Inject
	private TCoreBO<TProfile> pBO;
	
	@Override
	public TGenericEAO<TAuthorization> getEao() {
		return eao;
	}
	
	public List<String> process(List<TAuthorization> newLst, List<TAuthorization> oldLst) throws Exception{
		List<String> msg = new ArrayList<>();
		
		if(oldLst!=null && !oldLst.isEmpty()) {
		
			//itens removidos 
			final List<TAuthorization> remLst = ListUtils.removeAll(oldLst, newLst);
			//itens adicionados 
			final List<TAuthorization> addLst = ListUtils.removeAll(newLst, oldLst);
			//itens retidos
			final List<TAuthorization> savLst = ListUtils.retainAll(oldLst, newLst);
			
			if(remLst!=null && !remLst.isEmpty()) {
				List<TProfile> profiles = pBO.listAll(TProfile.class);
				if(profiles!=null &&! profiles.isEmpty()) {
					for (TProfile p : profiles) {
						if(p.getAutorizations()!=null) {
							p.getAutorizations().removeAll(remLst);
							pBO.save(p);
						}
					}
				}
				for (TAuthorization e : remLst) {
					super.remove(e);
					msg.add(e.toString() + " [Removed]");
				}
			}
			
			if(addLst!=null && !addLst.isEmpty()) {
				for (TAuthorization e : addLst) {
					super.save(e);
					msg.add(e.toString() + " [Added]");
				}
			}
			
			if(savLst!=null && !savLst.isEmpty()) {
				for (TAuthorization s : savLst) {
					String sLabel = s.toString();
					TAuthorization e = newLst.get(newLst.indexOf(s));
					boolean f = false;
					// app name 
					if(!e.getAppName().equals(s.getAppName())) {
						s.setAppName(e.getAppName());
						f = true;
					}
					// type
					if(!e.getType().equals(s.getType())) {
						s.setType(e.getType());
						f = true;
					}
					// module name
					if(e.getModuleName() != null) {
						if(s.getModuleName() == null 
								|| (s.getModuleName() != null && !e.getModuleName().equals(s.getModuleName()))) {
							s.setModuleName(e.getModuleName());
							f = true;
						}
					} else { 
						if(s.getModuleName() != null) {
							s.setModuleName(null);
							f = true;
						}
					}
					// view name
					if(e.getViewName() != null) {
						if(s.getViewName() == null 
								|| (s.getViewName() != null && !e.getViewName().equals(s.getViewName()))) {
							s.setViewName(e.getViewName());
							f = true;
						}
					} else { 
						if(s.getViewName() != null) {
							s.setViewName(null);
							f = true;
						}
					}
					// type description 
					if(e.getTypeDescription() != null) {
						if(s.getTypeDescription() == null 
								|| (s.getTypeDescription() != null && !e.getTypeDescription().equals(s.getTypeDescription()))) {
							s.setTypeDescription(e.getTypeDescription());
							f = true;
						}
					} else { 
						if(s.getTypeDescription() != null) {
							s.setTypeDescription(null);
							f = true;
						}
					}
					if(f) {
						super.save(s);
						msg.add(sLabel +" [Changed to] "+ e.toString());
					}
				}
			}
			
		} else {
			for (TAuthorization e : newLst) {
				super.save(e);
				msg.add(e.toString() + " [Added]");
			}
		}
		
		
		
		return msg;
	}

}
