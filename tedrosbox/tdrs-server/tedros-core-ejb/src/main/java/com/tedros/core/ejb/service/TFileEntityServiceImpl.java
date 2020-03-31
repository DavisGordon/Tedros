package com.tedros.core.ejb.service;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.persistence.Query;

import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.ejb.base.eao.TGenericEAO;
import com.tedros.ejb.base.service.TEjbService;
import com.tedros.ejb.base.service.TResult;
import com.tedros.ejb.base.service.TResult.EnumResult;
import com.tedros.global.model.TByteEntity;
import com.tedros.global.model.TFileEntity;

@Singleton
@Stateless(name = "TFileEntityService")
public class TFileEntityServiceImpl extends TEjbService<TFileEntity> implements	TFileEntityService {

	
	private TGenericBO<TFileEntity> bo = new TGenericBO<TFileEntity>(){

		private TGenericEAO<TFileEntity> eao = new TGenericEAO<>();
		
		@Override
		public ITGenericEAO<TFileEntity> getEao() {
			return eao;
		}
		
	};
	
	@Override
	public ITGenericBO<TFileEntity> getBussinesObject() {
		return bo;
	}

	@Override
	public TResult<TFileEntity> loadBytes(TFileEntity entity) {
		try{
			if(entity==null || entity.getByteEntity()==null || entity.getByteEntity().getId()==null)
				throw new IllegalArgumentException("ERROR: method loadBytes from TFileEntityService cant receive null for byteEntity.id at TFileEntity parameter!");
			
			Query qry = getEntityManager().createQuery("select entity.bytes from "+TByteEntity.class.getSimpleName()+" entity where entity.id = "+entity.getByteEntity().getId());
			byte[] bytes = (byte[]) qry.getSingleResult();
			
			entity.getByteEntity().setBytes(bytes);
			
			return new TResult<TFileEntity>(EnumResult.SUCESS, entity);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<TFileEntity>(EnumResult.ERROR, e.getMessage());
		}
	}


}
