/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 18/01/2014
 */
package com.tedros.fxapi.annotation.parser;

import com.tedros.fxapi.annotation.reader.TDetailReader;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TDetailReaderParser extends TAnnotationParser<TDetailReader, Object> {

	private static TDetailReaderParser instance;
	
	private TDetailReaderParser() {
		
	}
	
	public static TDetailReaderParser getInstance(){
		if(instance==null)
			instance = new TDetailReaderParser();
		return instance;	
	}	
}
