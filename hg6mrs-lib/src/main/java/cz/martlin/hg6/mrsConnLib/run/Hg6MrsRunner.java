package cz.martlin.hg6.mrsConnLib.run;

import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.hg6.mrs.misc.Hg6MrsException;
import cz.martlin.hg6.mrsConnLib.local.AbstractHg6MrsConn;

public class Hg6MrsRunner implements AbstractHg6MrsConn {

	private final Hg6MrsSyncLoop loop;

	private Th6MrsRunnerThread thread;

	public Hg6MrsRunner(Hg6Config config) throws Hg6MrsException {
		loop = new Hg6MrsSyncLoop(config, this);
	}

	public Hg6MrsSyncLoop getLoop() {
		return loop;
	}

	public synchronized void startLoop() throws Hg6MrsException {
		if (thread == null) {
			thread = new Th6MrsRunnerThread(loop);
			thread.start();

			loop.getSyncer().synchronize();
		}

	}

	public synchronized void stopLoop() throws Hg6MrsException {
		if (thread != null) {
			loop.interrupt();
			thread.interrupt();
			try {
				thread.join();
			} catch (InterruptedException e) {
			}
			thread = null;

			loop.getSyncer().synchronize();
		}
	}

	public boolean isLoopRunning() {
		return thread != null;
	}

}
