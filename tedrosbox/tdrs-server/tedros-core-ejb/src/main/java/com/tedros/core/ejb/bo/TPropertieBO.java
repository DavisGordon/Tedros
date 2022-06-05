package com.tedros.core.ejb.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.tedros.common.model.TFileEntity;
import com.tedros.core.domain.TSystemPropertie;
import com.tedros.core.ejb.eao.TPropertieEao;
import com.tedros.core.setting.model.TPropertie;
import com.tedros.ejb.base.bo.TGenericBO;

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
				eao.persist(e);
			}
		}
	}

}
