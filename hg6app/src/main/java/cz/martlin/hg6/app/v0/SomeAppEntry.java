package cz.martlin.hg6.app.v0;

import cz.martlin.hg6.core.v0.SomeApplication;
import cz.martlin.jrest.misc.CommunicationProtocol;
import cz.martlin.jrest.waiter.CommandProcessor;
import cz.martlin.jrest.waiter.JRestWaiterStarter;

public class SomeAppEntry {

	public static void main(String[] args) {
		SomeApplication app = new SomeApplication();

		CommunicationProtocol protocol = new SomeAppProtocol();
		CommandProcessor processor = new SomeAppCommandProcessor(app);
		
		JRestWaiterStarter starter = new JRestWaiterStarter(protocol, processor);

		starter.startWaiter();

	}

}
