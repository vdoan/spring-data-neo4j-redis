package com.tutorial.rediscache.exception;


public class RecordNotFoundException extends BaseException {

	public RecordNotFoundException(String message) {
		super(message, ResultCode.RESOURCE_NOT_FOUND);
	}
}
