package cz.martlin.hg5.test;

import cz.martlin.hg5.logic.config.Configuration;
import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.hg6.core.Hg6Core;
import cz.martlin.hg6.db.Hg6Database;
import cz.martlin.hg6.db.Hg6DbException;

public class TestingMain {

	public static void main(String[] args) {
		Configuration config = Hg6Config.get().createDefault();
		Hg6Core hg = new Hg6Core(config);

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

		try {
			Hg6Database db = new Hg6Database(config);
			System.out.println("Last report: " + db.loadLastReport());
		} catch (Hg6DbException e) {
			e.printStackTrace();
		}
	}
}
