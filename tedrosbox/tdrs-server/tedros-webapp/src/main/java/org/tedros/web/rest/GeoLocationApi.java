/**
 * 
 */
package org.tedros.web.rest;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.tedros.ejb.controller.IAdminAreaController;
import org.tedros.ejb.controller.ICityController;
import org.tedros.ejb.controller.ICountryController;

/**
 * @author Davis Gordon
 *
 */
@Path("/geo")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class GeoLocationApi extends BaseApi {


	@EJB 
	private ICountryController cntServ;
	
	@EJB 
	private IAdminAreaController aaServ;

	@EJB 
	private ICityController ctServ;
	
}
