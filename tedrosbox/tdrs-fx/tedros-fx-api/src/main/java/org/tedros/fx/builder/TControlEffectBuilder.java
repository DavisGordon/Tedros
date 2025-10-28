/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import org.tedros.fx.effect.TEffectUtil;

import javafx.scene.effect.Effect;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TControlEffectBuilder {

	public static Effect buildNotNullEffect() {
		return TEffectUtil.buildNotNullFieldFormEffect();
	}
	
}
