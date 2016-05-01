package cz.martlin.hg6.mrsConnLib.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg6.config.Configuration;
import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.hg6.mrs.misc.Hg6MrsException;
import cz.martlin.hg6.mrsConnLib.local.AbstractHg6MrsConn;
import cz.martlin.hg6.mrsConnLib.sync.SituationsMrsSyncer;
import cz.martlin.jrest.misc.Interruptable;

public class Hg6MrsSyncLoop implements Interruptable {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private static final int MS_IN_MIN = 60 * 1000;

	private final SituationsMrsSyncer syncer;
	private final Configuration config;

	private boolean interrupted;

	public Hg6MrsSyncLoop(Hg6Config config, AbstractHg6MrsConn mrs) throws Hg6MrsException {
		super();
		this.config = config.getConfig();
		this.syncer = new SituationsMrsSyncer(config, mrs);
	}

	public SituationsMrsSyncer getSyncer() {
		return syncer;
	}

	@Override
	public void interrupt() {
		interrupted = true;
	}

	public void run() {
		interrupted = false;

		LOG.info("Sync performer running");

		while (!interrupted) {
			try {
				LOG.debug("Synchronizing");
				syncer.synchronize();
			} catch (Hg6MrsException e) {
				LOG.info("Synchronization failed", e);
			}

			long wait = MS_IN_MIN * config.getMrsInterval();
			try {
				LOG.trace("Waiting {} ms", wait);
				Thread.sleep(wait);
			} catch (InterruptedException e) {
			}
		}

		LOG.info("Synchronization stopped");
	}

}
