package org.tedros.core.cdi.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.common.model.TFileEntity;
import org.tedros.core.cdi.eao.TPropertieEao;
import org.tedros.core.domain.TSystemPropertie;
import org.tedros.core.setting.model.TPropertie;
import org.tedros.server.cdi.bo.TGenericBO;

@RequestScoped
public class TPropertieBO extends TGenericBO<TPropertie> {
	
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
	
	public void buildProperties() throws Exception {
		for(TSystemPropertie p : TSystemPropertie.values()) {
			if(!eao.exist(p.getValue())) {
				TPropertie e = new TPropertie();
				e.setName(p.name());
				e.setKey(p.getValue());
				e.setDescription(p.getDescription());
				eao.persist(e);
			}
				
		}
	}

}
