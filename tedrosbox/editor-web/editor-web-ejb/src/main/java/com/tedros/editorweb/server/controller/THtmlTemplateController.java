/**
 * 
 */
package com.tedros.editorweb.server.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.editorweb.model.ComponentTemplate;
import com.tedros.editorweb.model.HtmlTemplate;
import com.tedros.editorweb.model.Script;
import com.tedros.editorweb.model.Style;
import com.tedros.editorweb.server.service.TEntityService;
import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TRemoteSecurity;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * @author Davis Gordon
 *
 */
@TRemoteSecurity
@Stateless(name="ITHtmlTemplateController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class THtmlTemplateController extends TSecureEjbController<HtmlTemplate> 
implements ITHtmlTemplateController, ITSecurity{

	@EJB
	private TEntityService<HtmlTemplate> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	protected ITEjbService<HtmlTemplate> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return this.securityController;
	}
	
	@Override
	public TResult<HtmlTemplate> save(TAccessToken token, HtmlTemplate e) {
		if(e.getScripts()!=null)
			for(Script s : e.getScripts())
				if(s.getTemplate()==null)
					s.setTemplate(e);
		if(e.getStyles()!=null)
			for(Style s : e.getStyles())
				if(s.getTemplate()==null)
					s.setTemplate(e);
		if(e.getComponents()!=null)
			for(ComponentTemplate s : e.getComponents())
				if(s.getTemplate()==null)
					s.setTemplate(e);
		return super.save(token, e);
	}


}
