package cz.martlin.hg6.v0;

import cz.martlin.hg6.core.v0.SomeApplication;

public class TestingMain {

	public static void main(String[] args) {

		SomeApplication app = new SomeApplication();
		
		app.start();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
		app.stop();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
		}
		
		app.start();
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
		}
		app.stop();
	}

}
