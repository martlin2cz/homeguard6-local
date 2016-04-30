package cz.martlin.hg6.mrsConnLib.sync;

import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.hg6.mrs.misc.Hg6MrsException;
import cz.martlin.hg6.mrs.situation.Situation;
import cz.martlin.hg6.mrsCli.Hg6MrsClient;
import cz.martlin.hg6.mrsConnLib.local.AbstractHg6MrsConn;
import cz.martlin.hg6.mrsConnLib.local.LocalSituationCreator;
import cz.martlin.hg6.mrsConnLib.local.LocalSituationPerformer;

public class SituationsMrsSyncer {
	private final SituationsSyncerCore sincer;
	private final Hg6MrsClient mrs;
	private final LocalSituationCreator creator;
	private final LocalSituationPerformer performer;

	public SituationsMrsSyncer(Hg6Config config, AbstractHg6MrsConn mrs) throws Hg6MrsException {
		this.sincer = new SituationsSyncerCore();
		this.mrs = new Hg6MrsClient(config.getConfig().getMrsBaseUrl());
		this.creator = new LocalSituationCreator(config, mrs);
		this.performer = new LocalSituationPerformer(config, mrs);
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
		performer.changeToSituation(newLocal);
	}

}
