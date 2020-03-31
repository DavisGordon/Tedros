package com.tedros.ejb.base.service;

import java.io.Serializable;

/**
 * A Service result.
 * 
 *  @author Davis Gordon
 * 
 * */

public class TResult<E> implements Serializable {

	private static final long serialVersionUID = -7635310661063307949L;
	
	/**
	 * SUCESS(1),
	 * ERROR(-1),
	 * OUTDATED(-2),
	 * WARNING(0);
	 * */
	public enum EnumResult {
		SUCESS(1),
		ERROR(-1),
		OUTDATED(-2),
		WARNING(0);
		
		private int value;
		
		private EnumResult(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
	}
	
	private EnumResult result;
	private String message;
	private String errorMessage;
	private E value;
	
	public TResult() {
		
	}
	
	public TResult(EnumResult result) {
		this.result = result;
	}
	
	public TResult(EnumResult result, String message) {
		this.result = result;
		this.message = message;
	}
	
	public TResult(EnumResult result, String message, String errorMessage) {
		this.result = result;
		this.message = message;
		this.errorMessage = errorMessage;
	}
	
	public TResult(EnumResult result, String message, String errorMessage, E value) {
		this.result = result;
		this.message = message;
		this.errorMessage = errorMessage;
		this.value = value;
	}
	
	public TResult(EnumResult result, String message, E value) {
		this.result = result;
		this.message = message;
		this.value = value;
	}
	
	public TResult(EnumResult result, E value) {
		this.result = result;
		this.value = value;
	}
	
	public EnumResult getResult() {
		return result;
	}
	public void setResult(EnumResult result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public E getValue() {
		return value;
	}
	public void setValue(E value) {
		this.value = value;
	}
	
	
	
}
