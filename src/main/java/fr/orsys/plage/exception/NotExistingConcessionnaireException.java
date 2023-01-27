package fr.orsys.plage.exception;

public class NotExistingConcessionnaireException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotExistingConcessionnaireException(String message) {
		super(message);
	}
}
