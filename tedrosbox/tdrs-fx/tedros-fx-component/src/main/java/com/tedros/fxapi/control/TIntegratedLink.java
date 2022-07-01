/**
 * 
 */
package com.tedros.fxapi.control;

import javafx.scene.Node;
import javafx.scene.control.Hyperlink;

/**
 * @author Davis Gordon
 *
 */
public class TIntegratedLink extends Hyperlink {

	private Long tEntityId;
	private String tModulePath;
	private String tEntityClassName;
	private String tModelViewClassName;
	
	/**
	 * @param arg0
	 */
	public TIntegratedLink(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public TIntegratedLink(String arg0, Node arg1) {
		super(arg0, arg1);
	}
	
	private void init(){
		
	}

	/**
	 * @return the tEntityId
	 */
	public Long gettEntityId() {
		return tEntityId;
	}

	/**
	 * @param tEntityId the tEntityId to set
	 */
	public void settEntityId(Long tEntityId) {
		this.tEntityId = tEntityId;
	}

	/**
	 * @return the tModulePath
	 */
	public String gettModulePath() {
		return tModulePath;
	}

	/**
	 * @param tModulePath the tModulePath to set
	 */
	public void settModulePath(String tModulePath) {
		this.tModulePath = tModulePath;
	}

	/**
	 * @return the tEntityClassName
	 */
	public String gettEntityClassName() {
		return tEntityClassName;
	}

	/**
	 * @param tEntityClassName the tEntityClassName to set
	 */
	public void settEntityClassName(String tEntityClassName) {
		this.tEntityClassName = tEntityClassName;
	}

	/**
	 * @return the tModelViewClassName
	 */
	public String gettModelViewClassName() {
		return tModelViewClassName;
	}

	/**
	 * @param tModelViewClassName the tModelViewClassName to set
	 */
	public void settModelViewClassName(String tModelViewClassName) {
		this.tModelViewClassName = tModelViewClassName;
	}

}
