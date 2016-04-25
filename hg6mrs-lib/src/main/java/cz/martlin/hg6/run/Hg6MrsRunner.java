package cz.martlin.hg6.run;

import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.hg6.mrs.Hg6MrsException;

public class Hg6MrsRunner {

	private final Hg6MrsSyncLoop loop;

	private Th6MrsRunnerThread thread;

	public Hg6MrsRunner(Hg6Config config) throws Hg6MrsException {
		loop = new Hg6MrsSyncLoop(config);
	}

	public Hg6MrsSyncLoop getLoop() {
		return loop;
	}

	public synchronized void startLoop() {
		if (thread == null) {
			thread = new Th6MrsRunnerThread(loop);
			thread.start();
		}
	}

	public synchronized void stopLoop() {
		if (thread != null) {
			thread.interrupt();
			try {
				thread.join();
			} catch (InterruptedException e) {
			}
			thread = null;
		}
	}

	public synchronized boolean isLoopRunning() {
		return thread != null;
	}

}
