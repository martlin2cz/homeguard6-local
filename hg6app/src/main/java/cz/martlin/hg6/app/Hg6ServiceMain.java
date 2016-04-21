package cz.martlin.hg6.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg5.logic.config.Configuration;
import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.hg6.core.Hg6Core;
import cz.martlin.hg6.coreJRest.Hg6CommandsProcessor;
import cz.martlin.hg6.coreJRest.Hg6CoreServer;

public class Hg6ServiceMain {

	private static final Logger LOG = LoggerFactory.getLogger(Hg6ServiceMain.class);

	public static void main(String[] args) {
		Configuration config = Hg6Config.get().getConfig();
		Hg6Core homeguard = new Hg6Core(config);
		Hg6CommandsProcessor processor = new Hg6CommandsProcessorImpl(homeguard);
		Hg6CoreServer server = new Hg6CoreServer(processor);

		server.startServer();
		LOG.info("Homeguard6 service is running");

	}

}
