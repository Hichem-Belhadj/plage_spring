package fr.orsys.plage.exception;

public class NotExistingUtilisateurException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotExistingUtilisateurException(String message) {
		super(message);
	}
}
