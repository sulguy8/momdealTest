package kr.co.momdeal.config;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import kr.co.momdeal.exception.NotFoundAjaxException;
import kr.co.momdeal.exception.ServiceException;
import kr.co.momdeal.exception.TokenException;
import kr.co.momdeal.vo.common.ErrorVO;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@RestController
@Slf4j
public class ExceptionHandlerController {
	
	/*
	 * JSP요청이 아닌 AJAX요청시에
	 * 없는 리소스 요청할 경우 service에서 
	 * throw new NotFoundAjaxException(메세지);
	 */
	
	@ExceptionHandler(NotFoundAjaxException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public ResponseEntity<ErrorVO> requestHandlingNoHandlerFound(NotFoundAjaxException ex,WebRequest req) {
		log.error("excetion=>{}",ex);
		ErrorVO errorDetails = new ErrorVO(new Date(), ex.getMessage(),
		        req.getDescription(false));
		    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(ConstraintViolationException.class)
	public void constraintViolationException(HttpServletResponse response) throws IOException {
	    response.sendError(HttpStatus.BAD_REQUEST.value());
	}
	

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<ErrorVO> requestHandlingServiceException(ServiceException ex,WebRequest req) throws IOException {
		log.error("excetion=>{}",ex);
		ErrorVO errorDetails = new ErrorVO(new Date(), ex.getMessage(),
		        req.getDescription(false),ex.getErrCode());
		    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(TokenException.class)
	public ResponseEntity<ErrorVO> requestHandlingTokenException(TokenException ex,WebRequest req) throws IOException {
		log.error("excetion=>{}",ex);
		ErrorVO errorDetails = new ErrorVO(new Date(), ex.getMessage(),
		        req.getDescription(false),"err01",ex.getId());
		    return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
	}
}
