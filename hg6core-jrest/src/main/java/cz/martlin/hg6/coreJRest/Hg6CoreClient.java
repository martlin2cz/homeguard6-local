package cz.martlin.hg6.coreJRest;

import java.util.Calendar;

import cz.martlin.hg5.logic.config.Configuration;
import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.jrest.impl.jarmil.SingleJarmilGuest;
import cz.martlin.jrest.misc.JRestException;

public class Hg6CoreClient {
	// private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final SingleJarmilGuest guest;

	public Hg6CoreClient(Hg6Config config) {
		Protocol protocol = new Protocol(config.getConfig());
		guest = protocol.getGuest(null);
	}

	public Hg6CoreClient(Configuration configuration, String host) {
		Protocol protocol = new Protocol(configuration);
		guest = protocol.getGuest(host);
	}

	public void start() throws Hg6CoreConnException {
		try {
			guest.invoke(Protocol.START_METHOD);
		} catch (JRestException e) {
			throw new Hg6CoreConnException("Cannot invoke start", e);
		}
	}

	public void stop() throws Hg6CoreConnException {
		try {
			guest.invoke(Protocol.STOP_METHOD);
		} catch (JRestException e) {
			throw new Hg6CoreConnException("Cannot invoke stop", e);
		}
	}

	public Boolean isRunning() throws Hg6CoreConnException {
		try {
			return guest.invoke(Protocol.IS_RUNNING_METHOD);
		} catch (JRestException e) {
			throw new Hg6CoreConnException("Cannot invoke isRunning", e);
		}
	}

	public String simpleInfo() throws Hg6CoreConnException {
		try {
			return guest.invoke(Protocol.SIMPLE_INFO_METHOD);
		} catch (JRestException e) {
			throw new Hg6CoreConnException("Cannot invoke simpleInfo", e);
		}
	}

	public Calendar getCurrentStartedAt() throws Hg6CoreConnException {
		try {
			return guest.invoke(Protocol.CURRENT_STARTED_METHOD);
		} catch (JRestException e) {
			throw new Hg6CoreConnException("Cannot invoke currentStartedAt", e);
		}
	}

	public void configChanged() throws Hg6CoreConnException {
		try {
			guest.invoke(Protocol.CONFIG_CHANGED_METHOD);
		} catch (JRestException e) {
			throw new Hg6CoreConnException("Cannot invoke configChanged", e);
		}
	}

}
