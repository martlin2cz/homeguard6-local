package cz.martlin.hg6.coreJRest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.guest.JRestGuest;
import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.reqresp.JRestRequest;
import cz.martlin.jrest.protocol.reqresp.JRestResponse;
import cz.martlin.jrest.protocol.reqresp.ResponseStatus;

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
			JRestResponse response = guest.sendRequest(request);
			return response.is(ResponseStatus.OK);
		} catch (JRestException e) {
			log.error("Cannot send start command", e);
			return false;
		}
	}

	public boolean stop() {
		JRestRequest request = new JRestRequest(Protocol.STOP_COMMAND);
		try {
			JRestResponse response = guest.sendRequest(request);
			return response.is(ResponseStatus.OK);
		} catch (JRestException e) {
			log.error("Cannot send stop command", e);
			return false;
		}
	}

	public Boolean isRunning() {
		JRestRequest request = new JRestRequest(Protocol.IS_RUNNING_COMMAND);
		try {
			JRestResponse response = guest.sendRequest(request);

			if (!response.is(ResponseStatus.OK)) {
				throw new JRestException("Got no OK response: " + response);
			}
			return Boolean.parseBoolean(response.getData());
		} catch (JRestException e) {
			log.error("Cannot send isRunning command", e);
			return null;
		}
	}

	public String simpleInfo() {
		JRestRequest request = new JRestRequest(Protocol.SIMPLE_INFO_COMMAND);
		try {
			JRestResponse response = guest.sendRequest(request);

			if (!response.is(ResponseStatus.OK)) {
				throw new JRestException("Got no OK response: " + response);
			}
			return response.getData();
		} catch (JRestException e) {
			log.error("Cannot send simpleInfo command", e);
			return null;
		}
	}

}
