package fr.orsys.plage.exception;

public class NotAutorizedUtilisateurException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotAutorizedUtilisateurException(String message) {
		super(message);
	}
}
