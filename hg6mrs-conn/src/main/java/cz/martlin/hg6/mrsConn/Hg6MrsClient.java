package cz.martlin.hg6.mrsConn;

import java.net.URL;

import cz.martlin.hg5.logic.data.GuardingReport;
import cz.martlin.hg6.mrsConn.tools.NetworkTools;

public class Hg6MrsClient {
	private static final NetworkTools TOOLS = new NetworkTools();

	private final String url;

	public Hg6MrsClient(String url) {
		this.url = url;
	}

	public Hg6CommandToLocal sendStarted(GuardingReport report) throws Hg6MrsException {
		return sendWithReport(Protocol.STARTED_PATH, report);
	}

	public Hg6CommandToLocal sendStopped(GuardingReport report) throws Hg6MrsException {
		return sendWithReport(Protocol.STOPPED_PATH, report);
	}

	public Hg6CommandToLocal sendFailed(String message) throws Hg6MrsException {
		return sendWith(Protocol.FAILED_PATH, //
				Protocol.MESSAGE_PARAM_NAME, message);
	}

	public Hg6CommandToLocal sendClientDown(GuardingReport report) throws Hg6MrsException {
		return sendWithReport(Protocol.CLIENT_DOWN_PATH, report);
	}

	private Hg6CommandToLocal sendWithReport(String path, GuardingReport report) throws Hg6MrsException {

		if (report != null) {
			return sendWith(path, //
					Protocol.STARTED_AT_PARAM_NAME, TOOLS.formatCalendar(report.getStartedAt()), //
					Protocol.STOPPED_AT_PARAM_NAME, TOOLS.formatCalendar(report.getStoppedAt()), //
					Protocol.LAST_WARN_AT_PARAM_NAME, TOOLS.formatCalendar(report.getLastWarningAt()), //
					Protocol.DESCRIPTION_PARAM_NAME, report.getDescription(), //
					Protocol.ITEMS_COUNT_PARAM_NAME, report.getItemsCount(), //
					Protocol.WARNINGS_COUNT_PARAM_NAME, report.getWarningsCount(), //
					Protocol.CRITICAL_COUNT_PARAM_NAME, report.getCriticalCount()//
			);
		} else {
			return sendWith(path);
		}
	}

	private Hg6CommandToLocal sendWith(String path, Object... params) throws Hg6MrsException {
		return send(path, params);
	}

	private Hg6CommandToLocal send(String path, Object[] params) throws Hg6MrsException {
		try {
			URL url = TOOLS.createGetUrl(this.url, path, params);
			String response = TOOLS.connectAndGetResponse(url);
			return Hg6CommandToLocal.valueOf(response);
		} catch (Exception e) {
			throw new Hg6MrsException("Cannot send to path " + path, e);
		}
	}

}
