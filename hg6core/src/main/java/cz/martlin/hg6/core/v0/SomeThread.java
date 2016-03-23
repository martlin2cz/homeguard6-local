package cz.martlin.hg6.core.v0;

import java.util.Date;

public class SomeThread extends Thread {

	private boolean interrupted;

	public SomeThread() {
		super("SomeThread");
	}

	@Override
	public void run() {
		System.out.println("STARTED.");
		
		while (!interrupted) {
			System.out.println("RUNNING SOMETHING: " + new Date());
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
			}

		}
		
		System.out.println("STOPPED.");
	}

	@Override
	public void interrupt() {
		this.interrupted = true;
		super.interrupt();
	}
}
