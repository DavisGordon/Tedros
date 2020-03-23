/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 22/04/2014
 */
package com.tedros.fxapi.form.group;

import java.util.List;

import com.tedros.core.context.TEntry;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public interface TGroupFormControl {
	void runBefore(final TEntry<Object> item, final List<TEntry<Object>> entryList);
}
