package cz.martlin.hg6.mrsLib.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.hg6.mrs.Hg6MrsException;

public class SituationsMrsSyncerTestApp {

	private static final Logger LOG = LoggerFactory.getLogger(SituationsMrsSyncerTestApp.class);

	public static void main(String[] args) throws Hg6MrsException {
		Hg6Config config = Hg6Config.get();
		SituationsMrsSyncer syncer = new SituationsMrsSyncer(config);

		LOG.info("Synchronizing");
		syncer.synchronize();
		LOG.info("Synchronized");

	}

}
