package cz.martlin.hg6.clientApp;

import cz.martling.hg6.clientApp.ClientAppMain;

public class TestClientAppCall {
	public static void main(String[] args) {
		final String command = "stop";

		ClientAppMain.main(new String[] { command });
	}
}
