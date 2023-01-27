package fr.orsys.plage.exception;

public class NotExistingFileException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotExistingFileException(String message) {
		super(message);
	}
}
