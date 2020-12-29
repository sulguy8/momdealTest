package kr.co.momdeal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@Slf4j
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String errCode;
	
	public ServiceException(Exception e) {
		super(e);
	}
	public ServiceException(String errorMsg) {
		super(errorMsg);
	}

	public ServiceException(String errorMsg,String errCode) {
		super(errorMsg);
		this.errCode = errCode;
	}
	public ServiceException(Exception e, String string) {
		super(e);
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	
}
