/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 18/01/2014
 */
package com.tedros.fxapi.annotation.parser;

import com.tedros.fxapi.annotation.reader.TReader;
import com.tedros.fxapi.reader.TDateTimeReader;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TDateTimeReaderParser extends TAnnotationParser<TReader, TDateTimeReader> {

	private static TDateTimeReaderParser instance;
	
	private TDateTimeReaderParser() {
		
	}
	
	public static TDateTimeReaderParser getInstance(){
		if(instance==null)
			instance = new TDateTimeReaderParser();
		return instance;	
	}	
}
