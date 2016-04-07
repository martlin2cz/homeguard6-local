package cz.martling.hg6.clientApp.v0;

import cz.martlin.hg6.coreJRest.v0.SomeAppClient;

public class SomeAppConsoleClient {

	public static void main(String[] args) {
		SomeAppClient client = new SomeAppClient();

		client.start();
		System.out.println("Started");

		try {
			Thread.sleep(4 * 1000);
		} catch (InterruptedException e) {
		}

		client.stop();
		System.out.println("Stopped");
	}
}
