package com.tedros.fxapi.domain;

import com.tedros.fxapi.annotation.reader.TReader;


/**
 * <pre>
 * The mode to show in the view.  
 * </pre>
 * */
public enum TViewMode {
	/**
	 * Reader mode, show all fields annotated by {@link TReader}
	 * */
	READER, 
	
	/**
	 * Edit mode, show all fields in edit mode to input user data.
	 * */
	EDIT;
}
