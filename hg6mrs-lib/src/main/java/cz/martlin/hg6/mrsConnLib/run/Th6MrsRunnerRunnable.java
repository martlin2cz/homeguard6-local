package cz.martlin.hg6.mrsConnLib.run;

public class Th6MrsRunnerRunnable implements Runnable {
	private final Hg6MrsSyncLoop loop;

	public Th6MrsRunnerRunnable(Hg6MrsSyncLoop loop) {
		super();
		this.loop = loop;
	}

	@Override
	public void run() {
		loop.run();
	}

}
