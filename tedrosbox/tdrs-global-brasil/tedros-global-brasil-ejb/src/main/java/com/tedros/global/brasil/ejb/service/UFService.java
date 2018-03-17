/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.tedros.global.brasil.ejb.service;

import javax.ejb.Singleton;
import javax.ejb.Stateless;

import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.ejb.base.eao.TGenericEAO;
import com.tedros.ejb.base.service.TEjbService;
import com.tedros.global.brasil.model.UF;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Singleton
@Stateless(name = "IUFService")
public class UFService extends TEjbService<UF> implements IUFService {
	
	private TGenericBO<UF> bo = new TGenericBO<UF>() {

		private TGenericEAO<UF> eao = new TGenericEAO<>();
		
		@Override
		public ITGenericEAO<UF> getEao() {
			
			return eao;
		}
	};
	
	@Override
	public ITGenericBO<UF> getBussinesObject() {
		return bo;
	}

	
}
