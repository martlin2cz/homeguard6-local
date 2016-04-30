package cz.martlin.hg6.clientApp;

public class ClientAppMain {

	public static void main(String[] args) {

		tryToHandleHelpOrVersion(args);

		handleCommands(args);
	}

	private static void tryToHandleHelpOrVersion(String[] args) {
		if (args.length == 1) {
			String arg = args[0];
			if (("-h".equals(arg) || "--help".equals(arg)) //
					|| ("-v".equals(arg) || "--version".equals(arg))) {

				showHelpAndTryExit(0);
			}
		}

	}

	private static void showHelpAndTryExit(Integer code) {

		System.out.println("Homeguard 6 client app");
		System.out.println("Usage: hg6cli ");
		System.out.println("	" + CommandsPerformer.START_CMD);
		System.out.println("	" + CommandsPerformer.STOP_CMD);
		System.out.println("	" + CommandsPerformer.IS_RUNNING_CMD);
		System.out.println("	" + CommandsPerformer.SIMPLE_INFO_CMD);
		System.out.println();
		System.out.println("	" + CommandsPerformer.START_MRS_LOOP_CMD);
		System.out.println("	" + CommandsPerformer.STOP_MRS_LOOP_CMD);
		System.out.println("	" + CommandsPerformer.MRS_IS_LOOP_RUNNING);
		System.out.println("	" + CommandsPerformer.MRS_SYNCHRONIZE);

		if (code != null) {
			System.exit(code);
		}
	}

	private static void handleCommands(String[] args) {

		if (args.length < 1) {
			System.err.println("Missing argument(s).");
			showHelpAndTryExit(1);
		}

		CommandsPerformer performer = new CommandsPerformer();

		for (String arg : args) {
			boolean known = performer.handleCommand(arg);

			if (!known) {
				System.err.println("Unknown command: " + arg);
				showHelpAndTryExit(null);
			}
		}
	}

}
