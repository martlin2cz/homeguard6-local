package cz.martlin.hg6.mrs.diff;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg6.mrs.situation.GuardingStatus;
import cz.martlin.hg6.mrs.situation.SimplifiedConfig;
import cz.martlin.hg6.mrs.situation.SimplifiedGuardReport;
import cz.martlin.hg6.mrs.situation.Situation;

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
		boolean changed = !Objects.equals(from, to);
		if (changed) {
			diffs.add(SituationDifference.CHANGED_CONFIG);
		}

	}

	protected void diffReports(SituationsDiff diffs, SimplifiedGuardReport from, SimplifiedGuardReport to) {
		boolean changedReportStart = !Objects.equals(from.getStartedAt(), to.getStartedAt());
		boolean changedReportStop = !Objects.equals(from.getStartedAt(), to.getStartedAt());
		boolean changedReportDesc = !Objects.equals(from.getStartedAt(), to.getStartedAt());
		boolean changedReportItems = !Objects.equals(from.getItemsCount(), to.getItemsCount())
				|| !Objects.equals(from.getWarningsCount(), to.getWarningsCount())
				|| !Objects.equals(from.getCriticalCount(), to.getCriticalCount());

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

	protected void diffStatuses(SituationsDiff diffs, GuardingStatus from, GuardingStatus to) {
		boolean changedStatus = !Objects.equals(from, to);
		if (changedStatus) {
			diffs.add(SituationDifference.CHANGED_STATUS);
		}
	}

}
