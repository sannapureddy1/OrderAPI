package com.orderAPI.enumerations;

public enum OrderStatus{
	ORDER_PLACED,
	ORDER_PACKAGED,
	ORDER_SHIPPED,
	ORDER_DELIVERED,
	ORDER_CLOSED,
	ORDER_CANCELLED;
	
	private static final OrderStatus[] status = values();
	public OrderStatus next() {
		if(this.ordinal() < status.length) {
           return status[(this.ordinal() + 1) % status.length];
		}
		return null;
    }
}
