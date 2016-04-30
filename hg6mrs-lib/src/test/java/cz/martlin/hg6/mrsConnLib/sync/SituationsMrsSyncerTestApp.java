package cz.martlin.hg6.mrsConnLib.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.hg6.mrs.misc.Hg6MrsException;
import cz.martlin.hg6.mrsConnLib.local.AbstractHg6MrsConn;
import cz.martlin.hg6.mrsConnLib.run.Hg6Mrs;
import cz.martlin.hg6.mrsConnLib.sync.SituationsMrsSyncer;

public class SituationsMrsSyncerTestApp {

	private static final Logger LOG = LoggerFactory.getLogger(SituationsMrsSyncerTestApp.class);

	public static void main(String[] args) throws Hg6MrsException {
		Hg6Config config = Hg6Config.get();
		AbstractHg6MrsConn mrs = new Hg6Mrs(config);
		SituationsMrsSyncer syncer = new SituationsMrsSyncer(config, mrs);

		LOG.info("Synchronizing");
		syncer.synchronize();
		LOG.info("Synchronized");

	}

}
