/**
 * 
 */
package org.tedros.web.model;

import org.tedros.location.model.Address;

/**
 * @author Davis Gordon
 *
 */
public class AddressModel extends BaseModel<Long> {

	private static final long serialVersionUID = -8220037146982788050L;

	private ValueModel<Long> streetType;
	
	private String publicPlace;
	
	private String complement;

	private String neighborhood;
	
	private ValueModel<Long> country;
	
	private ValueModel<Long> adminArea;
	
	private ValueModel<Long> city;
	
	private String code;
	/**
	 * 
	 */
	public AddressModel() {
	}
	
	public AddressModel(Address a) {
		if(a.getStreetType()!=null)
			this.streetType = new ValueModel<>(a.getStreetType().getId(), a.getStreetType().getName());
		this.publicPlace = a.getPublicPlace();
		this.complement = a.getComplement();
		this.neighborhood = a.getNeighborhood();
		this.code = a.getCode();
		if(a.getCountry()!=null)
			this.country = new ValueModel<>(a.getCountry().getId(), a.getCountry().getName());
		if(a.getAdminArea()!=null)
			this.adminArea = new ValueModel<>(a.getAdminArea().getId(), a.getAdminArea().getName());
		if(a.getCity()!=null)
			this.city = new ValueModel<>(a.getCity().getId(), a.getCity().getName());
	}

	public AddressModel(Long id, ValueModel<Long> streetType, String publicPlace, String complement, String neighborhood,
			ValueModel<Long> country, ValueModel<Long> adminArea, ValueModel<Long> city, String code) {
		super(id);
		this.streetType = streetType;
		this.publicPlace = publicPlace;
		this.complement = complement;
		this.neighborhood = neighborhood;
		this.country = country;
		this.adminArea = adminArea;
		this.city = city;
		this.code = code;
	}

	/**
	 * @return the streetType
	 */
	public ValueModel<Long> getStreetType() {
		return streetType;
	}

	/**
	 * @param streetType the streetType to set
	 */
	public void setStreetType(ValueModel<Long> streetType) {
		this.streetType = streetType;
	}

	/**
	 * @return the publicPlace
	 */
	public String getPublicPlace() {
		return publicPlace;
	}

	/**
	 * @param publicPlace the publicPlace to set
	 */
	public void setPublicPlace(String publicPlace) {
		this.publicPlace = publicPlace;
	}

	/**
	 * @return the complement
	 */
	public String getComplement() {
		return complement;
	}

	/**
	 * @param complement the complement to set
	 */
	public void setComplement(String complement) {
		this.complement = complement;
	}

	/**
	 * @return the neighborhood
	 */
	public String getNeighborhood() {
		return neighborhood;
	}

	/**
	 * @param neighborhood the neighborhood to set
	 */
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	/**
	 * @return the country
	 */
	public ValueModel<Long> getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(ValueModel<Long> country) {
		this.country = country;
	}

	/**
	 * @return the adminArea
	 */
	public ValueModel<Long> getAdminArea() {
		return adminArea;
	}

	/**
	 * @param adminArea the adminArea to set
	 */
	public void setAdminArea(ValueModel<Long> adminArea) {
		this.adminArea = adminArea;
	}

	/**
	 * @return the city
	 */
	public ValueModel<Long> getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(ValueModel<Long> city) {
		this.city = city;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

}
