package cz.martlin.hg6.coreJRest;

import cz.martlin.hg6.config.Configuration;
import cz.martlin.jrest.impl.jarmil.JarmilWaiterShift;
import cz.martlin.jrest.impl.jarmil.SingleJarmilGuest;
import cz.martlin.jrest.impl.jarmil.protocol.JarmilGuestProtocol;
import cz.martlin.jrest.impl.jarmil.protocol.JarmilWaiterProtocol;
import cz.martlin.jrest.impl.jarmil.target.TargetOnGuest;
import cz.martlin.jrest.impl.jarmil.target.TargetOnWaiter;
import cz.martlin.jrest.impl.jarmil.targets.guest.ObjectOnGuestTarget;
import cz.martlin.jrest.impl.jarmil.targets.waiter.ObjectOnWaiterTarget;

public class Protocol {

	private static final String NAME = "hg6core";

	public static final String START_METHOD = "start";
	public static final String STOP_METHOD = "stop";
	public static final String IS_RUNNING_METHOD = "isRunning";
	public static final String SIMPLE_INFO_METHOD = "simpleInfo";
	public static final String CURRENT_STARTED_METHOD = "currentReportStartedAt";
	public static final String CONFIG_CHANGED_METHOD = "configChanged";

	private final int port;

	public Protocol(Configuration configuration) {
		port = configuration.getCoreJRestPort();
	}

	public JarmilWaiterProtocol getWaiterProtocol(Hg6CoreCmdsProcessor processor) {
		TargetOnWaiter target = ObjectOnWaiterTarget.create(NAME, processor);
		return JarmilWaiterProtocol.createSingle(port, target);
	}

	public JarmilGuestProtocol getGuestProtocol(String host) {
		return JarmilGuestProtocol.create(port, host);
	}

	public JarmilWaiterShift getWaiterShift(Hg6CoreCmdsProcessor processor) {
		JarmilWaiterProtocol protocol = getWaiterProtocol(processor);
		return new JarmilWaiterShift(protocol);
	}

	public SingleJarmilGuest getGuest(String host) {
		TargetOnGuest target = ObjectOnGuestTarget.create(NAME);
		JarmilGuestProtocol protocol = getGuestProtocol(host);
		return new SingleJarmilGuest(target, protocol);
	}

}
