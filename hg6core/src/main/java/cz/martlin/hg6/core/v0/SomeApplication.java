package cz.martlin.hg6.core.v0;

public class SomeApplication {

	private SomeThread t;

	public void start() {
		System.out.println("Some application is starting...");
		t = new SomeThread();
		t.start();
	}

	public void stop() {
		System.out.println("Some application is stopping...");
		t.interrupt();
		try {
			t.join();
		} catch (InterruptedException e) {
		}
	}
}
