package cz.martlin.hg6.mrsLib.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg5.logic.config.Configuration;
import cz.martlin.hg5.logic.data.GuardingReport;
import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.hg6.config.Hg6ConfigException;
import cz.martlin.hg6.coreJRest.Hg6CoreClient;
import cz.martlin.hg6.coreJRest.Hg6CoreConnException;
import cz.martlin.hg6.db.Hg6Database;
import cz.martlin.hg6.db.Hg6DbException;
import cz.martlin.hg6.mrs.Hg6MrsException;
import cz.martlin.hg6.mrs.diff.SituationDiffer;
import cz.martlin.hg6.mrs.diff.SituationDifference;
import cz.martlin.hg6.mrs.diff.SituationsDiff;
import cz.martlin.hg6.mrs.situation.GuardingStatus;
import cz.martlin.hg6.mrs.situation.SimplifiedConfig;
import cz.martlin.hg6.mrs.situation.SimplifiedGuardReport;
import cz.martlin.hg6.mrs.situation.Situation;

public class LocalSituationCreator {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final Hg6Config config;
	private final Hg6CoreClient core;
	private final Hg6Database db;

	private final SituationDiffer differ;

	public LocalSituationCreator(Hg6Config config) {
		this.config = config;
		this.core = new Hg6CoreClient();
		this.db = new Hg6Database(config.getConfig());

		this.differ = new SituationDiffer();
	}

	public Situation getCurrentLocalSituation() throws Hg6MrsException {
		LOG.info("Getting current local situation");
		try {
			GuardingStatus status = getCurrentStatus();
			SimplifiedGuardReport report = getCurrentReport();
			SimplifiedConfig config = getCurrentConfig();

			return new Situation(status, report, config);
		} catch (Hg6CoreConnException | Hg6DbException | Hg6ConfigException e) {
			throw new Hg6MrsException("Cannot infer local situation", e);
		}
	}

	private GuardingStatus getCurrentStatus() throws Hg6CoreConnException {
		LOG.debug("Getting current local situation");
		GuardingStatus status;

		if (core.isRunning()) {
			status = GuardingStatus.STARTED;
		} else {
			status = GuardingStatus.STOPPED;
		}

		LOG.debug("The status is {}", status);
		return status;
	}

	private SimplifiedGuardReport getCurrentReport() throws Hg6DbException {
		LOG.debug("Getting current report");

		GuardingReport report = db.loadNewestReport();
		SimplifiedGuardReport rep = SimplifiedGuardReport.create(report);

		LOG.debug("The report is {}", rep);
		return rep;
	}

	private SimplifiedConfig getCurrentConfig() throws Hg6ConfigException {
		LOG.debug("Getting current config");

		this.config.load();
		Configuration config = this.config.getConfig();
		SimplifiedConfig cfg = SimplifiedConfig.create(config);

		LOG.debug("The config is {}", cfg);
		return cfg;
	}

	public void changeToSituation(Situation situation) throws Hg6MrsException {
		LOG.info("Changing situation to {}", situation);
		try {
			Situation current = getCurrentLocalSituation();
			SituationsDiff diff = differ.computeDifferences(current, situation);
			LOG.debug("Found changes to do: {}", diff);

			doSituationChange(situation, diff);
			LOG.info("Changed to {}", situation);

		} catch (Hg6MrsException | Hg6CoreConnException | Hg6DbException | Hg6ConfigException e) {
			throw new Hg6MrsException("Cannot change local situation", e);
		}
	}

	private void doSituationChange(Situation situation, SituationsDiff diff)
			throws Hg6CoreConnException, Hg6DbException, Hg6ConfigException {

		LOG.debug("Performing change {}", diff);

		doStatusChange(situation.getStatus(), diff);
		doReportChange(situation.getReport(), diff);
		doConfigChange(situation.getConfig(), diff);
	}

	private void doStatusChange(GuardingStatus status, SituationsDiff diff) throws Hg6CoreConnException {

		if (diff.has(SituationDifference.CHANGED_STATUS)) {
			if (GuardingStatus.STARTED.equals(status)) {
				LOG.debug("Starting guarding");
				core.start();
			}
			if (GuardingStatus.STOPPED.equals(status)) {
				LOG.debug("Stopping guarding");
				core.stop();
			}
		}
	}

	private void doReportChange(SimplifiedGuardReport report, SituationsDiff diff) throws Hg6DbException {
		if (diff.has(SituationDifference.CHANGED_REPORT)) {
			LOG.warn("Report have been changed. God bless us");
		}

		if (diff.has(SituationDifference.CHANGED_REPORT_METADATA)) {
			LOG.info("Saving changed metadata of report: {}", report);

			GuardingReport rep = db.loadNewestReport();

			rep.setDescription(report.getDescription());

			db.saveMetadataOfReport(report);
		}

		if (diff.has(SituationDifference.CHANGED_REPORT_ITEMS)) {
			LOG.warn("Report items have been changed. Ingoring");
		}
	}

	private void doConfigChange(SimplifiedConfig config, SituationsDiff diff) throws Hg6ConfigException {

		if (diff.has(SituationDifference.CHANGED_CONFIG)) {
			LOG.info("Saving changed config: {}", config);

			Configuration cfg = this.config.getConfig();

			cfg.setMrsInterval(config.getInterval());

			this.config.save();
		}
	}
}
