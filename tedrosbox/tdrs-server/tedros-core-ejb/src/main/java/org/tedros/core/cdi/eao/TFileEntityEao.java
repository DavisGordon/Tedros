/**
 * 
 */
package org.tedros.core.cdi.eao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import org.tedros.common.model.TByteEntity;
import org.tedros.common.model.TFileEntity;
import org.tedros.server.cdi.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class TFileEntityEao extends TGenericEAO<TFileEntity> {

	@SuppressWarnings("unchecked")
	public List<TFileEntity> find(List<String> owner, List<String> ext, Long maxSize, boolean loaded){
		String s = "select e from "+TFileEntity.class.getSimpleName()+" e where 1=1 ";
		if(owner!=null)
			s += " and e.owner in :ow";
		if(ext!=null)
			s += " and e.fileExtension in :ext";
		if(maxSize!=null)
			s += " and e.fileSize <= :ms";
		
		Query qry = getEntityManager().createQuery(s);
		
		if(owner!=null)
			qry.setParameter("ow", owner);
		if(ext!=null)
			qry.setParameter("ext", ext);
		if(maxSize!=null)
			qry.setParameter("ms", maxSize);
		
		List<TFileEntity> l = qry.getResultList();
		if(l!=null && !l.isEmpty() && loaded)
			for(TFileEntity e : l)
				loadBytes(e);
		return l;
	}
	
	public void loadBytes(final TFileEntity entity) {
		Query qry = getEntityManager().createQuery("select entity.bytes from "+TByteEntity.class.getSimpleName()+" entity where entity.id = "+entity.getByteEntity().getId());
		byte[] bytes = (byte[]) qry.getSingleResult();
			
		entity.getByteEntity().setBytes(bytes);
		
	}
}
