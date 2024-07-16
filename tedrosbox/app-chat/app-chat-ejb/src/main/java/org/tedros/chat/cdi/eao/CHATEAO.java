/**
 * 
 */
package org.tedros.chat.cdi.eao;

import jakarta.enterprise.context.Dependent;

import org.tedros.server.cdi.eao.TGenericEAO;
import org.tedros.server.entity.ITEntity;

/**
 * The generic entity access object.
 * 
 * This use a Dependent context. 
 * 
 * @author Davis Dun
 *
 */
@Dependent
public class CHATEAO<E extends ITEntity> extends TGenericEAO<E> {

}
