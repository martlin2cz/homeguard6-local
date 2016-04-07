package cz.martlin.hg6.app;

import cz.martlin.hg6.core.Homeguard;
import cz.martlin.hg6.coreJRest.Hg6CommandsProcessor;

public class Hg6CommandsProcessorImpl implements Hg6CommandsProcessor {

	private final Homeguard hg;

	public Hg6CommandsProcessorImpl(Homeguard homeguard) {
		this.hg = homeguard;
	}

	@Override
	public void start() {
		hg.start();
	}

	@Override
	public void stop() {
		hg.stop();
	}

	@Override
	public boolean isRunning() {
		return hg.isRunning();
	}

}
