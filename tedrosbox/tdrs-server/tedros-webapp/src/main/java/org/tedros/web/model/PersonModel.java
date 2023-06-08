/**
 * 
 */
package org.tedros.web.model;

import java.util.Optional;

import org.tedros.extension.model.Contact;
import org.tedros.extension.model.ContactType;
import org.tedros.extension.model.Document;
import org.tedros.person.model.NaturalPerson;
import org.tedros.web.util.ApiUtils;

/**
 * @author Davis Gordon
 *
 */
public class PersonModel extends BaseModel<Long>{

	
	private static final long serialVersionUID = -6713872178067756959L;
	
	private String name;
	
	private String lastName;
	
	private String birthDate;
	
	private String sex;
	
	private String phone;
	
	private String identity;
	
	private String taxId;
	
	private AddressModel address;
	
	/**
	 * 
	 */
	public PersonModel() {
		// TODO Auto-generated constructor stub
	}
	
	public PersonModel(NaturalPerson p) {
		super(p.getId());
		this.name = p.getName();
		this.lastName = p.getLastName();
		if(p.getBirthDate()!=null)
			this.birthDate = ApiUtils.formatToDate(p.getBirthDate());
		if(p.getSex()!=null)
			this.sex = p.getSex().name();
		if(p.getDocuments()!=null) {
			Optional<Document> idtt = p.getDocuments()
					.stream().filter(d->d.getCode()!=null && d.getCode().equals("IDTT"))
					.findFirst();
			if(idtt.isPresent())
				this.identity = idtt.get().getValue();
			Optional<Document> txd = p.getDocuments()
					.stream().filter(d->d.getCode()!=null && d.getCode().equals("TXD"))
					.findFirst();
			if(txd.isPresent())
				this.taxId = txd.get().getValue();
		}
		if(p.getContacts()!=null) {
			Optional<Contact> ct = p.getContacts()
					.stream().filter(c->c.getType().equals(ContactType.PHONE))
					.findFirst();
			if(ct.isPresent())
				this.phone = ct.get().getValue();
			
		}
		if(p.getAddress()!=null)
			this.address = new AddressModel(p.getAddress());
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the birthDate
	 */
	public String getBirthDate() {
		return birthDate;
	}

	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the identity
	 */
	public String getIdentity() {
		return identity;
	}

	/**
	 * @param identity the identity to set
	 */
	public void setIdentity(String identity) {
		this.identity = identity;
	}

	/**
	 * @return the taxId
	 */
	public String getTaxId() {
		return taxId;
	}

	/**
	 * @param taxId the taxId to set
	 */
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	/**
	 * @return the address
	 */
	public AddressModel getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(AddressModel address) {
		this.address = address;
	}

}
