package com.orderAPI.DTO;

import java.util.List;

public class AddressListResponseDTO {
	private Long id;
	private String userName;
	private List<AddressResponseDTO> addressList;
	
	public AddressListResponseDTO() {
		super();
	}

	public AddressListResponseDTO(Long id, String userName, List<AddressResponseDTO> addressList) {
		super();
		this.id = id;
		this.userName = userName;
		this.addressList = addressList;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<AddressResponseDTO> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<AddressResponseDTO> addressList) {
		this.addressList = addressList;
	}

}
