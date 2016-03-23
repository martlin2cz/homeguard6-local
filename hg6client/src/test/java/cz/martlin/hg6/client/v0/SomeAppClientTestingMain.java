package cz.martlin.hg6.client.v0;

import hg6client.SomeAppClient;

public class SomeAppClientTestingMain {

	public static void main(String[] args) {

		SomeAppClient cli = new SomeAppClient();

		cli.start();

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}

		cli.stop();

		//cli.kill();
	}
}
