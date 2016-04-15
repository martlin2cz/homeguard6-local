package cz.martlin.hg6.mrsLib;

import cz.martlin.hg5.logic.config.Configuration;
import cz.martlin.hg6.coreJRest.Hg6CoreClient;
import cz.martlin.hg6.mrsConn.Hg6MrsClient;

public class Hg6MrsConnector {
	private final Hg6MrsClient mrs;
	private final Hg6CoreClient core;

	public Hg6MrsConnector(Configuration config) {
		mrs = new Hg6MrsClient(config.getMRSurl());
		core = new Hg6CoreClient();
	}

	public void start() {
		System.err.println("TODOOOOO...."); // TODO
	}

	public void stop() {
		System.err.println("TODOOOOO...."); // TODO
	}
}
