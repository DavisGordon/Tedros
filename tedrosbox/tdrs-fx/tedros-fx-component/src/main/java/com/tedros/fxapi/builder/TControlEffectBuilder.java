/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import javafx.scene.effect.Effect;

import com.tedros.fxapi.effect.TEffectUtil;

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
