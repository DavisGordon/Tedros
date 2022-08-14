package org.tedros.fx.domain;

import org.tedros.fx.annotation.reader.TReader;


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
