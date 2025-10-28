<<<<<<<< HEAD:tedrosbox/app-solidarity/app-solidarity-ejb/src/main/java/com/solidarity/ejb/exception/EmailBusinessException.java
/**
 * 
 */
package com.solidarity.ejb.exception;

/**
 * @author Davis Gordon
 *
 */
public class EmailBusinessException extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2391016396156217537L;

	/**
	 * 
	 */
	public EmailBusinessException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public EmailBusinessException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public EmailBusinessException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public EmailBusinessException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public EmailBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
========
/**
 * 
 */
package org.tedros.server.util;

/**
 * @author Davis Gordon
 *
 */
public class TSentEmailException extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3553326262089191778L;

	/**
	 * 
	 */
	public TSentEmailException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public TSentEmailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public TSentEmailException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public TSentEmailException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public TSentEmailException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
>>>>>>>> tedrosbox_v17_globalweb:tedrosbox/tdrs-server/tedros-server-api/src/main/java/org/tedros/server/util/TSentEmailException.java
