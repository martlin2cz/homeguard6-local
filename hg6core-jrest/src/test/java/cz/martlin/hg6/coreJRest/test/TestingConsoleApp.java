package cz.martlin.hg6.coreJRest.test;

import cz.martlin.hg6.coreJRest.v0.HG6Client;
import cz.martlin.hg6.coreJRest.v0.Hg6CommandsProcessor;
import cz.martlin.hg6.coreJRest.v0.Hg6Server;

public class TestingConsoleApp {

	public static void main(String[] args) {

		Hg6CommandsProcessor processor = new FakeHG6CmdsProcImpl();
		Hg6Server server = new Hg6Server(processor);

		server.startServer();

		HG6Client client = new HG6Client();

		client.start();

		try {
			Thread.sleep(2 * 1000);
		} catch (InterruptedException e) {
		}

		client.stop();

		server.stopServer();

	}

}
