package kr.co.momdeal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED, reason = "Invalid Client Token")
@Slf4j
public class TokenException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private String id;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public TokenException(Exception e) {
		super(e);
	}
	public TokenException(String errorMsg) {
		super(errorMsg);
		log.debug(this.getMessage());
	}
	public TokenException(String errorMsg,String id) {
		super(errorMsg);
		this.id = id;
		log.debug(this.getMessage());
	}
	public TokenException(Exception e, String string) {
		super(e);
		log.debug(e.getCause().getMessage());
	}
}
