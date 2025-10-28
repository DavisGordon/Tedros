/**
 * 
 */
package org.tedros.server.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The chart serie model
 * @author Davis Gordon
 *
 */
public class TChartSerie<X, Y> implements ITChartSerie<X, Y> {

	private static final long serialVersionUID = -6778631459163590845L;

	private String name;
	private List<ITChartData<X,Y>> datas;
	/**
	 * 
	 */
	public TChartSerie(String name) {
		this.name = name;
	}
	
	/**
	 * Adds a data to the list
	 * @param x
	 * @param y
	 */
	public void addData(X x, Y y) {
		if(datas==null)
			datas = new ArrayList<>();
		datas.add(new TChartData<X,Y>(x,y));
	}

	/* (non-Javadoc)
	 * @see org.tedros.server.model.ITChartSerie#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see org.tedros.server.model.ITChartSerie#getDatas()
	 */
	@Override
	public List<ITChartData<X, Y>> getDatas() {
		return datas;
	}

}
