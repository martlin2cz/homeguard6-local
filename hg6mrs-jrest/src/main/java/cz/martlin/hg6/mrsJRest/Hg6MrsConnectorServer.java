package cz.martlin.hg6.mrsJRest;

import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.jrest.impl.jarmil.JarmilWaiterShift;

public class Hg6MrsConnectorServer {
	private final Protocol protocol;

	private final JarmilWaiterShift shift;

	public Hg6MrsConnectorServer(Hg6Config config, Hg6MrsCmdsProcessor processor) {
		protocol = new Protocol(config.getConfig());
		shift = protocol.getWaiterShift(processor);
	}

	public void startWaiter() {
		shift.startWaiter();
	}

	public void stopWaiter() {
		shift.stopWaiter();
	}
}
