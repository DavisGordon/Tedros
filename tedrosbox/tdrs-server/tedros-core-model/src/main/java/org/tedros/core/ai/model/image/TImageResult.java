/**
 * 
 */
package org.tedros.core.ai.model.image;

import java.util.ArrayList;
import java.util.List;

import org.tedros.core.ai.model.TBaseResult;
import org.tedros.core.ai.model.TResponseFormat;

/**
 * @author Davis Gordon
 *
 */
public class TImageResult extends TBaseResult {

	private static final long serialVersionUID = -6877305621095887716L;

    /**
     * List of image results.
     */
    private List<TImage> data;
    
	/**
	 * 
	 */
	public TImageResult() {
		super.setSuccess(true);
	}
	
	
	/**
	 * @param log
	 * @param success
	 */
	public TImageResult(String log, boolean success) {
		super(log, success);
	}


	public void addData(String value, TResponseFormat format) {
		if(data==null)
			data = new ArrayList<>();
		data.add(new TImage(value, format));
	}
	/**
	 * @return the data
	 */
	public List<TImage> getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(List<TImage> data) {
		this.data = data;
	}
}
