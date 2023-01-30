package fr.orsys.plage.exception;

public class InformationDeConnexionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2076217603936622443L;

	public InformationDeConnexionException() {
		super();
	}

	public InformationDeConnexionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InformationDeConnexionException(String message, Throwable cause) {
		super(message, cause);
	}

	public InformationDeConnexionException(String message) {
		super(message);
	}

	public InformationDeConnexionException(Throwable cause) {
		super(cause);
	}

}
