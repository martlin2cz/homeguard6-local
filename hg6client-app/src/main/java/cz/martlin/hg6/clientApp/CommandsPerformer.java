package cz.martlin.hg6.clientApp;

import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.hg6.coreJRest.Hg6CoreClient;
import cz.martlin.hg6.coreJRest.Hg6CoreConnException;
import cz.martlin.hg6.mrsConnJRest.Hg6MrsConnClient;
import cz.martlin.hg6.mrsConnJRest.Hg6MrsConnJRestException;

public class CommandsPerformer {
	public static final String MRS_IS_LOOP_RUNNING = "is-mrs-loop-running";
	public static final String MRS_SYNCHRONIZE = "mrs-synchronize";
	public static final String STOP_MRS_LOOP_CMD = "stop-mrs-loop";
	public static final String START_MRS_LOOP_CMD = "start-mrs-loop";
	public static final String SIMPLE_INFO_CMD = "simple-info";
	public static final String IS_RUNNING_CMD = "is-running";
	public static final String STOP_CMD = "stop";
	public static final String START_CMD = "start";

	private final Hg6Config config;
	private final Hg6CoreClient core;
	private final Hg6MrsConnClient mrs;

	public CommandsPerformer() {
		config = Hg6Config.get();
		core = new Hg6CoreClient(config);
		mrs = new Hg6MrsConnClient(config);
	}

	public boolean handleCommand(String arg) {
		if (START_CMD.equals(arg)) {
			doStart(core);
			return true;
		}

		if (STOP_CMD.equals(arg)) {
			doStop(core);
			return true;
		}

		if (IS_RUNNING_CMD.equals(arg)) {
			doIsRunning(core);
			return true;
		}

		if (SIMPLE_INFO_CMD.equals(arg)) {
			doSimpleInfo(core);
			return true;
		}

		if (START_MRS_LOOP_CMD.equals(arg)) {
			doStartMrsLoop(mrs);
			return true;
		}

		if (STOP_MRS_LOOP_CMD.equals(arg)) {
			doStopMrsLoop(mrs);
			return true;
		}
		if (MRS_SYNCHRONIZE.equals(arg)) {
			doMrsSynchronize(mrs);
			return true;
		}
		if (MRS_IS_LOOP_RUNNING.equals(arg)) {
			doIsRunningMrsLoop(mrs);
			return true;
		}

		return false;
	}

	private void doSimpleInfo(Hg6CoreClient client) {

		try {
			String si = client.simpleInfo();
			System.out.println(si);
		} catch (Hg6CoreConnException e) {
			System.err.println("FAILED: " + e);
		}

	}

	private void doIsRunning(Hg6CoreClient client) {
		try {
			boolean is = client.isRunning();
			System.out.println("Is running? " + is);
		} catch (Hg6CoreConnException e) {
			System.err.println("FAILED: " + e);
		}
	}

	private void doStop(Hg6CoreClient client) {
		try {
			client.stop();
			System.out.println("Stopped");
		} catch (Hg6CoreConnException e) {
			System.err.println("FAILED: " + e);
		}
	}

	private void doStart(Hg6CoreClient client) {
		try {
			client.start();
			System.out.println("Started");
		} catch (Hg6CoreConnException e) {
			System.err.println("FAILED: " + e);
		}
	}

	private void doStartMrsLoop(Hg6MrsConnClient client) {
		try {
			client.startLoop();
			System.out.println("MRS sync loop started");
		} catch (Hg6MrsConnJRestException e) {
			System.err.println("FAILED: " + e);
		}
	}

	private void doStopMrsLoop(Hg6MrsConnClient client) {
		try {
			client.stopLoop();
			System.out.println("MRS sync loop stopped");
		} catch (Hg6MrsConnJRestException e) {
			System.err.println("FAILED: " + e);
		}
	}

	private void doIsRunningMrsLoop(Hg6MrsConnClient client) {
		try {
			boolean running = client.isLoopRunning();
			System.out.println("Is MRS sync loop running? " + running);
		} catch (Hg6MrsConnJRestException e) {
			System.err.println("FAILED: " + e);
		}
	}

	private void doMrsSynchronize(Hg6MrsConnClient client) {
		try {
			client.synchronize();
			System.out.println("Synchronized");
		} catch (Hg6MrsConnJRestException e) {
			System.err.println("FAILED: " + e);
		}
	}

}
