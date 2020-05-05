package wallet.exception;

public class AccountAlreadyExitsException extends RuntimeException{
	public AccountAlreadyExitsException(String message) {
		super(message);
	}
}
