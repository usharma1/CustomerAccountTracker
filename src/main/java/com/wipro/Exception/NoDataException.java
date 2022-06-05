package com.wipro.Exception;

public class NoDataException extends Exception {

	String msg;

	public NoDataException(String msg) {
		super();
		this.msg = msg;
	}
}
