package cz.martlin.hg6.mrsConnLib.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg6.mrs.diff.SituationDiffer;
import cz.martlin.hg6.mrs.diff.SituationDifference;
import cz.martlin.hg6.mrs.diff.SituationsDiff;
import cz.martlin.hg6.mrs.situation.SimplifiedConfig;
import cz.martlin.hg6.mrs.situation.SimplifiedGuardReport;
import cz.martlin.hg6.mrs.situation.Situation;
import cz.martlin.hg6.mrs.situation.Status;

public class SituationsMerger {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final SituationDiffer differ = new SituationDiffer();

	public SituationsMerger() {
	}

	public Situation computeNewSituation(Situation local, Situation remote) {
		LOG.info("Merging situations: local={}, remote={}", local, remote);

		SituationsDiff diff = differ.computeDifferences(local, remote);

		SimplifiedGuardReport report = local.getReport();
		Status status = local.getStatus();
		SimplifiedConfig config = local.getConfig();

		if (diff.has(SituationDifference.CHANGED_REPORT)) {
			LOG.debug("Report changed, using the local situation");
		} else {
			if (diff.has(SituationDifference.CHANGED_CORE_STATUS)) {
				LOG.debug("Changed core status, passing the remote to result");
				if (remote.getStatus() != null && status != null) {
					status.setCoreRunning(remote.getStatus().getCoreRunning());
				}
			}
			if (diff.has(SituationDifference.CHANGED_REPORT_STOPPED)) {
				LOG.debug("Changed report's stoppedAt, using the local one's");
				if (remote.getReport() != null && report != null) {
					report.setStoppedAt(local.getReport().getStoppedAt());
				}
			}
			if (diff.has(SituationDifference.CHANGED_REPORT_DESC)) {
				LOG.debug("Changed report's description, passing the remote to result");
				if (remote.getReport() != null && report != null) {
					report.setDescription(remote.getReport().getDescription());
				}
			}
			if (diff.has(SituationDifference.CHANGED_CONFIG_INTERVAL)) {
				LOG.debug("Changed config's interval, passing the remote to result");
				if (remote.getConfig() != null && config != null) {
					config.setTo(remote.getConfig());
				}
			}
		}

		Situation result = new Situation(status, report, config);
		LOG.info("Merged into: {}", result);
		return result;

	}

}
