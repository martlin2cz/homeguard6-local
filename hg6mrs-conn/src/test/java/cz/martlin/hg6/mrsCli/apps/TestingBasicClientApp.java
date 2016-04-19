package cz.martlin.hg6.mrsCli.apps;

import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg5.logic.data.GuardingReport;
import cz.martlin.hg5.logic.data.ReportItem;
import cz.martlin.hg6.mrsConn.Hg6CommandToLocal;
import cz.martlin.hg6.mrsConn.Hg6MrsClient;
import cz.martlin.hg6.mrsConn.Hg6MrsException;

public class TestingBasicClientApp {
	private static final Logger LOG = LoggerFactory.getLogger(TestingBasicClientApp.class);

	private static final int WAIT_SEC = 10;
	private static final int MS_IN_S = 1000;

	public static void main(String[] args) throws InterruptedException, Hg6MrsException {
		final String url = "http://localhost:9080/mrs/ws/hg6";

		System.out.println("Will send some status updates to " + url + ", between each will wait " + WAIT_SEC
				+ "seconds, so you can check in the browser (for instance) if it works.");

		Hg6MrsClient cli = new Hg6MrsClient(url);
		Thread.sleep(WAIT_SEC * MS_IN_S);

		// ok, ready, register to server
		Hg6CommandToLocal resp2 = cli.sendStopped(null);
		LOG.info("Stopped #1 -> \t " + resp2);
		Thread.sleep(WAIT_SEC * MS_IN_S);

		// okay, now continue with some stuff
		Hg6CommandToLocal resp3 = cli.sendStopped(null);
		LOG.info("Stopped #2 -> \t " + resp3);
		Thread.sleep(WAIT_SEC * MS_IN_S);

		GuardingReport report1 = createTestingReport(0);
		// ok, did not helped (assuming). So, someone started hg localy, notify
		// mrs about that
		Hg6CommandToLocal resp4 = cli.sendStarted(report1);
		LOG.info("Started #1 -> \t " + resp4);
		Thread.sleep(WAIT_SEC * MS_IN_S);

		GuardingReport report2 = createTestingReport(1);
		// ... keep running (with updated report) ...
		Hg6CommandToLocal resp5 = cli.sendStarted(report2);
		LOG.info("Started #2 -> \t " + resp5);
		Thread.sleep(WAIT_SEC * MS_IN_S);

		GuardingReport report3 = createTestingReport(2);
		// ... still keep running ...
		Hg6CommandToLocal resp6 = cli.sendStarted(report3);
		LOG.info("Started #3 -> \t " + resp6);
		Thread.sleep(WAIT_SEC * MS_IN_S);

		// okay, hg stopped now from local (with the last report)
		Hg6CommandToLocal resp7 = cli.sendStopped(report3);
		LOG.info("Stopped #1 -> \t " + resp7);
		Thread.sleep(WAIT_SEC * MS_IN_S);

		// ... still stopped ...
		Hg6CommandToLocal resp8 = cli.sendStopped(null);
		LOG.info("Stopped #2 -> \t " + resp8);
		Thread.sleep(WAIT_SEC * MS_IN_S);

		// ... stopped and stopped ...
		Hg6CommandToLocal resp9 = cli.sendStopped(report3);
		LOG.info("Stopped #3 -> \t " + resp9);
		Thread.sleep(WAIT_SEC * MS_IN_S);

		// ok, fuck it up. Die
		Hg6CommandToLocal resp10 = cli.sendClientDown(report3);
		LOG.info("Client down -> \t " + resp10);
		Thread.sleep(WAIT_SEC * MS_IN_S);

		System.out.println("Done.");
	}

	private static GuardingReport createTestingReport(int items) {
		GuardingReport report = new GuardingReport();

		report.setStarted(new GregorianCalendar(2016, 4, 15, 12, 05, 40));
		report.setDescription("Some testing report, yea");

		if (items > 0) {
			report.addReportItem(new ReportItem(new GregorianCalendar(2016, 4, 15, 12, 06, 21), //
					10, 100, 0.3, 0.5, 0.2, 0.1, 15, 10));
		}
		if (items > 1) {
			report.addReportItem(new ReportItem(new GregorianCalendar(2016, 4, 15, 12, 30, 44), //
					10, 100, 0.3, 0.5, 0.2, 0.1, 69, 42));
		}
		if (items > 2) {
			report.addReportItem(new ReportItem(new GregorianCalendar(2016, 4, 15, 12, 30, 44), //
					10, 100, 0.3, 0.5, 0.2, 0.1, 49, 1));
		}
		return report;
	}

}
