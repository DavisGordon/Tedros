/**
 * 
 */
package com.tedros.core.ejb.eao;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import com.tedros.common.model.TByteEntity;
import com.tedros.common.model.TFileEntity;
import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class TFileEntityEao extends TGenericEAO<TFileEntity> {

	
	public void loadBytes(final TFileEntity entity) {
		Query qry = getEntityManager().createQuery("select entity.bytes from "+TByteEntity.class.getSimpleName()+" entity where entity.id = "+entity.getByteEntity().getId());
		byte[] bytes = (byte[]) qry.getSingleResult();
			
		entity.getByteEntity().setBytes(bytes);
		
	}
}
