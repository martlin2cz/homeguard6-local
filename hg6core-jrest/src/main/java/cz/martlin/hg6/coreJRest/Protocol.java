package cz.martlin.hg6.coreJRest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import cz.martlin.jrest.guest.JRestGuest;
import cz.martlin.jrest.protocol.GuestProtocol;
import cz.martlin.jrest.protocol.WaiterProtocol;
import cz.martlin.jrest.protocol.protocols.simple.SimpleGuestProtocolImpl;
import cz.martlin.jrest.protocol.protocols.simple.SimpleWaiterProtocolImpl;
import cz.martlin.jrest.waiter.JRestWaiter;
import cz.martlin.jrest.waiter.JRestWaiterShift;
import cz.martlin.jrest.waiter.RequestHandler;

public class Protocol {

	private static final int PORT = 6_417_1;

	public static final String START_COMMAND = "start";
	public static final String STOP_COMMAND = "stop";
	public static final String IS_RUNNING_COMMAND = "is-running";
	public static final String SIMPLE_INFO_COMMAND = "simple-info";
	public static final String CURRENT_STARTED_COMMAND = "current-report-started-at";
	public static final String CONFIG_CHANGED_COMMAND = "config-changed";
	// TODO something like: "change-config set-interval 40?"

	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");

	public static final String STOPPED_TEXT = "stopped";
	public static final String STARTED_TEXT = "started";
	public static final String UPDATED_TEXT = "updated";
	public static final String FAILED_TEXT = "failed";

	public Protocol() {
	}

	public JRestGuest getGuest() {
		GuestProtocol protocol = new SimpleGuestProtocolImpl(PORT);
		return new JRestGuest(protocol);
	}

	public JRestGuest getGuest(String host) {
		GuestProtocol protocol = new SimpleGuestProtocolImpl(PORT, host);
		return new JRestGuest(protocol);
	}

	public JRestWaiter getWaiter(Hg6CommandsProcessor processor) {

		RequestHandler handler = new Hg6CoreHandlerWithProcessor(processor);
		WaiterProtocol protocol = new SimpleWaiterProtocolImpl(PORT, handler);

		return new JRestWaiter(protocol);
	}

	public JRestWaiterShift getWaitersShift(Hg6CommandsProcessor processor) {

		JRestWaiter waiter = getWaiter(processor);
		return new JRestWaiterShift(waiter);
	}
}
