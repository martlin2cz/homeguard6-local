package cz.martling.hg6.clientApp;

import cz.martlin.hg6.coreJRest.Hg6CoreClient;
import cz.martlin.hg6.coreJRest.Hg6CoreConnException;

public class ClientAppMain {

	private static final Hg6CoreClient client = new Hg6CoreClient();
	// TODO host?
	// TODO init only when required?

	public static void main(String[] args) {

		tryToHandleHelpOrVersion(args);

		handleCommandOrFail(args);
	}

	private static void tryToHandleHelpOrVersion(String[] args) {
		if (args.length == 1) {
			String arg = args[0];
			if (("-h".equals(arg) || "--help".equals(arg)) //
					|| ("-v".equals(arg) || "--version".equals(arg))) {

				showHelpAndExit(0);
			}
		}

	}

	private static void showHelpAndExit(int code) {

		System.out.println("Homeguard 6 client app");
		System.out.println("Usage: hg6cli start|stop|is-running|simple-info");

		System.exit(0);
	}

	private static void handleCommandOrFail(String[] args) {
		if (args.length != 1) {
			System.err.println("Invalid arguments.");
			showHelpAndExit(1);
		}

		String arg = args[0];
		if ("start".equals(arg)) {
			doStart();
			return;
		}

		if ("stop".equals(arg)) {
			doStop();
			return;
		}

		if ("is-running".equals(arg)) {
			doIsRunning();
			return;
		}

		if ("simple-info".equals(arg)) {
			doSimpleInfo();
			return;
		}

		System.err.println("Unknown command: " + arg);
	}

	private static void doSimpleInfo() {

		try {
			String si = client.simpleInfo();
			System.out.println(si);
		} catch (Hg6CoreConnException e) {
			System.err.println("FAILED: " + e);
		}

	}

	private static void doIsRunning() {
		try {
			boolean is = client.isRunning();
			System.out.println("Is running? " + is);
		} catch (Hg6CoreConnException e) {
			System.err.println("FAILED: " + e);
		}
	}

	private static void doStop() {
		try {
			client.stop();
			System.out.println("Stopped");
		} catch (Hg6CoreConnException e) {
			System.err.println("FAILED: " + e);
		}
	}

	private static void doStart() {
		try {
			client.start();
			System.out.println("Started");
		} catch (Hg6CoreConnException e) {
			System.err.println("FAILED: " + e);
		}
	}

}
