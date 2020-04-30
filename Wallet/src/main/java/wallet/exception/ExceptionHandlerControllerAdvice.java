package wallet.exception;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import wallet.model.ExceptionResponse;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ExceptionResponse handleResourceNotFound( UserNotFoundException exception,
			HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.setRequestedURI(request.getRequestURI());
		error.setStatus(HttpStatus.NOT_FOUND);
		return error;
	}
	
	@ExceptionHandler(AccountAlreadyExitsException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ExceptionResponse handleAccountExits(AccountAlreadyExitsException exception,
			HttpServletRequest request) {
		
		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.setRequestedURI(request.getRequestURI());
		return error;
	}
	
	@ExceptionHandler(LowBalanceException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ExceptionResponse handleAccountExits(LowBalanceException exception,
			HttpServletRequest request) {
		
		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.setRequestedURI(request.getRequestURI());
		return error;
	}
	
}
