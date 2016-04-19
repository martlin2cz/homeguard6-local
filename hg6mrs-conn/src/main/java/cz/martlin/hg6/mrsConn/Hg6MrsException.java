package cz.martlin.hg6.mrsConn;

public class Hg6MrsException extends Exception {

	private static final long serialVersionUID = 6263522688960512534L;

	public Hg6MrsException(String message, Throwable cause) {
		super(message, cause);
	}

	public Hg6MrsException(String message) {
		super(message);
	}

	public Hg6MrsException(Throwable cause) {
		super(cause);
	}

}
