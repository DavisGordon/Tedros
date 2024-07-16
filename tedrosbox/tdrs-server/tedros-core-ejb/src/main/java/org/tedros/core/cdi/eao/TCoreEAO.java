/**
 * 
 */
package org.tedros.core.cdi.eao;

import jakarta.enterprise.context.Dependent;

import org.tedros.server.cdi.eao.TGenericEAO;
import org.tedros.server.entity.ITEntity;

/**
 * @author Davis Gordon
 *
 */
@Dependent
public class TCoreEAO<E extends ITEntity> extends TGenericEAO<E> {

}
