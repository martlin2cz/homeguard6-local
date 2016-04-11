package cz.martlin.hg6.coreJRest;

import java.util.Calendar;

public interface Hg6CommandsProcessor {

	public void start();

	public void stop();

	public boolean getIsRunning();

	public String getSimpleInfo();
	
	public Calendar getCurrentReportStartedAt();
	
	public boolean configChanged();

	// TODO

}
