/**
 * 
 */
package com.tedros.editorweb.server.controller;

import javax.ejb.Remote;

import com.tedros.editorweb.model.HtmlTemplate;
import com.tedros.ejb.base.controller.ITSecureEjbController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface ITHtmlTemplateController extends ITSecureEjbController<HtmlTemplate> {

}
