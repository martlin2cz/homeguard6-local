package cz.martlin.hg6.mrs.test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import cz.martlin.hg6.mrs.situation.SimplifiedConfig;
import cz.martlin.hg6.mrs.situation.SimplifiedGuardReport;
import cz.martlin.hg6.mrs.situation.Situation;
import cz.martlin.hg6.mrs.situation.Status;

public class TestingSituationsCreator {

	private static final int YEAR = 2016;

	public Situation createEmpty() {
		return new Situation();
	}

	public Situation createBasic() {

		SimplifiedGuardReport report = new SimplifiedGuardReport(//
				cal(19, 4, 17, 14, 26), cal(19, 4, 20, 5, 6), cal(19, 4, 19, 30, 11), 10, 3, 420, "Testing 1");

		SimplifiedConfig config = new SimplifiedConfig();
		config.setInterval(9);

		Status status = new Status();

		status.setCoreRunning(false);
		status.setMrsConnRunning(true);

		return new Situation(status, report, config);
	}

	public Situation createNewerBasic() {

		SimplifiedGuardReport report = new SimplifiedGuardReport(//
				cal(1, 5, 17, 51, 50), cal(18, 41, 14, 11, 18), null, 0, 0, 99, "Testing 2");

		SimplifiedConfig config = new SimplifiedConfig();
		config.setInterval(18);

		Status status = new Status();

		status.setCoreRunning(true);
		status.setMrsConnRunning(true);

		return new Situation(status, report, config);
	}

	protected Calendar cal(int day, int month, int hour, int minute, int second) {
		Calendar cal = new GregorianCalendar(YEAR, month, day, hour, minute, second);

		return cal;
	}
}
