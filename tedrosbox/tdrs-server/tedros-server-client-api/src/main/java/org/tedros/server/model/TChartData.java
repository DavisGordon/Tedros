/**
 * 
 */
package org.tedros.server.model;

/**
 * The chart data model
 * @author Davis Gordon
 *
 */
public class TChartData<X, Y> implements ITChartData<X, Y> {

	private static final long serialVersionUID = -5521383652337950329L;
	
	private X x;
	private Y y;
	/**
	 * x for horizontal data 
	 * and y for vertical data
	 */
	public TChartData(X x, Y y) {
		this.x = x;
		this.y = y;
	}

	/* (non-Javadoc)
	 * @see org.tedros.server.model.ITChartData#getX()
	 */
	@Override
	public X getX() {
		return x;
	}

	/* (non-Javadoc)
	 * @see org.tedros.server.model.ITChartData#getY()
	 */
	@Override
	public Y getY() {
		return y;
	}

}
