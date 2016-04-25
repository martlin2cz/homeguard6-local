package cz.martlin.hg6.coreJRest;

import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.jrest.impl.jarmil.JarmilWaiterShift;

public class Hg6CoreServer {

	private final JarmilWaiterShift shift;

	public Hg6CoreServer(Hg6Config config, Hg6CoreCmdsProcessor processor) {
		Protocol protocol = new Protocol(config.getConfig());
		this.shift = protocol.getWaiterShift(processor);
	}

	public void startServer() {
		shift.startWaiter();
	}

	public void stopServer() {
		shift.stopWaiter();
	}

}
