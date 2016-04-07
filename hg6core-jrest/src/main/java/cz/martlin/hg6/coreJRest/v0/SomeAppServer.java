package cz.martlin.hg6.coreJRest.v0;

import cz.martlin.jrest.waiter.JRestWaiterShift;

public class SomeAppServer {

	private final JRestWaiterShift shift;

	public SomeAppServer(SomeAppCommandsProcessor processor) {
		Protocol protocol = new Protocol();
		this.shift = protocol.getWaitersShift(processor);
	}

	public void startServer() {
		shift.startWaiter();
	}

	public void stopServer() {
		shift.stopWaiter();
	}

}
