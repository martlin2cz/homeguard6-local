package cz.martling.hg6.clientApp;

import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.hg6.coreJRest.Hg6CoreClient;
import cz.martlin.hg6.coreJRest.Hg6CoreConnException;
import cz.martlin.hg6.mrsJRest.Hg6MrsConnJRestException;
import cz.martlin.hg6.mrsJRest.Hg6MrsConnectorClient;

public class ClientAppMain {

	private static final String MRS_IS_LOOP_RUNNING = "is-mrs-loop-running";
	private static final String MRS_SYNCHRONIZE = "mrs-synchronize";
	private static final String STOP_MRS_LOOP_CMD = "stop-mrs-loop";
	private static final String START_MRS_LOOP_CMD = "start-mrs-loop";
	private static final String SIMPLE_INFO_CMD = "simple-info";
	private static final String IS_RUNNING_CMD = "is-running";
	private static final String STOP_CMD = "stop";
	private static final String START_CMD = "start";

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
		System.out.println("Usage: hg6cli ");
		System.out.println("	" + START_CMD);
		System.out.println("	" + STOP_CMD);
		System.out.println("	" + IS_RUNNING_CMD);
		System.out.println("	" + SIMPLE_INFO_CMD);
		System.out.println();
		System.out.println("	" + START_MRS_LOOP_CMD);
		System.out.println("	" + STOP_MRS_LOOP_CMD);
		System.out.println("	" + MRS_IS_LOOP_RUNNING);
		System.out.println("	" + MRS_SYNCHRONIZE);

		System.exit(code);
	}

	private static void handleCommandOrFail(String[] args) {
		Hg6Config config = Hg6Config.get();
		Hg6CoreClient core = new Hg6CoreClient(config);
		Hg6MrsConnectorClient mrs = new Hg6MrsConnectorClient(config);

		if (args.length != 1) {
			System.err.println("Invalid arguments.");
			showHelpAndExit(1);
		}

		String arg = args[0];
		if (START_CMD.equals(arg)) {
			doStart(core);
			return;
		}

		if (STOP_CMD.equals(arg)) {
			doStop(core);
			return;
		}

		if (IS_RUNNING_CMD.equals(arg)) {
			doIsRunning(core);
			return;
		}

		if (SIMPLE_INFO_CMD.equals(arg)) {
			doSimpleInfo(core);
			return;
		}

		if (START_MRS_LOOP_CMD.equals(arg)) {
			doStartMrsLoop(mrs);
			return;
		}

		if (STOP_MRS_LOOP_CMD.equals(arg)) {
			doStopMrsLoop(mrs);
			return;
		}
		if (MRS_SYNCHRONIZE.equals(arg)) {
			doMrsSynchronize(mrs);
			return;
		}
		if (MRS_IS_LOOP_RUNNING.equals(arg)) {
			doIsRunningMrsLoop(mrs);
			return;
		}

		System.err.println("Unknown command: " + arg);
		showHelpAndExit(1);
	}

	private static void doSimpleInfo(Hg6CoreClient client) {

		try {
			String si = client.simpleInfo();
			System.out.println(si);
		} catch (Hg6CoreConnException e) {
			System.err.println("FAILED: " + e);
		}

	}

	private static void doIsRunning(Hg6CoreClient client) {
		try {
			boolean is = client.isRunning();
			System.out.println("Is running? " + is);
		} catch (Hg6CoreConnException e) {
			System.err.println("FAILED: " + e);
		}
	}

	private static void doStop(Hg6CoreClient client) {
		try {
			client.stop();
			System.out.println("Stopped");
		} catch (Hg6CoreConnException e) {
			System.err.println("FAILED: " + e);
		}
	}

	private static void doStart(Hg6CoreClient client) {
		try {
			client.start();
			System.out.println("Started");
		} catch (Hg6CoreConnException e) {
			System.err.println("FAILED: " + e);
		}
	}

	private static void doStartMrsLoop(Hg6MrsConnectorClient client) {
		try {
			client.startLoop();
			System.out.println("MRS sync loop started");
		} catch (Hg6MrsConnJRestException e) {
			System.err.println("FAILED: " + e);
		}
	}

	private static void doStopMrsLoop(Hg6MrsConnectorClient client) {
		try {
			client.stopLoop();
			System.out.println("MRS sync loop stopped");
		} catch (Hg6MrsConnJRestException e) {
			System.err.println("FAILED: " + e);
		}
	}

	private static void doIsRunningMrsLoop(Hg6MrsConnectorClient client) {
		try {
			boolean running = client.isLoopRunning();
			System.out.println("Is MRS sync loop running? " + running);
		} catch (Hg6MrsConnJRestException e) {
			System.err.println("FAILED: " + e);
		}
	}

	private static void doMrsSynchronize(Hg6MrsConnectorClient client) {
		try {
			client.synchronize();
			System.out.println("Synchronized");
		} catch (Hg6MrsConnJRestException e) {
			System.err.println("FAILED: " + e);
		}
	}

}
