package com.util;

public class OpResult {
	/**
	 * This class is used in tandem with jackson's objectmapper to make json literals
	 * passing true to the constructor will make a json string like this:
	 * {Success: true}
	 * 
	 * And false will make this:
	 * {Success: false}
	 */
	private boolean Success;

	public OpResult(boolean success) {
		super();
		Success = success;
	}
	
	public boolean isSuccess() {
		return Success;
	}

	public void setSuccess(boolean success) {
		Success = success;
	}
}