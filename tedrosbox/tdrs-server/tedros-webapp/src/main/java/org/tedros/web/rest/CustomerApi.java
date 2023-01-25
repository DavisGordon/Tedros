package org.tedros.web.rest;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.tedros.docs.model.Document;
import org.tedros.ejb.controller.IAdminAreaController;
import org.tedros.ejb.controller.ICityController;
import org.tedros.ejb.controller.ICountryController;
import org.tedros.ejb.controller.IStreetTypeController;
import org.tedros.env.ejb.controller.IWebUserController;
import org.tedros.env.entity.WebUser;
import org.tedros.extension.model.Contact;
import org.tedros.extension.model.ContactType;
import org.tedros.location.model.Address;
import org.tedros.location.model.AdminArea;
import org.tedros.location.model.City;
import org.tedros.location.model.Country;
import org.tedros.location.model.StreetType;
import org.tedros.person.domain.Sex;
import org.tedros.person.ejb.controller.ICustomerController;
import org.tedros.person.model.Customer;
import org.tedros.person.model.NaturalPerson;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.web.model.PersonModel;
import org.tedros.web.model.RestModel;
import org.tedros.web.util.ApiUtils;

/**
 * @author Davis Gordon
 *
 */
@Path("/cstmr")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class CustomerApi extends WebSessionBaseApi{
	
	@EJB 
	private ICountryController cntServ;
	
	@EJB 
	private IAdminAreaController aaServ;

	@EJB 
	private ICityController ctServ;

	@EJB 
	private IStreetTypeController sttServ;
	
	@EJB
	private ICustomerController ctmServ;

	@EJB
	private IWebUserController wuServ;
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/user/alter")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public RestModel<PersonModel> alterUser(@FormParam("name") String  name, 
			@FormParam("lastName") String  lastName,
			@FormParam("identity") String  identity,
			@FormParam("taxId") String  taxId,
			@FormParam("birthDate") String  birthDate,
			@FormParam("phone") String  phone,
			@FormParam("sex") String  sex,
			@FormParam("streetType") String  streetType,
			@FormParam("publicPlace") String  publicPlace,
			@FormParam("neighborhood") String  neighborhood,
			@FormParam("complement") String  complement,
			@FormParam("country") Long  country,
			@FormParam("adminArea") Long  adminArea,
			@FormParam("city") Long  city,
			@FormParam("code") String  code
			){
	
		try{
			Customer p = (Customer) session.get().getUser().getPerson();
			p.setName(name);
			p.setLastName(lastName);
			if(StringUtils.isNotBlank(birthDate)) {
				Date dt = ApiUtils.convertToDate(birthDate);
				p.setBirthDate(dt);
			}
			if(StringUtils.isNotBlank(sex)) {
				p.setSex(Sex.valueOf(sex));
			}
			Address adrs = p.getAddress()!=null ? p.getAddress() : new Address();
			
			adrs.setPublicPlace(publicPlace);
			adrs.setComplement(complement);
			adrs.setNeighborhood(neighborhood);
			adrs.setCode(code);
			
			if(StringUtils.isNotBlank(streetType)) {
				StreetType e = new StreetType();
				e.setName(streetType);
				TResult<StreetType> res = sttServ.find(appBean.getToken(), e);
				e = res.getValue();
				adrs.setStreetType(e);
			}
				
			if(country!=null) {
				Country e = new Country();
				e.setId(country);
				TResult<Country> res = cntServ.findById(appBean.getToken(), e);
				e = res.getValue();
				adrs.setCountry(e);
			}
			
			if(adminArea!=null) {
				AdminArea e = new AdminArea();
				e.setId(adminArea);
				TResult<AdminArea> res = aaServ.findById(appBean.getToken(), e);
				e = res.getValue();
				adrs.setAdminArea(e);
			}
			
			if(city!=null) {
				City e = new City();
				e.setId(city);
				TResult<City> res = ctServ.findById(appBean.getToken(), e);
				e = res.getValue();
				adrs.setCity(e);
			}
			
			if(StringUtils.isNotBlank(phone)) {
				if(p.getContacts()==null || p.getContacts().isEmpty()) {
					Contact c = new Contact();
					c.setName(name);
					c.setValue(phone);
					c.setType(ContactType.PHONE);
					if(p.getContacts()==null)
						p.setContacts(new HashSet<>());
					p.getContacts().add(c);
				}else if(p.getContacts()!=null && !p.getContacts().isEmpty()) {
					Optional<Contact> phn = p.getContacts()
							.stream().filter(c->c.getType().equals(ContactType.PHONE))
							.findFirst();
					Contact c = phn.isPresent() ? phn.get() : new Contact();
					c.setName(name);
					c.setValue(phone);
					c.setType(ContactType.PHONE);
					if(c.isNew())
						p.getContacts().add(c);
				} 	
			}
			
			if(StringUtils.isNotBlank(identity) || StringUtils.isNotBlank(taxId) ) {
				if(p.getDocuments()==null || p.getDocuments().isEmpty()) {
					
					if(p.getDocuments()==null)
						p.setDocuments(new HashSet<>());
					
					if(StringUtils.isNotBlank(identity)) {
						Document d = new Document();
						d.setCode("IDTT");
						d.setName("Identity");
						d.setValue(identity);
						p.getDocuments().add(d);
					}
					if(StringUtils.isNotBlank(taxId)) {
						Document d = new Document();
						d.setCode("TXD");
						d.setName("Tax Id");
						d.setValue(taxId);
						p.getDocuments().add(d);
					}
				}else if(p.getDocuments()!=null && !p.getDocuments().isEmpty()) {
					Optional<Document> idtt = p.getDocuments()
							.stream().filter(d->d.getCode()!=null && d.getCode().equals("IDTT"))
							.findFirst();
					Document d1 = (idtt.isPresent()) ? idtt.get() : new Document();
					d1.setCode("IDTT");
					d1.setName("Identity");
					d1.setValue(identity);
					if(d1.isNew())
						p.getDocuments().add(d1);
					
					Optional<Document> txd = p.getDocuments()
							.stream().filter(d->d.getCode()!=null && d.getCode().equals("TXD"))
							.findFirst();
					Document d2 = (txd.isPresent()) ? txd.get() : new Document();
					d2.setCode("TXD");
					d2.setName("Tax Id");
					d2.setValue(taxId);
					if(d2.isNew())
						p.getDocuments().add(d2);
							
				} 	
			}
			
			TResult<Customer> res = ctmServ.save(appBean.getToken(), p);
			if(res.getState().equals(TState.SUCCESS)) {
				Customer c = res.getValue();
				WebUser wu = session.get().getUser();
				wu.setPerson(c);
				wuServ.save(appBean.getToken(), wu);
				
				return new RestModel<>(new PersonModel(c), OK, "OK");
			}else
				return new RestModel<>(null, ERROR, res.getState().equals(TState.WARNING) 
						? res.getMessage() 
								: error.getValue());
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, ERROR, error.getValue());
		}
		
	}
	
	@GET
	@Path("/user")
	public RestModel<PersonModel> getUser(){
		
		try{
			NaturalPerson p = session.get().getUser().getPerson();
			if(p!=null)
				return new RestModel<>(new PersonModel(p), OK, "OK");
			else
				return new RestModel<>(null, WARN, "EMPTY");
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, ERROR, error.getValue());
		}
	}
}
