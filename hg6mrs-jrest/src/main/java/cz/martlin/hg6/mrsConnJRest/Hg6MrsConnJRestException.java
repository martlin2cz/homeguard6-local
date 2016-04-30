package cz.martlin.hg6.mrsConnJRest;

public class Hg6MrsConnJRestException extends Exception {

	private static final long serialVersionUID = 8410609139279153399L;

	public Hg6MrsConnJRestException(String message, Throwable cause) {
		super(message, cause);
	}

	public Hg6MrsConnJRestException(String message) {
		super(message);
	}

	public Hg6MrsConnJRestException(Throwable cause) {
		super(cause);
	}

}
