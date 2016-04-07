package cz.martlin.hg6.app.v0;

import cz.martlin.hg6.core.v0.SomeApplication;
import cz.martlin.hg6.coreJRest.v0.SomeAppCommandsProcessor;

public class Hg6CommandsProcessorImpl implements SomeAppCommandsProcessor {

	private final SomeApplication app;

	public Hg6CommandsProcessorImpl(SomeApplication app) {
		this.app = app;
	}

	@Override
	public void start() {
		app.start();
	}

	@Override
	public void stop() {
		app.stop();
	}

}
