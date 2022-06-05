package com.wipro.Exception;

public class ResourceNotFoundException extends Exception {

	String msg;

	public ResourceNotFoundException(String msg) {
		super();
		this.msg = msg;
	}
	
}
