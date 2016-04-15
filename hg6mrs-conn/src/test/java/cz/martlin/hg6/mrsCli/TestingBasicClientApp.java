package cz.martlin.hg6.mrsCli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg6.mrsConn.Hg6CommandToLocal;
import cz.martlin.hg6.mrsConn.Hg6MrsClient;

public class TestingBasicClientApp {
	private static final Logger LOG = LoggerFactory.getLogger(TestingBasicClientApp.class);

	private static final int WAIT_SEC = 10;
	private static final int MS_IN_S = 1000;

	public static void main(String[] args) throws InterruptedException {
		final String url = "http://localhost:9080/mrs/ws/hg6";

		System.out.println("Will send some status updates to " + url + ", between each will wait " + WAIT_SEC
				+ "seconds, so you can check in the browser (for instance) if it works.");

		Hg6MrsClient cli = new Hg6MrsClient(url);

		Hg6CommandToLocal cmd1 = cli.sendStarted();
		LOG.info("Start sent with response: \t " + cmd1);
		Thread.sleep(WAIT_SEC * MS_IN_S);

		Hg6CommandToLocal cmd2 = cli.sendStopped();
		LOG.info("Stop sent with response: \t " + cmd2);
		Thread.sleep(WAIT_SEC * MS_IN_S);

		Hg6CommandToLocal cmd3 = cli.sendStartFailed();
		LOG.info("Stop failed sent with response: \t " + cmd3);
		Thread.sleep(WAIT_SEC * MS_IN_S);

		Hg6CommandToLocal cmd4 = cli.sendStopFailed();
		LOG.info("Start failed sent with response: \t " + cmd4);
		Thread.sleep(WAIT_SEC * MS_IN_S);

		Hg6CommandToLocal cmd5 = cli.sendClientDown();
		LOG.info("Client down sent with response: \t " + cmd5);

		System.out.println("Done.");
	}

}
