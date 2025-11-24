package org.tedros.core.cdi.bo;

import org.tedros.common.model.TFileEntity;
import org.tedros.core.cdi.eao.TPropertieEao;
import org.tedros.core.domain.TSystemPropertie;
import org.tedros.core.setting.model.TPropertie;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.util.TLoggerUtil;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class TPropertieBO extends TGenericBO<TPropertie> {
	
	private TLoggerUtil logger = TLoggerUtil.create(TPropertieBO.class);
	
	@Inject
	private TPropertieEao eao;
	
	@Override
	public TPropertieEao getEao() {
		return eao;
	}

	public String getValue(String key) {
		return eao.getValue(key);
	}
	
	public TFileEntity getFile(String key){
		return eao.getFile(key);
	}
	
	public boolean exists(String key) {		
		boolean res = eao.exist(key);
		
		logger.info("Application property {} : {}", key, res ? "Already setted!" : "Not exists!");
		
		return res;
	}
	
	public boolean create(TPropertie propertie) throws Exception {
		
		if(exists(propertie.getKey())) 
			return false;
		
		eao.persist(propertie);
		
		logger.info("Property {} created!", propertie.getValue());
		
		return true;
	}
	
	public void buildProperties() throws Exception {
		for(TSystemPropertie p : TSystemPropertie.values()) {		
			if(!exists(p.getValue())) {				
				TPropertie e = new TPropertie();
				e.setName(p.name());
				e.setKey(p.getValue());
				e.setDescription(p.getDescription());
				eao.persist(e);
			}
		}
	}

}
