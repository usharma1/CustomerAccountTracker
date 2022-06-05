package com.wipro.Exception;

public class NotFoundException extends Exception {

	String msg;

	public NotFoundException(String msg) {
		super();
		this.msg = msg;
	}
}
