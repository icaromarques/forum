package br.com.example.forum.Forum.exception;

import br.com.example.forum.Forum.model.dto.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author icaroafonso
 *
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

	
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	
	@ExceptionHandler(value = {Exception.class ,ValidationException.class, IllegalArgumentException.class})
	public ResponseEntity<ApiError> exceptionHandler(Exception ex, HttpServletRequest request) {

		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		ApiError error;
		String requestPath = request.getRequestURL().toString();

		if (ex instanceof ValidationException){
		  ValidationException validationException = (ValidationException)ex;
		  error = new ApiError(validationException, requestPath);
		  httpStatus = validationException.getStatus();
		} else {
			error = new ApiError(httpStatus, ex.getMessage(), ex);
		}

		LOGGER.error(LocalDateTime.now().toString() +" - " + ex.getMessage());
		return new ResponseEntity<ApiError>(error,  httpStatus);
	}

}
