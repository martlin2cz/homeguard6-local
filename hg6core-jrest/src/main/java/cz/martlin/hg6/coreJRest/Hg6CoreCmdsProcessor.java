package cz.martlin.hg6.coreJRest;

import java.util.Calendar;

import cz.martlin.jrest.impl.jarmil.handler.JarmilTarget;

public interface Hg6CoreCmdsProcessor extends JarmilTarget {

	public void start();

	public void stop();

	public boolean getIsRunning();

	public String getSimpleInfo() throws Exception;

	public Calendar getCurrentReportStartedAt();

	public void configChanged() throws Exception;

	// TODO

}