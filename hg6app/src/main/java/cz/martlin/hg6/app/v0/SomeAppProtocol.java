package cz.martlin.hg6.app.v0;

import cz.martlin.jrest.misc.CommunicationProtocol;

public class SomeAppProtocol extends CommunicationProtocol {

	private static final int PORT = 5_2016;

	public SomeAppProtocol() {
		super(PORT);
	}
}
