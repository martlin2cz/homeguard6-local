package cz.martlin.hg6.mrsCli.apps;

import cz.martlin.hg6.mrsCli.TestingLoopClientEmulator;
import cz.martlin.hg6.mrsConn.Hg6MrsException;

public class TestingClientEmulApp {

	public static void main(String[] args) throws Hg6MrsException {

		final String url = "http://localhost:9080/mrs/ws/hg6";
		final int iterations = 100;
		final int interval = 5;

		TestingLoopClientEmulator emul = new TestingLoopClientEmulator(url);
		emul.run(iterations, interval);

	}

}
