package kr.co.momdeal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundAjaxException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public NotFoundAjaxException(Exception e) {
		super(e);
	}
	public NotFoundAjaxException(String errorMsg) {
		super(errorMsg);
	}
}
