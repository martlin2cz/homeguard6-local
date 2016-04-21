package cz.martlin.hg6.config;

public class Hg6ConfigException extends Exception {

	private static final long serialVersionUID = -1123750809463023590L;

	public Hg6ConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	public Hg6ConfigException(String message) {
		super(message);
	}

	public Hg6ConfigException(Throwable cause) {
		super(cause);
	}

}
