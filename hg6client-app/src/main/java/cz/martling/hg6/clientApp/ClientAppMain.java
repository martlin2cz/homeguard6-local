package cz.martling.hg6.clientApp;

import cz.martlin.hg6.coreJRest.HG6Client;

public class ClientAppMain {

	private static final HG6Client client = new HG6Client();
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
		String si = client.simpleInfo();

		if (si != null) {
			System.out.println(si);
		} else {
			System.err.println("FAILED");
		}
	}

	private static void doIsRunning() {
		Boolean what = client.isRunning();

		if (what != null) {
			System.out.println("Is running? " + what);
		} else {
			System.err.println("FAILED");
		}
	}

	private static void doStop() {
		boolean success = client.stop();

		if (success) {
			System.out.println("Stopped");
		} else {
			System.err.println("FAILED");
		}
	}

	private static void doStart() {
		boolean success = client.start();

		if (success) {
			System.out.println("Started");
		} else {
			System.err.println("FAILED");
		}
	}

}
