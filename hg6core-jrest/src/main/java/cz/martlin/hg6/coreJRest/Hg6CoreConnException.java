package cz.martlin.hg6.coreJRest;

public class Hg6CoreConnException extends Exception {
	private static final long serialVersionUID = -7446821959496846151L;

	public Hg6CoreConnException(String message) {
		super(message);
	}

	public Hg6CoreConnException(Throwable cause) {
		super(cause);
	}

	public Hg6CoreConnException(String message, Throwable cause) {
		super(message, cause);
	}

}
