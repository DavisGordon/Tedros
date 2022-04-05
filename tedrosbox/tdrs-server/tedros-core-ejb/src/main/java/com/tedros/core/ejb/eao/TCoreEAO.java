/**
 * 
 */
package com.tedros.core.ejb.eao;

import javax.enterprise.context.Dependent;

import com.tedros.ejb.base.eao.TGenericEAO;
import com.tedros.ejb.base.entity.ITEntity;

/**
 * @author Davis Gordon
 *
 */
@Dependent
public class TCoreEAO<E extends ITEntity> extends TGenericEAO<E> {

}
