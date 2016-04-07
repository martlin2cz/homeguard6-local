package cz.martlin.hg6.app.v0;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg6.core.v0.SomeApplication;
import cz.martlin.hg6.coreJRest.v0.SomeAppCommandsProcessor;
import cz.martlin.hg6.coreJRest.v0.SomeAppServer;

public class SomeAppEntry {
	private static final Logger log = LoggerFactory.getLogger(SomeAppEntry.class);

	public static void main(String[] args) {
		SomeApplication app = new SomeApplication();

		SomeAppCommandsProcessor processor = new Hg6CommandsProcessorImpl(app);
		SomeAppServer server = new SomeAppServer(processor);

		server.startServer();

		log.info("Some app server running");
	}

}
