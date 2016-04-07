package cz.martlin.hg5.test;

import cz.martlin.hg5.logic.config.Configuration;
import cz.martlin.hg6.core.Homeguard;

public class TestingMain {

	public static void main(String[] args) {
		Configuration config = new Configuration();
		Homeguard hg = new Homeguard(config);

		System.out.println("Starting...");
		hg.start();
		System.out.println("Started.");

		try {
			Thread.sleep(10 * 60 * 1000);
		} catch (InterruptedException e) {
		}

		System.out.println("Stopping...");
		hg.stop();
		System.out.println("Stopped.");

		System.out.println("Last report:\n " + hg.lastReport());
	}
}
