package com.orderAPI.DTO;

public class AddressResponseDTO {
	
	private Long id;
	private String residence;
	private String locality;
	private String landmark;
	private String country;
	private String state;
	private String city;
	private String pincode;
	
	public AddressResponseDTO() {
		super();
	}
	
	public AddressResponseDTO(Long id, String residence, String locality, String landmark, String country, String state,
			String city, String pincode) {
		super();
		this.id = id;
		this.residence = residence;
		this.locality = locality;
		this.landmark = landmark;
		this.country = country;
		this.state = state;
		this.city = city;
		this.pincode = pincode;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getResidence() {
		return residence;
	}
	public void setResidence(String residence) {
		this.residence = residence;
	}
	
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	
	public String getLandmark() {
		return landmark;
	}
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	
}
