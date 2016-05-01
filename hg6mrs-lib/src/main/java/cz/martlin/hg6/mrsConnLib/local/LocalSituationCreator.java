package cz.martlin.hg6.mrsConnLib.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg5.logic.data.GuardingReport;
import cz.martlin.hg6.config.Configuration;
import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.hg6.config.Hg6ConfigException;
import cz.martlin.hg6.coreJRest.Hg6CoreClient;
import cz.martlin.hg6.coreJRest.Hg6CoreConnException;
import cz.martlin.hg6.db.Hg6Database;
import cz.martlin.hg6.db.Hg6DbException;
import cz.martlin.hg6.mrs.misc.Hg6MrsException;
import cz.martlin.hg6.mrs.situation.SimplifiedConfig;
import cz.martlin.hg6.mrs.situation.SimplifiedGuardReport;
import cz.martlin.hg6.mrs.situation.Situation;
import cz.martlin.hg6.mrs.situation.Status;

public class LocalSituationCreator {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final Hg6Config config;
	private final Hg6CoreClient core;
	private final Hg6Database db;
	private final AbstractHg6MrsConn mrs;

	public LocalSituationCreator(Hg6Config config, AbstractHg6MrsConn mrs) {
		this.config = config;
		this.core = new Hg6CoreClient(config);
		this.db = new Hg6Database(config.getConfig());
		this.mrs = mrs;
	}

	public Situation getCurrentLocalSituation() {
		LOG.info("Getting current local situation");

		Status status = getCurrentStatus();
		SimplifiedGuardReport report = getCurrentReport();
		SimplifiedConfig config = getCurrentConfig();

		Situation situation = new Situation(status, report, config);
		LOG.info("Got local situation: {}", situation);
		return situation;
	}

	private Status getCurrentStatus() {
		LOG.debug("Getting current local status");
		Status status = new Status();

		Boolean coreRunning;
		try {
			coreRunning = core.isRunning();
		} catch (Hg6CoreConnException e) {
			LOG.error("Cannot infer whether is core running", e);
			coreRunning = null;
		}
		status.setCoreRunning(coreRunning);

		Boolean mrsRunning;
		try {
			mrsRunning = mrs.isLoopRunning();
		} catch (Hg6MrsException e) {
			LOG.error("Cannot infer whether is mrs conn loop running", e);
			mrsRunning = null;
		}
		status.setMrsConnRunning(mrsRunning);

		LOG.debug("The status is {}", status);
		return status;
	}

	private SimplifiedGuardReport getCurrentReport() {
		LOG.debug("Getting current report");

		SimplifiedGuardReport rep;
		try {
			GuardingReport report = db.loadNewestReport();
			rep = SimplifiedGuardReport.create(report);
		} catch (Hg6DbException e) {
			LOG.error("Cannot load report", e);
			rep = null;
		}

		LOG.debug("The report is {}", rep);
		return rep;
	}

	private SimplifiedConfig getCurrentConfig() {
		LOG.debug("Getting current config");

		SimplifiedConfig cfg;
		try {
			this.config.load();
			Configuration config = this.config.getConfig();
			cfg = SimplifiedConfig.create(config);
		} catch (Hg6ConfigException e) {
			LOG.error("Cannot load config", e);
			cfg = null;
		}

		LOG.debug("The config is {}", cfg);
		return cfg;
	}

}
