package cz.martlin.hg6.app.v0;

import cz.martlin.hg6.core.v0.SomeApplication;
import cz.martlin.jrest.waiter.CommandProcessor;

public class SomeAppCommandProcessor implements CommandProcessor {

	private final SomeApplication app;

	public SomeAppCommandProcessor(SomeApplication app) {
		this.app = app;
	}

	@Override
	public String handleCommand(String cmd) throws Exception {
		if ("start".equals(cmd)) {
			app.start();
			return "Started";
		} else if ("stop".equals(cmd)) {
			app.stop();
			return "Stopped";
		} else {
			return "Unkwnown command: " + cmd;
		}
	}

}
