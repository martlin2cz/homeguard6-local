package cz.martlin.hg6.coreJRest.test;

import cz.martlin.hg6.coreJRest.v0.SomeAppClient;
import cz.martlin.hg6.coreJRest.v0.SomeAppCommandsProcessor;
import cz.martlin.hg6.coreJRest.v0.SomeAppServer;

public class SomeAppTestingServerApp {

	public static void main(String[] args) {

		SomeAppCommandsProcessor processor = new SomeAppCmdsProcImpl();
		SomeAppServer server = new SomeAppServer(processor);

		server.startServer();

		SomeAppClient client = new SomeAppClient();

		client.start();

		try {
			Thread.sleep(2 * 1000);
		} catch (InterruptedException e) {
		}

		client.stop();

		server.stopServer();

	}

}
