package cz.martlin.hg6.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg5.logic.config.Configuration;
import cz.martlin.hg6.core.Homeguard;
import cz.martlin.hg6.coreJRest.Hg6CommandsProcessor;
import cz.martlin.hg6.coreJRest.Hg6Server;

public class Hg6ServiceMain {

	private static final Logger LOG = LoggerFactory.getLogger(Hg6ServiceMain.class);

	public static void main(String[] args) {
		Configuration config = new Configuration(); // TODO load?
		Homeguard homeguard = new Homeguard(config);
		Hg6CommandsProcessor processor = new Hg6CommandsProcessorImpl(homeguard);
		Hg6Server server = new Hg6Server(processor);

		server.startServer();
		LOG.info("Homeguard6 service is running");

	}

}
