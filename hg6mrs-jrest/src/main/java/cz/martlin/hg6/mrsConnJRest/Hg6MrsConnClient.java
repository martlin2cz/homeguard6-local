package cz.martlin.hg6.mrsConnJRest;

import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.jrest.impl.jarmil.SingleJarmilGuest;
import cz.martlin.jrest.misc.JRestException;

public class Hg6MrsConnClient {

	private final Protocol protocol;

	private final SingleJarmilGuest guest;

	public Hg6MrsConnClient(Hg6Config config) {
		protocol = new Protocol(config.getConfig());
		guest = protocol.getGuest(null);
	}

	public void startLoop() throws Hg6MrsConnJRestException {
		try {
			guest.invoke(Protocol.START_LOOP_METHOD);
		} catch (JRestException e) {
			throw new Hg6MrsConnJRestException("Cannot invoke startLoop", e);
		}
	}

	public void stopLoop() throws Hg6MrsConnJRestException {
		try {
			guest.invoke(Protocol.STOP_LOOP_METHOD);
		} catch (JRestException e) {
			throw new Hg6MrsConnJRestException("Cannot invoke stopLoop", e);
		}
	}
	

	public void synchronize() throws Hg6MrsConnJRestException {
		try {
			guest.invoke(Protocol.SYNCHRONIZE_METHOD);
		} catch (JRestException e) {
			throw new Hg6MrsConnJRestException("Cannot invoke synchronize", e);
		}
	}

	public boolean isLoopRunning() throws Hg6MrsConnJRestException {
		try {
			return guest.invoke(Protocol.IS_LOOP_RUNNING_METHOD);
		} catch (JRestException e) {
			throw new Hg6MrsConnJRestException("Cannot invoke isLoopRunning", e);
		}
	}
	
	public void configChanged() throws Hg6MrsConnJRestException {
		try {
			guest.invoke(Protocol.CONFIG_CHANGED_METHOD);
		} catch (JRestException e) {
			throw new Hg6MrsConnJRestException("Cannot invoke configChanged", e);
		}
	}

}
