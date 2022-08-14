/**
 * 
 */
package org.tedros.tools.resource;

import org.tedros.tools.start.TConstant;
import org.tedros.util.TAppResource;

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
