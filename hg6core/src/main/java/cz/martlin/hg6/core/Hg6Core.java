package cz.martlin.hg6.core;

import cz.martlin.hg5.logic.data.GuardingReport;
import cz.martlin.hg5.logic.guard.GuardingPerformer;
import cz.martlin.hg6.config.Configuration;
import cz.martlin.hg6.db.Hg6Database;

public class Hg6Core {

	private final GuardingPerformer performer;
	private final Hg6Database db;
	// XXX private final ImprovedAudioProcessor audioProcessor;

	private final Configuration config;

	public Hg6Core(Configuration config) {
		this.config = config;
		this.performer = new GuardingPerformer(config);
		this.db = new Hg6Database(config);
		// this.audioProcessor = new ImprovedAudioProcessor(config);
	}

	public Configuration getConfig() {
		return config;
	}

	///////////////////////////////////////////////////////////////////////////

	public synchronized void start() {
		performer.start();
	}

	public synchronized void stop() {
		performer.stop();
	}

	public synchronized boolean isRunning() {
		return performer.isGuardingRunning();
	}

	///////////////////////////////////////////////////////////////////////////
	/**
	 * If running returns current report. Else fails with NullPointerException,
	 * so be careful!
	 * 
	 * @return
	 */
	public synchronized GuardingReport currentlyRunningReport() {
		return performer.getCurrentInstance().getReport();
	}

	// TODO generate chart & other export formats (csv?) ...

	///////////////////////////////////////////////////////////////////////////

}
