package cz.martlin.hg6.mrsConnJRest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg6.config.Hg6ConfigException;
import cz.martlin.hg6.mrs.misc.Hg6MrsException;

public class TestingHg6MrsConnCmdsProcImpl implements Hg6MrsConnCmdsProcessor {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private boolean running;

	public TestingHg6MrsConnCmdsProcImpl() {
	}

	@Override
	public String getJarmilTargetDescription() {
		return "Simple testing implementation of Hg6MrsConn commands processor";
	}

	@Override
	public void startLoop() {
		running = true;
		LOG.info("Loop started");
	}

	@Override
	public void stopLoop() {
		running = false;
		LOG.info("Loop stopped");
	}

	@Override
	public void synchronize() throws Hg6MrsException {
		LOG.info("Synchronized");
	}

	@Override
	public boolean isLoopRunning() {
		return running;
	}
	
	@Override
	public void configChanged() throws Hg6ConfigException {
		LOG.info("config have been changed, reloading...");
	}

}
