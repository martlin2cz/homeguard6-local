package cz.martlin.mrs.hg6.comm;

import java.util.Calendar;

import cz.martlin.hg6.mrsConn.RemoteGuardReport;

public class SituationOnLocal {

	private final RemoteGuardReport report;
	private final RemoteConfig config;

	public SituationOnLocal(RemoteGuardReport report, RemoteConfig config) {
		super();
		this.report = report;
		this.config = config;
	}

	public RemoteGuardReport getReport() {
		return report;
	}

	public RemoteConfig getConfig() {
		return config;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((config == null) ? 0 : config.hashCode());
		result = prime * result + ((report == null) ? 0 : report.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SituationOnLocal other = (SituationOnLocal) obj;
		if (config == null) {
			if (other.config != null)
				return false;
		} else if (!config.equals(other.config))
			return false;
		if (report == null) {
			if (other.report != null)
				return false;
		} else if (!report.equals(other.report))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SituationOnLocal [report=" + report + ", config=" + config + "]";
	}

	public static SituationOnLocal create(Calendar startedAt, Calendar stoppedAt, Calendar lastWarnAt,
			Integer criticalCount, Integer warningsCount, Integer itemsCount, String description, Integer interval) {

		RemoteGuardReport report = new RemoteGuardReport(startedAt, stoppedAt, lastWarnAt, warningsCount, criticalCount,
				itemsCount, description);

		RemoteConfig config = new RemoteConfig(interval);

		return new SituationOnLocal(report, config);
	}

}
