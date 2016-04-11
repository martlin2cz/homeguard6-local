package cz.martlin.hg6.app;

import java.util.Calendar;

import cz.martlin.hg5.logic.config.Hg6Config;
import cz.martlin.hg6.core.Hg6Core;
import cz.martlin.hg6.coreJRest.Hg6CommandsProcessor;
import cz.martlin.hg6.db.Hg6Database;
import cz.martlin.hg6.db.Hg6DbException;

public class Hg6CommandsProcessorImpl implements Hg6CommandsProcessor {

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
	public String getSimpleInfo() {
		try {
			return db.loadLastReport().toString();
			// TODO like this?
			//TODO fix reporting ... somehow...
		} catch (Hg6DbException e) {
			return null; // TODO exception ...
		} 
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
	public boolean configChanged() {
		return cfg.load();
	}

}
