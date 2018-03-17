/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 17/11/2014
 */
package com.tedros.fxapi.effect;

import javafx.scene.effect.Effect;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author davis.dun
 *
 */
public interface ITEffectBuilder<E extends Effect> {
	
	public E build();

}
