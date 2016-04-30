package cz.martlin.hg6.mrsConnLib.run;

import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.hg6.mrs.misc.Hg6MrsException;
import cz.martlin.hg6.mrsConnLib.local.AbstractHg6MrsConn;
import cz.martlin.hg6.mrsConnLib.sync.SituationsMrsSyncer;

public class Hg6Mrs implements AbstractHg6MrsConn {

	private final Hg6MrsRunner runner;
	private final SituationsMrsSyncer syncer;

	public Hg6Mrs(Hg6Config config) throws Hg6MrsException {
		runner = new Hg6MrsRunner(config);
		syncer = new SituationsMrsSyncer(config, runner);
	}

	public boolean isLoopRunning() {
		return runner.isLoopRunning();
	}

	public void startLoop() throws Hg6MrsException {
		runner.startLoop();
	}

	public void stopLoop() throws Hg6MrsException {
		runner.stopLoop();
	}

	public void invokeSyncManually() throws Hg6MrsException {
		syncer.synchronize();
	}

}
