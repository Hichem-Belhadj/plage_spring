package fr.orsys.plage.exception;

public class NotExistingRoleException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotExistingRoleException(String message) {
		super(message);
	}
}
