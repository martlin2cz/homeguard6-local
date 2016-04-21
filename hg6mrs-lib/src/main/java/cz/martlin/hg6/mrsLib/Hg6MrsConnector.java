package cz.martlin.hg6.mrsLib;

import cz.martlin.hg5.logic.config.Configuration;
import cz.martlin.hg6.coreJRest.Hg6CoreClient;
import cz.martlin.hg6.mrs.Hg6MrsException;
import cz.martlin.hg6.mrsConn.Hg6MrsClient;

public class Hg6MrsConnector {
	private final Hg6MrsClient mrs;
	private final Hg6CoreClient core;
	// TODO add db, do not?
	// TODO make wrapping, JRest-ing, class

	public Hg6MrsConnector(Configuration config) throws Hg6MrsException {
		mrs = new Hg6MrsClient(null); // TOO
		core = new Hg6CoreClient();
	}

	public void start() {
		System.err.println("TODOOOOO...."); // TODO
	}

	public void stop() {
		System.err.println("TODOOOOO...."); // TODO
	}
}
