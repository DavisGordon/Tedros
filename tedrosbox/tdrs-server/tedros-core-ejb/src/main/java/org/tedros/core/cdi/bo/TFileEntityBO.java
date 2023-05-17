/**
 * 
 */
package org.tedros.core.cdi.bo;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.common.model.TFileEntity;
import org.tedros.core.cdi.eao.TFileEntityEao;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class TFileEntityBO extends TGenericBO<TFileEntity> {

	@Inject
	private TFileEntityEao eao;
	
	
	@Override
	public ITGenericEAO<TFileEntity> getEao() {
		return eao;
	}
	
	public void loadBytes(final TFileEntity entity) {
		eao.loadBytes(entity);
	}
	

	public List<TFileEntity> find(List<String> owner, List<String> ext, Long maxSize, boolean loaded){
		return eao.find(owner, ext, maxSize, loaded);
	}

}
