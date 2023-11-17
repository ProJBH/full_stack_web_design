package com.bohuajia.o2o.dto;

/**
 * Encapsulates the json object and uses it for all returned results
 */
public class Result<T> {

	private boolean success;// indicate success or not

	private T data;// return data when success 

	private String errorMsg;// error message

	private int errorCode;

	public Result() {
	}

	// Constructor on success
	public Result(boolean success, T data) {
		this.success = success;
		this.data = data;
	}

	// Constructor on failed
	public Result(boolean success, int errorCode, String errorMsg) {
		this.success = success;
		this.errorMsg = errorMsg;
		this.errorCode = errorCode;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
