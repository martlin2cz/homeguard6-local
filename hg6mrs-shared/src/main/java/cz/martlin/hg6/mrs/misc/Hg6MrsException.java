package cz.martlin.hg6.mrs.misc;

public class Hg6MrsException extends Exception {

	private static final long serialVersionUID = 7056132945659620869L;

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
