package cz.martlin.hg6.mrsConnJRest;

import cz.martlin.hg5.logic.config.Configuration;
import cz.martlin.jrest.impl.jarmil.JarmilWaiterShift;
import cz.martlin.jrest.impl.jarmil.SingleJarmilGuest;
import cz.martlin.jrest.impl.jarmil.protocol.JarmilGuestProtocol;
import cz.martlin.jrest.impl.jarmil.protocol.JarmilWaiterProtocol;
import cz.martlin.jrest.impl.jarmil.target.TargetOnGuest;
import cz.martlin.jrest.impl.jarmil.target.TargetOnWaiter;
import cz.martlin.jrest.impl.jarmil.targets.guest.ObjectOnGuestTarget;
import cz.martlin.jrest.impl.jarmil.targets.waiter.ObjectOnWaiterTarget;

public class Protocol {

	public static final String NAME = "hg6mrs";

	public static final String START_LOOP_METHOD = "startLoop";
	public static final String STOP_LOOP_METHOD = "stopLoop";
	public static final String SYNCHRONIZE_METHOD = "synchronize";
	public static final String IS_LOOP_RUNNING_METHOD = "isLoopRunning";
	public static final String CONFIG_CHANGED_METHOD = "configChanged";

	public final int port;

	public Protocol(Configuration config) {
		this.port = config.getMrsJRestPort();
	}

	public JarmilWaiterProtocol getWaiterProtocol(Hg6MrsConnCmdsProcessor processor) {
		TargetOnWaiter target = ObjectOnWaiterTarget.create(NAME, processor);
		return JarmilWaiterProtocol.createSingle(port, target);
	}

	public JarmilGuestProtocol getGuestProtocol(String host) {
		return JarmilGuestProtocol.create(port, host);
	}

	public JarmilWaiterShift getWaiterShift(Hg6MrsConnCmdsProcessor processor) {
		JarmilWaiterProtocol protocol = getWaiterProtocol(processor);
		return new JarmilWaiterShift(protocol);
	}

	public SingleJarmilGuest getGuest(String host) {
		TargetOnGuest target = ObjectOnGuestTarget.create(NAME);
		JarmilGuestProtocol protocol = getGuestProtocol(host);
		return new SingleJarmilGuest(target, protocol);
	}

}
