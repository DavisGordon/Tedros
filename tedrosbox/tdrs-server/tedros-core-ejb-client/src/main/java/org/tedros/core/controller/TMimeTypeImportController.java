/**
 * 
 */
package org.tedros.core.controller;

import jakarta.ejb.Remote;

import org.tedros.common.model.TMimeType;
import org.tedros.server.controller.ITEjbImportController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface TMimeTypeImportController extends ITEjbImportController<TMimeType> {

	static final String JNDI_NAME = "TMimeTypeImportControllerRemote";
}
