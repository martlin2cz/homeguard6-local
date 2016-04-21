package cz.martlin.hg6.mrsLib.sync;

import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.hg6.mrs.Hg6MrsException;
import cz.martlin.hg6.mrs.situation.Situation;
import cz.martlin.hg6.mrsConn.Hg6MrsClient;
import cz.martlin.hg6.mrsLib.local.LocalSituationCreator;

public class SituationsMrsSyncer {
	private final SituationsSyncerCore sincer;
	private final Hg6MrsClient mrs;
	private final LocalSituationCreator creator;

	public SituationsMrsSyncer(Hg6Config config) throws Hg6MrsException {
		sincer = new SituationsSyncerCore();
		mrs = new Hg6MrsClient(config.getConfig().getMrsBaseUrl());
		creator = new LocalSituationCreator(config);
	}

	public void synchronize() throws Hg6MrsException {
		Situation local = getCurrentLocal();
		Situation remote = getCurrentRemote();

		SyncResult sync = sincer.synchronize(local, remote);

		Situation newLocal = sync.getLocal();
		Situation newRemote = sync.getRemote();

		commitNewLocal(newLocal);
		commitNewRemote(newRemote);
	}

	private Situation getCurrentLocal() throws Hg6MrsException {
		return creator.getCurrentLocalSituation();
	}

	private Situation getCurrentRemote() throws Hg6MrsException {
		return mrs.getRemoteSituation();
	}

	private void commitNewRemote(Situation newRemote) throws Hg6MrsException {
		mrs.submitLocalSituation(newRemote);
	}

	private void commitNewLocal(Situation newLocal) throws Hg6MrsException {
		creator.changeToSituation(newLocal);
	}

}
