/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 04/09/2014
 */
package com.tedros.ejb.base.model;

import java.io.Serializable;

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

}
