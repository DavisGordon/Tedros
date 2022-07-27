/**
 * 
 */
package com.tedros.tools.resource;

import com.tedros.tools.start.TConstant;
import com.tedros.util.TAppResource;

/**
 * @author Davis Gordon
 *
 */
public class AppResource extends TAppResource{
	
	private static final String CSV ="mime_type.csv";
	
	public AppResource() {
		super(TConstant.UUI);
		super.addResource(CSV);
	}
}
