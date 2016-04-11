package cz.martlin.hg5;

import java.io.Serializable;

public class HomeGuardApp implements Serializable {
	private static final long serialVersionUID = -5651193670762995887L;

	private static final String APP_NAME = "Homeguard";
	private static final String VERSION = "6";
	private static final String AUTHOR = "m@rtlin";

	public static String getAppName() {
		return APP_NAME;
	}

	public static String getVersion() {
		return VERSION;
	}

	public static String getAuthor() {
		return AUTHOR;
	}

}
