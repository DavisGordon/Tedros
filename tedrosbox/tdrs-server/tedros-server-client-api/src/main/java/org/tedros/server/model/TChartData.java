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
	private Object extra;
	
	/**
	 * @param x
	 * @param y
	 */
	public TChartData(X x, Y y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @param x
	 * @param y
	 * @param extra
	 */
	public TChartData(X x, Y y, Object extra) {
		this.x = x;
		this.y = y;
		this.extra = extra;
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

	@Override
	public Object getExtra() {
		return extra;
	}

}
