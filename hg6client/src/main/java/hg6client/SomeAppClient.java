package hg6client;

import cz.martlin.hg6.app.v0.SomeAppProtocol;
import cz.martlin.jrest.guest.JRestGuest;
import cz.martlin.jrest.misc.JRestException;

public class SomeAppClient {

	private final JRestGuest guest;

	public SomeAppClient() {
		this.guest = new JRestGuest(new SomeAppProtocol());
	}

	public boolean start() {
		try {
			String response = guest.sendCommand("start");
			System.out.println("Started? " + response);
			return true;
		} catch (JRestException e) {
			System.err.println("Start failed: " + e);
			return false;
		}
	}

	public boolean stop() {
		try {
			String response = guest.sendCommand("stop");
			System.out.println("Stopped? " + response);
			return true;
		} catch (JRestException e) {
			System.err.println("Stop failed: " + e);
			return false;
		}
	}

	public boolean kill() {
		try {
			guest.stopWaiter();
			System.out.println("Waiter down.");
			return true;
		} catch (JRestException e) {
			System.err.println("Murder unsuccessfull: " + e);
			return false;
		}
	}
}
