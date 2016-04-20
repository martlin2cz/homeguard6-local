package cz.martlin.hg6.mrs.situation;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TestingSituationsCreator {

	public Situation createEmpty() {
		return new Situation();
	}

	public Situation createEmpty(GuardingStatus status) {
		return new Situation(status, null, null);
	}

	public Situation createBasic() {

		SimplifiedGuardReport report = new SimplifiedGuardReport(//
				cal(19, 4, 17, 14, 26), cal(19, 4, 20, 5, 6), cal(19, 4, 19, 30, 11), 10, 3, 420, "Testing 1");
		GuardingStatus status = GuardingStatus.STARTED;
		SimplifiedConfig config = new SimplifiedConfig(9);
		return new Situation(status, report, config);
	}

	protected Calendar cal(int day, int month, int hour, int minute, int second) {
		Calendar cal = new GregorianCalendar();

		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.HOUR, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);

		return cal;
	}
}