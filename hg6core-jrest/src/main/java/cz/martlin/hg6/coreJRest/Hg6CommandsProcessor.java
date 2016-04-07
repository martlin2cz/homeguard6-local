package cz.martlin.hg6.coreJRest;

public interface Hg6CommandsProcessor {

	public void start();

	public void stop();

	public String getIsRunning();

	public String getSimpleInfo();

	// TODO

}
