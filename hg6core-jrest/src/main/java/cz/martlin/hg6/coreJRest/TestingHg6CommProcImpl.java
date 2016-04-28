package cz.martlin.hg6.coreJRest;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestingHg6CommProcImpl implements Hg6CoreCmdsProcessor {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private boolean running;

	public TestingHg6CommProcImpl() {
	}

	@Override
	public String getJarmilTargetDescription() {
		return "Simple testing implementation of Hg6CommandsProcessor";
	}

	@Override
	public void start() {
		running = true;
		LOG.info("The Hg6 started");
	}

	@Override
	public void stop() {
		running = false;
		LOG.info("The Hg6 stopped");
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	@Override
	public String getSimpleInfo() {
		return "This is a simple info...";
	}

	@Override
	public Calendar getCurrentReportStartedAt() {
		return null; // nope
	}

	@Override
	public void configChanged() {
		LOG.info("The config changed. Reloading...");
	}

}
