package cz.martlin.hg6.mrsConnApp;

import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.hg6.config.Hg6ConfigException;
import cz.martlin.hg6.mrs.misc.Hg6MrsException;
import cz.martlin.hg6.mrsConnJRest.Hg6MrsConnCmdsProcessor;
import cz.martlin.hg6.mrsConnLib.run.Hg6MrsRunner;

public class Hg6MrsConnRealCmdProc implements Hg6MrsConnCmdsProcessor {

	private final Hg6Config config;
	private final Hg6MrsRunner runner;

	public Hg6MrsConnRealCmdProc(Hg6Config config) throws Hg6MrsException {
		this.runner = new Hg6MrsRunner(config);
		this.config = config;
	}

	@Override
	public String getJarmilTargetDescription() {
		return "The real implementation of Hg6MrsCmds processor performing sync between local and MRS";
	}

	@Override
	public void startLoop() throws Hg6MrsException {
		runner.startLoop();
	}

	@Override
	public void stopLoop() throws Hg6MrsException {
		runner.stopLoop();
	}

	@Override
	public void synchronize() throws Hg6MrsException {
		runner.getLoop().getSyncer().synchronize();
	}

	@Override
	public boolean isLoopRunning() {
		return runner.isLoopRunning();
	}

	@Override
	public void configChanged() throws Hg6ConfigException {
		config.load();
	}

}
