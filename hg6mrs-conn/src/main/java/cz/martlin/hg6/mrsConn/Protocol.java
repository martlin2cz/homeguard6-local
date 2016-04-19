package cz.martlin.hg6.mrsConn;

import java.util.Calendar;

import cz.martlin.hg6.mrsConn.tools.NetworkTools;

public class Protocol {
	public static final String CURRENT_STATUS_PATH = "/current-status";

	public static final String COMMAND_PARAM_NAME = "type";
	public static final String COMMAND_PATH = "/{" + COMMAND_PARAM_NAME + "}";

	public static final String STARTED_PATH = "/started";
	public static final String FAILED_PATH = "/failed";
	public static final String STOPPED_PATH = "/stopped";
	public static final String CLIENT_DOWN_PATH = "/client-down";

	public static final String STARTED_AT_PARAM_NAME = "startedAt";
	public static final String STOPPED_AT_PARAM_NAME = "stoppedAt";
	public static final String LAST_WARN_AT_PARAM_NAME = "lastWarningAt";
	public static final String CRITICAL_COUNT_PARAM_NAME = "criticalCount";
	public static final String WARNINGS_COUNT_PARAM_NAME = "warningsCount";
	public static final String ITEMS_COUNT_PARAM_NAME = "itemsCount";
	public static final String DESCRIPTION_PARAM_NAME = "description";

	public static final String MESSAGE_PARAM_NAME = "message";
	public static final String INTERVAL_PARAM_NAME = "interval";

	private final NetworkTools tools = new NetworkTools();

	public Protocol() {
		super();
	}

	public String formatCalendar(Calendar calendar) {
		return tools.formatCalendar(calendar);
	}

	public Calendar parseCalendar(String string) {
		return tools.parseCalendar(string);
	}

	
	
}
