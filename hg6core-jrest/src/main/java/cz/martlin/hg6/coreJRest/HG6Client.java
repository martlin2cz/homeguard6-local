package cz.martlin.hg6.coreJRest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.guest.JRestGuest;
import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.reqresp.JRestRequest;

public class HG6Client {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private final JRestGuest guest;

	public HG6Client() {
		Protocol protocol = new Protocol();
		guest = protocol.getGuest();
	}

	public HG6Client(String host) {
		Protocol protocol = new Protocol();
		guest = protocol.getGuest(host);
	}

	public boolean start() {
		JRestRequest request = new JRestRequest(Protocol.START_COMMAND);
		try {
			guest.sendRequest(request);
			return true;
		} catch (JRestException e) {
			log.error("Cannot send start command", e);
			return false;
		}
	}

	public boolean stop() {
		JRestRequest request = new JRestRequest(Protocol.STOP_COMMAND);
		try {
			guest.sendRequest(request);
			return true;
		} catch (JRestException e) {
			log.error("Cannot send stop command", e);
			return false;
		}
	}

}
