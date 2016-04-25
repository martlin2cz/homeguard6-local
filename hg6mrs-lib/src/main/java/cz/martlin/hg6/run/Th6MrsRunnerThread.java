package cz.martlin.hg6.run;

public class Th6MrsRunnerThread extends Thread {

	public Th6MrsRunnerThread(Hg6MrsSyncLoop loop) {
		super(new Th6MrsRunnerRunnable(loop), "hg6mrs loop runner thread");	
	}
}
