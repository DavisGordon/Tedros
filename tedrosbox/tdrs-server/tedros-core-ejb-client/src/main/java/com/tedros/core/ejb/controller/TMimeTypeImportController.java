/**
 * 
 */
package com.tedros.core.ejb.controller;

import javax.ejb.Remote;

import com.tedros.common.model.TMimeType;
import com.tedros.ejb.base.controller.ITEjbImportController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface TMimeTypeImportController extends ITEjbImportController<TMimeType> {

	static final String JNDI_NAME = "TMimeTypeImportControllerRemote";
}
