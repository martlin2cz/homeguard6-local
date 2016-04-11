package cz.martlin.hg6.db;

public class Hg6DbException extends Exception {

	private static final long serialVersionUID = -7449628339942439146L;

	public Hg6DbException(String message, Throwable cause) {
		super(message, cause);
	}

	public Hg6DbException(String message) {
		super(message);
	}

	public Hg6DbException(Throwable cause) {
		super(cause);
	}

}
