package cz.martlin.hg6.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.hg6.core.Hg6Core;
import cz.martlin.hg6.coreJRest.Hg6CoreCmdsProcessor;
import cz.martlin.hg6.coreJRest.Hg6CoreServer;
import cz.martlin.hg6.coreJRest.TestingHg6CommProcImpl;

public class Hg6ServiceMain {

	private static final Logger LOG = LoggerFactory.getLogger(Hg6ServiceMain.class);

	public static void main(String[] args) {
		Hg6Config config = Hg6Config.get();
		Hg6Core homeguard = new Hg6Core(config.getConfig());

		// Hg6CommandsProcessor processor = new
		// Hg6CommandsProcessorImpl(homeguard);
		Hg6CoreCmdsProcessor processor = new TestingHg6CommProcImpl();

		Hg6CoreServer server = new Hg6CoreServer(config, processor);

		server.startServer();
		LOG.info("Homeguard6 service is running");

	}

}
