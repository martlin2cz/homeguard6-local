package cz.martlin.hg6.mrsLib.sync;

import cz.martlin.hg6.mrs.diff.SituationDiffer;
import cz.martlin.hg6.mrs.diff.SituationDifference;
import cz.martlin.hg6.mrs.diff.SituationsDiff;
import cz.martlin.hg6.mrs.situation.Situation;

public class SituationsSyncerCore {
	private final SituationDiffer differ = new SituationDiffer();

	public SyncResult synchronize(Situation local, Situation remote) {
		SituationsDiff diff = differ.computeDifferences(local, remote);

		Situation newRemote;
		Situation newLocal;

		if (diff.has(SituationDifference.CHANGED_REPORT)) {
			newLocal = local;
			newRemote = local.duplicate();

		} else {
			newLocal = local.duplicate();
			newRemote = remote.duplicate();

			newRemote.setReport(newLocal.getReport());
			newLocal.setConfig(newRemote.getConfig());
			newLocal.setStatus(newRemote.getStatus());
		}

		return new SyncResult(newRemote, newLocal);
	}
}
