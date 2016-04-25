package cz.martlin.hg6.mrsApp;

import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.hg6.mrs.Hg6MrsException;
import cz.martlin.hg6.mrsJRest.Hg6MrsCmdsProcessor;
import cz.martlin.hg6.run.Hg6MrsRunner;

public class Hg6MrsConnRealCmdProc implements Hg6MrsCmdsProcessor {

	private final Hg6MrsRunner runner;

	public Hg6MrsConnRealCmdProc(Hg6Config config) throws Hg6MrsException {
		runner = new Hg6MrsRunner(config);
	}

	@Override
	public String getJarmilTargetDescription() {
		return "The real implementation of Hg6MrsCmds processor performing sync between local and MRS";
	}

	@Override
	public void startLoop() {
		runner.startLoop();
	}

	@Override
	public void stopLoop() {
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

}
