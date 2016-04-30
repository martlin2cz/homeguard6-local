package cz.martlin.hg6.mrsConnLib.local;

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
import cz.martlin.hg6.mrs.diff.SituationDiffer;
import cz.martlin.hg6.mrs.diff.SituationDifference;
import cz.martlin.hg6.mrs.diff.SituationsDiff;
import cz.martlin.hg6.mrs.misc.Hg6MrsException;
import cz.martlin.hg6.mrs.situation.SimplifiedConfig;
import cz.martlin.hg6.mrs.situation.SimplifiedGuardReport;
import cz.martlin.hg6.mrs.situation.Situation;
import cz.martlin.hg6.mrs.situation.Status;

public class LocalSituationPerformer {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final Hg6Config config;
	private final Hg6CoreClient core;
	private final Hg6Database db;

	private final LocalSituationCreator local;
	private final SituationDiffer differ;

	public LocalSituationPerformer(Hg6Config config, AbstractHg6MrsConn mrs) {
		this.config = config;
		this.core = new Hg6CoreClient(config);
		this.db = new Hg6Database(config.getConfig());

		this.local = new LocalSituationCreator(config, mrs);
		this.differ = new SituationDiffer();
	}

	public void changeToSituation(Situation situation) throws Hg6MrsException {
		LOG.info("Changing situation to {}", situation);
		try {
			Situation current = local.getCurrentLocalSituation();
			SituationsDiff diff = differ.computeDifferences(current, situation);
			LOG.debug("Found changes to do: {}", diff);

			doSituationChange(situation, diff);
			LOG.info("Changed to situation {}", situation);

		} catch (Hg6CoreConnException | Hg6DbException | Hg6ConfigException | Hg6MrsException e) {
			throw new Hg6MrsException("Cannot change local situation", e);
		}
	}

	private void doSituationChange(Situation situation, SituationsDiff diff)
			throws Hg6CoreConnException, Hg6DbException, Hg6ConfigException, Hg6MrsException {

		LOG.debug("Performing change {}", diff);

		doStatusChange(situation.getStatus(), diff);
		doReportChange(situation.getReport(), diff);
		doConfigChange(situation.getConfig(), diff);
	}

	private void doStatusChange(Status status, SituationsDiff diff) throws Hg6CoreConnException, Hg6MrsException {

		if (diff.has(SituationDifference.CHANGED_CORE_STATUS)) {
			Boolean running = status.getCoreRunning();
			if (running == null) {
				LOG.warn("Trying to change status of hg core to null. Ingorning.");
			}

			if (running == true) {
				LOG.debug("Starting guarding");
				core.start();
			}

			if (running == false) {
				LOG.debug("Stopping guarding");
				core.stop();
			}
		}

		if (diff.has(SituationDifference.CHANGED_MRS_STATUS)) {
			LOG.warn("Trying to change status of hg6 mrs core. No, I won't do that.");

			// if (running == null) {
			// LOG.warn("Trying to change status of hg core to null.
			// Ingorning.");
			// }
			// if (running == true) {
			// LOG.debug("Starting mrs sync loop");
			// mrs.startLoop();
			// }
			// if (running == false) {
			// LOG.debug("Stopping mrs sync loop");
			// mrs.stopLoop();
			// }
		}
	}

	private void doReportChange(SimplifiedGuardReport report, SituationsDiff diff) throws Hg6DbException {
		if (report == null) {
			LOG.warn("Tried to do diffs on null report: " + diff);
			return;
		}

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
		if (config == null) {
			LOG.warn("Tried to do diffs on null config: " + diff);
			return;
		}

		if (diff.has(SituationDifference.CHANGED_CONFIG_INTERVAL)) {
			LOG.info("Saving changed config: {}", config);

			Configuration cfg = this.config.getConfig();

			cfg.setMrsInterval(config.getInterval());

			this.config.save();
		}
	}

}
