/**
 * 
 */
package com.tedros.editorweb.server.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.editorweb.model.Content;
import com.tedros.editorweb.model.Item;
import com.tedros.editorweb.model.Metadata;
import com.tedros.editorweb.model.Page;
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
@Stateless(name="ITPageController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TPageController extends TSecureEjbController<Page> 
implements ITPageController, ITSecurity{

	@EJB
	private TEntityService<Page> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	protected ITEjbService<Page> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return this.securityController;
	}
	
	@Override
	public TResult<Page> save(TAccessToken token, Page e) {
		if(e.getScripts()!=null)
			for(Script s : e.getScripts())
				if(s.getPage()==null)
					s.setPage(e);
		if(e.getStyles()!=null)
			for(Style s : e.getStyles())
				if(s.getPage()==null)
					s.setPage(e);
		if(e.getMetas()!=null)
			for(Metadata s : e.getMetas())
				if(s.getPage()==null)
					s.setPage(e);
		if(e.getContents()!=null)
			for(Content s : e.getContents()) {
				if(s.getPage()==null)
					s.setPage(e);
				if(s.getItems()!=null)
					for(Item i : s.getItems())
						if(i.getContent()==null)
							i.setContent(s);
			}
		return super.save(token, e);
	}


}
