package fr.orsys.plage.exception;

public class UtilisateurNonAuthorise extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UtilisateurNonAuthorise(String message) {
		super(message);
	}
}
