package cz.martlin.hg6.mrs.diff;

import cz.martlin.hg6.mrs.situation.SimplifiedConfig;
import cz.martlin.hg6.mrs.situation.Situation;
import cz.martlin.hg6.mrs.situation.Status;

public class DifferencesCopier {

	public DifferencesCopier() {
	}

	public void doChanges(SituationsDiff diff, Situation from, Situation to) {
		for (SituationDifference difference : diff.getDifferences()) {
			doChange(difference, from, to);
		}
	}

	public void doChange(SituationDifference difference, Situation from, Situation to) {
		switch (difference) {
		case CHANGED_CONFIG_INTERVAL:
			if (to.getConfig() == null) {
				to.setConfig(new SimplifiedConfig());
			}
			if (from.getConfig() != null) {
				to.getConfig().setInterval(from.getConfig().getInterval());
			}
			break;
		case CHANGED_MRS_STATUS:
			if (to.getStatus() == null) {
				to.setStatus(new Status());
			}
			if (from.getStatus() != null) {
				to.getStatus().setMrsConnRunning(from.getStatus().getMrsConnRunning());
			}
			break;
		case CHANGED_CORE_STATUS:
			if (to.getStatus() == null) {
				to.setStatus(new Status());
			}
			if (from.getStatus() != null) {
				to.getStatus().setCoreRunning(from.getStatus().getCoreRunning());
			}
			break;
		case CHANGED_REPORT:
			if (from.getReport() != null) {
				to.setReport(from.getReport().cloneQuietly());
			} else {
				to.setReport(null);
			}
			break;
		case CHANGED_REPORT_ITEMS:
			if (from.getReport() != null) {
				to.getReport().setItemsTo(from.getReport());
			}
			break;
		case CHANGED_REPORT_STOPPED:
			if (from.getReport() != null) {
				to.getReport().setStoppedAt(from.getReport().getStoppedAt());
			}
			break;
		case CHANGED_REPORT_DESC:
			if (from.getReport() != null) {
				to.getReport().setDescription(from.getReport().getDescription());
			}
		default:
			throw new IllegalArgumentException("Unsupported difference: " + difference);
		}
	}
}
