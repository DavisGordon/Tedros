/**
 * 
 */
package com.tedros.core.ejb.service;

import javax.ejb.Local;
import javax.ejb.Remote;

import com.tedros.ejb.base.entity.TEntity;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * @author Davis Gordon
 *
 */
@Remote
@Local
public interface TEntityService extends ITEjbService<TEntity> {

}
