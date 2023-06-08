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
import org.tedros.env.ejb.controller.IWebUserController;
import org.tedros.env.entity.WebUser;
import org.tedros.extension.ejb.controller.IAdminAreaController;
import org.tedros.extension.ejb.controller.ICityController;
import org.tedros.extension.ejb.controller.ICountryController;
import org.tedros.extension.ejb.controller.IExtensionDomainController;
import org.tedros.extension.model.Address;
import org.tedros.extension.model.AdminArea;
import org.tedros.extension.model.City;
import org.tedros.extension.model.Contact;
import org.tedros.extension.model.ContactType;
import org.tedros.extension.model.Country;
import org.tedros.extension.model.Document;
import org.tedros.extension.model.StreetType;
import org.tedros.person.domain.Sex;
import org.tedros.person.ejb.controller.ICustomerController;
import org.tedros.person.model.Customer;
import org.tedros.person.model.PersonEvent;
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
	private IExtensionDomainController sttServ;
	
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
			@FormParam("streetType") Long  streetType,
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
			PersonEvent event = new PersonEvent();
			if(p==null) {
				p = new Customer();
				event.setName("CRIADO/WEB");
				event.setDescription("Criado atraves do painel empresarial na plataforma web.");
			}else if(!p.isNew()) {
				TResult<Customer> r = this.ctmServ.findById(appBean.getToken(), p);
				if(r.getState().equals(TState.SUCCESS))
					p = r.getValue();
				event.setName("ALTERADO/WEB");
				event.setDescription("Alterado atraves do painel empresarial na plataforma web.");
			}
			
			if(p.getEvents()==null)
				p.setEvents(new HashSet<>());
			p.getEvents().add(event);
			
			p.setName(name);
			p.setLastName(lastName);
			if(StringUtils.isNotBlank(birthDate)) {
				Date dt = ApiUtils.convertToDate(birthDate);
				p.setBirthDate(dt);
			}
			if(StringUtils.isNotBlank(sex)) {
				p.setSex(Sex.valueOf(sex));
			}
			
			if(publicPlace!=null && streetType!=null && country!=null) {
				Address adrs = p.getAddress()!=null ? p.getAddress() : new Address();
				if(adrs.isNew())
					p.setAddress(adrs);
				adrs.setPublicPlace(publicPlace);
				adrs.setComplement(complement);
				adrs.setNeighborhood(neighborhood);
				adrs.setCode(code);
			
				StreetType st = new StreetType();
				st.setId(streetType);
				TResult<StreetType> rst = sttServ.findById(appBean.getToken(), st);
				st = rst.getValue();
				adrs.setStreetType(st);
			
				Country cntr = new Country();
				cntr.setId(country);
				TResult<Country> cr = cntServ.findById(appBean.getToken(), cntr);
				cntr = cr.getValue();
				adrs.setCountry(cntr);
			
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
				TResult<WebUser> res1 = wuServ.save(appBean.getToken(), wu);
				if(res1.getState().equals(TState.SUCCESS)) {
					session.get().setUser(res1.getValue());
				}
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
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/user")
	public RestModel<PersonModel> getUser(){
		
		try{
			Customer p = (Customer) session.get().getUser().getPerson();
			if(p!=null) {
				TResult<Customer> r = this.ctmServ.findById(appBean.getToken(), p);
				if(r.getState().equals(TState.SUCCESS))
					p = r.getValue();
				return new RestModel<>(new PersonModel(p), OK, "OK");
			}else
				return new RestModel<>(null, WARN, "EMPTY");
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, ERROR, error.getValue());
		}
	}
}
