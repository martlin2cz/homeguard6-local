package cz.martlin.hg6.coreJRest;

import cz.martlin.jrest.waiter.JRestWaiterShift;

public class Hg6Server {

	private final JRestWaiterShift shift;

	public Hg6Server(Hg6CommandsProcessor processor) {
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
