package com.org.bank.dto;

public enum OperationType {

	SAVE("SAVE"), WITHDRAWAL("WITHDRAWAL");
	
	public String value;
	
	private OperationType(String value) {
		this.value = value;
	}
}
