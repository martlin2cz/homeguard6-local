package cz.martlin.hg6.app;

import java.util.Calendar;

import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.hg6.config.Hg6ConfigException;
import cz.martlin.hg6.core.Hg6Core;
import cz.martlin.hg6.coreJRest.Hg6CoreCmdsProcessor;
import cz.martlin.hg6.db.Hg6Database;
import cz.martlin.hg6.db.Hg6DbException;

public class Hg6CommandsProcessorImpl implements Hg6CoreCmdsProcessor {

	private final Hg6Core hg;
	private final Hg6Config cfg;
	private final Hg6Database db;

	public Hg6CommandsProcessorImpl(Hg6Core homeguard) {
		this.hg = homeguard;
		this.cfg = Hg6Config.get();
		this.db = new Hg6Database(cfg.getConfig());
	}

	@Override
	public void start() {
		hg.start();
	}

	@Override
	public void stop() {
		hg.stop();
	}

	@Override
	public boolean getIsRunning() {
		return hg.isRunning();
	}

	@Override
	public String getSimpleInfo() throws Hg6DbException {
		return db.loadLastReport().toString();
	}

	@Override
	public Calendar getCurrentReportStartedAt() {
		if (hg.isRunning()) {
			return hg.currentlyRunningReport().getStartedAt();
		} else {
			return null;
		}
	}

	@Override
	public void configChanged() throws Hg6ConfigException {
		cfg.load();
	}

	@Override
	public String getJarmilTargetDescription() {
		return "Completed Hg6CommandsProcessor implementation using the hg6core";
	}

}
