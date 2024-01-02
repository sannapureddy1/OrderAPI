package com.orderAPI.DTO;

import com.orderAPI.entities.Inventory;

public class ResponseDTO {
	private String message;
	private Inventory inventory;
	
	public ResponseDTO() {
		super();
	}


	public ResponseDTO(String message, Inventory inventory) {
		super();
		this.message = message;
		this.inventory = inventory;
	}
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	
}
