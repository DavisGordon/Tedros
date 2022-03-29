/**
 * 
 */
package com.template.server.base.eao;

import javax.enterprise.context.Dependent;

import com.tedros.ejb.base.eao.TGenericEAO;
import com.tedros.ejb.base.entity.ITEntity;

/**
 * @author Davis Gordon
 *
 */
@Dependent
public class TEntityEAO<E extends ITEntity> extends TGenericEAO<E> {

}
