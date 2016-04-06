package cz.martlin.hg6.coreJRest.test;

import cz.martlin.hg6.coreJRest.v0.Hg6CommandsProcessor;

public class FakeHG6CmdsProcImpl implements Hg6CommandsProcessor {

	@Override
	public void start() {
		System.out.println("I am starting ...");
	}

	@Override
	public void stop() {
		System.out.println("I am stopping ...");
	}

}
