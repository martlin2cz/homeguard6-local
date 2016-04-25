package cz.martlin.hg6.run;

import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.hg6.mrs.Hg6MrsException;
import cz.martlin.hg6.mrsLib.sync.SituationsMrsSyncer;

public class Hg6Mrs {

	private final Hg6MrsRunner runner;
	private final SituationsMrsSyncer syncer;

	public Hg6Mrs(Hg6Config config) throws Hg6MrsException {
		runner = new Hg6MrsRunner(config);
		syncer = new SituationsMrsSyncer(config);
	}

	public void startLoop() {
		runner.startLoop();
	}

	public void stopLoop() {
		runner.stopLoop();
	}

	public void invokeSyncManually() throws Hg6MrsException {
		syncer.synchronize();
	}

}
