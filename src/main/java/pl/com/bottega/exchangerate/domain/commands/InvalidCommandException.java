package pl.com.bottega.exchangerate.domain.commands;


public class InvalidCommandException extends RuntimeException {

	private Validatable.ValidationErrors errors;
	private String message;

	public InvalidCommandException(Validatable.ValidationErrors errors) {
		this.errors = errors;
	}

	public InvalidCommandException(String message) {
		this.message = message;
	}

	public Validatable.ValidationErrors getErrors() {
		return errors;
	}

}
