package cz.martlin.hg6.coreJRest.v0;

import cz.martlin.jrest.guest.JRestGuest;
import cz.martlin.jrest.protocol.GuestProtocol;
import cz.martlin.jrest.protocol.WaiterProtocol;
import cz.martlin.jrest.protocol.protocols.simple.SimpleGuestProtocolImpl;
import cz.martlin.jrest.protocol.protocols.simple.SimpleWaiterProtocolImpl;
import cz.martlin.jrest.waiter.JRestWaiter;
import cz.martlin.jrest.waiter.JRestWaiterShift;
import cz.martlin.jrest.waiter.RequestHandler;

public class Protocol {

	private static final int PORT = 65777;

	public static final String START_COMMAND = "start";
	public static final String STOP_COMMAND = "stop";

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

	public JRestWaiter getWaiter(SomeAppCommandsProcessor processor) {

		RequestHandler handler = new SomeAppHandlerWithProcessor(processor);
		WaiterProtocol protocol = new SimpleWaiterProtocolImpl(PORT, handler);

		return new JRestWaiter(protocol);
	}

	public JRestWaiterShift getWaitersShift(SomeAppCommandsProcessor processor) {

		JRestWaiter waiter = getWaiter(processor);
		return new JRestWaiterShift(waiter);
	}
}
