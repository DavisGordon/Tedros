/**
 * 
 */
package org.tedros.web.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.tedros.ejb.controller.IAdminAreaController;
import org.tedros.ejb.controller.ICityController;
import org.tedros.ejb.controller.ICountryController;
import org.tedros.ejb.controller.IStreetTypeController;
import org.tedros.location.model.AdminArea;
import org.tedros.location.model.City;
import org.tedros.location.model.Country;
import org.tedros.location.model.StreetType;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.web.model.RestModel;
import org.tedros.web.model.ValueModel;

/**
 * @author Davis Gordon
 *
 */
@Path("/geo")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class GeoLocationApi extends BaseApi {

	@EJB 
	private IStreetTypeController stServ;
	
	@EJB 
	private ICountryController cntServ;
	
	@EJB 
	private IAdminAreaController aaServ;

	@EJB 
	private ICityController ctServ;
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/streettype/{name}")
	public RestModel<List<ValueModel<Long>>> filterStreetTypes(
			@PathParam("name") String name){
		try {
			List<ValueModel<Long>> lst = new ArrayList<>();
			StreetType st = new StreetType();
			st.setName(name);
			TResult<Map<String, Object>> res = stServ.findAll(appBean.getToken(), st, 0, Integer.MAX_VALUE, true, true);
			if(res.getState().equals(TState.SUCCESS)) {
				List<StreetType> l = (List<StreetType>) res.getValue().get("list");
				if(l!=null) {
					l.sort((a, b)->{
						return a.getName().compareToIgnoreCase(b.getName());
					});
					l.forEach(c->{
						lst.add(new ValueModel<>(c.getId(), c.getName()));
					});
				}
			}
			return new RestModel<>(lst, OK, "StreetTypes");
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<>(null, ERROR, error.getValue());
		}
	}
	
	@GET
	@Path("/countries")
	@SuppressWarnings("unchecked")
	public RestModel<List<ValueModel<Long>>> listCountries(){
		List<ValueModel<Long>> lst = new ArrayList<>();
		TResult<List<Country>> res = cntServ.listAll(appBean.getToken(), Country.class);
		if(res.getState().equals(TState.SUCCESS)) {
			List<Country> l = res.getValue();
			if(l!=null) {
				l.sort((a, b)->{
					return a.getName().compareToIgnoreCase(b.getName());
				});
				l.forEach(c->{
					lst.add(new ValueModel<>(c.getId(), c.getName()));
				});
			}
		}
		return new RestModel<>(lst, OK, "Countries");
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/adminAreas/{countryId}")
	public RestModel<List<ValueModel<Long>>> filterAdminAreas(@PathParam("countryId") Long id ){
		try {
			List<ValueModel<Long>> lst = new ArrayList<>();
			Country cnt = new Country();
			cnt.setId(id);
			TResult<Country> r = cntServ.findById(appBean.getToken(), cnt);
			if(r.getState().equals(TState.SUCCESS)) {
				TResult<List<AdminArea>> res = aaServ.filter(appBean.getToken(), r.getValue());
				if(res.getState().equals(TState.SUCCESS)) {
					List<AdminArea> l = res.getValue();
					if(l!=null) {
						l.forEach(c->{
							lst.add(new ValueModel<>(c.getId(), c.getName()));
						});
					}
				}
			}
			return new RestModel<>(lst, OK, "AdminAreas");
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<>(null, ERROR, error.getValue());
		}
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/cities/{countryId}/{adAreaId}")
	public RestModel<List<ValueModel<Long>>> filterCites(
			@PathParam("countryId") Long cId,
			@PathParam("adAreaId") Long aaId){
		try {
			List<ValueModel<Long>> lst = new ArrayList<>();
			Country cnt = new Country();
			cnt.setId(cId);
			TResult<Country> r = cntServ.findById(appBean.getToken(), cnt);
			if(r.getState().equals(TState.SUCCESS)) {
				AdminArea aa = new AdminArea();
				aa.setId(aaId);
				TResult<AdminArea> r1 = aaServ.findById(appBean.getToken(), aa);
				if(r1.getState().equals(TState.SUCCESS)) {
					TResult<List<City>> res = ctServ.filter(appBean.getToken(), r.getValue(), r1.getValue());
					if(res.getState().equals(TState.SUCCESS)) {
						List<City> l = res.getValue();
						if(l!=null) {
							l.forEach(c->{
								lst.add(new ValueModel<>(c.getId(), c.getName()));
							});
						}
					}
				}
			}
			return new RestModel<>(lst, OK, "Cities");
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<>(null, ERROR, error.getValue());
		}
	}
	
}
