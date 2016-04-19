package cz.martlin.hg6.mrsCli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg5.logic.data.GuardingReport;
import cz.martlin.hg6.mrsCli.apps.TestingBasicClientApp;
import cz.martlin.hg6.mrsConn.Hg6CommandToLocal;
import cz.martlin.hg6.mrsConn.Hg6MrsClient;
import cz.martlin.hg6.mrsConn.Hg6MrsException;

public class TestingLoopClientEmulator {
	private static final Logger LOG = LoggerFactory.getLogger(TestingBasicClientApp.class);
	private static final int MS_IN_S = 1000;

	private final String url;

	public TestingLoopClientEmulator(String url) {
		this.url = url;
	}

	public void run(int iterations, int interval) throws Hg6MrsException {
		Hg6MrsClient cli = new Hg6MrsClient(url);

		boolean nextlySendStart = false;
		GuardingReport report = null;

		LOG.info("Simple testing loop client emulator starting to do " + iterations + "iterations with interval "
				+ interval + " seconds");

		for (int i = 0; i < iterations; i++) {
			Hg6CommandToLocal resp;

			if (nextlySendStart) {
				resp = cli.sendStarted(report);
				LOG.info(i + ": sent STARTED, recieved back: " + resp);
			} else {
				resp = cli.sendStopped(report);
				LOG.info(i + ": sent STOPPED, recieved back: " + resp);
			}

			if (resp.equals(Hg6CommandToLocal.START)) {
				LOG.info("Okay, I am starting now");
				nextlySendStart = true;
			}
			if (resp.equals(Hg6CommandToLocal.STOP)) {
				LOG.info("Okay, I am now stoped");
				nextlySendStart = false;
			}

			try {
				Thread.sleep(interval * MS_IN_S);
			} catch (InterruptedException e) {
			}
		}

		LOG.info("Done all " + iterations + " iterations");
	}
}
