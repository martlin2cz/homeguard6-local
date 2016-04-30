package cz.martlin.hg6.mrs.diff;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg6.mrs.situation.SimplifiedConfig;
import cz.martlin.hg6.mrs.situation.SimplifiedGuardReport;
import cz.martlin.hg6.mrs.situation.Situation;
import cz.martlin.hg6.mrs.situation.Status;

public class SituationDiffer {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public SituationDiffer() {
	}

	public SituationsDiff computeDifferences(Situation from, Situation to) {
		LOG.info("Computing diff between {} and {}", from, to);

		SituationsDiff diffs = new SituationsDiff();

		diffStatuses(diffs, from.getStatus(), to.getStatus());

		diffReports(diffs, from.getReport(), to.getReport());

		diffConfigs(diffs, from.getConfig(), to.getConfig());

		LOG.debug("Computed diff: {}", diffs);
		return diffs;
	}

	protected void diffConfigs(SituationsDiff diffs, SimplifiedConfig from, SimplifiedConfig to) {
		if (from == null && to == null) {
			return;
		}

		boolean changedInterval = true;

		if (from != null && to != null) {
			changedInterval = from.getInterval() != to.getInterval();
		}

		if (changedInterval) {
			diffs.add(SituationDifference.CHANGED_CONFIG_INTERVAL);
		}

	}

	protected void diffReports(SituationsDiff diffs, SimplifiedGuardReport from, SimplifiedGuardReport to) {
		if (from == null && to == null) {
			return;
		}
		boolean changedReportStart = true, changedReportStop = true, changedReportDesc = true,
				changedReportItems = true;

		if (from != null && to != null) {
			changedReportStart = !Objects.equals(from.getStartedAt(), to.getStartedAt());
			changedReportStop = !Objects.equals(from.getStartedAt(), to.getStartedAt());
			changedReportDesc = !Objects.equals(from.getStartedAt(), to.getStartedAt());
			changedReportItems = !Objects.equals(from.getItemsCount(), to.getItemsCount())
					|| !Objects.equals(from.getWarningsCount(), to.getWarningsCount())
					|| !Objects.equals(from.getCriticalCount(), to.getCriticalCount());
		}

		if (changedReportStart) {
			diffs.add(SituationDifference.CHANGED_REPORT);
		}
		if (changedReportStop || changedReportDesc) {
			diffs.add(SituationDifference.CHANGED_REPORT_METADATA);
		}
		if (changedReportItems) {
			diffs.add(SituationDifference.CHANGED_REPORT_ITEMS);
		}

	}

	protected void diffStatuses(SituationsDiff diffs, Status from, Status to) {
		if (from == null && to == null) {
			return;
		}

		boolean changedCoreStatus = true;
		boolean changedMrsConnStatus = true;

		if (from != null && to != null) {
			changedCoreStatus = !Objects.equals(from.getCoreRunning(), to.getCoreRunning());
			changedMrsConnStatus = !Objects.equals(from.getMrsConnRunning(), to.getMrsConnRunning());
		}

		if (changedCoreStatus) {
			diffs.add(SituationDifference.CHANGED_CORE_STATUS);
		}
		
		if (changedMrsConnStatus) {
			diffs.add(SituationDifference.CHANGED_MRS_STATUS);
		}
	}

}
