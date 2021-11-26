/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 04/09/2014
 */
package com.tedros.ejb.base.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TByteModel implements ITByteModel, Serializable {

	private static final long serialVersionUID = 940276317128115769L;
	private byte[] bytes;
	
	
	@Override
	public byte[] getBytes() {
		return bytes;
	}

	
	@Override
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(bytes);
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TByteModel))
			return false;
		TByteModel other = (TByteModel) obj;
		if (!Arrays.equals(bytes, other.bytes))
			return false;
		return true;
	}

}
