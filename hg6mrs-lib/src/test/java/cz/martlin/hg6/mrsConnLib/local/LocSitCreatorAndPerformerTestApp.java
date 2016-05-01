package cz.martlin.hg6.mrsConnLib.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.hg6.mrs.misc.Hg6MrsException;
import cz.martlin.hg6.mrs.situation.Situation;
import cz.martlin.hg6.mrsConnLib.local.AbstractHg6MrsConn;
import cz.martlin.hg6.mrsConnLib.local.LocalSituationCreator;
import cz.martlin.hg6.mrsConnLib.run.Hg6Mrs;

public class LocSitCreatorAndPerformerTestApp {
	private final static Logger LOG = LoggerFactory.getLogger(LocSitCreatorAndPerformerTestApp.class);

	public static void main(String[] args) throws Hg6MrsException {
		Hg6Config config = Hg6Config.get();
		AbstractHg6MrsConn mrs = new Hg6Mrs(config);

		LocalSituationCreator creator = new LocalSituationCreator(config, mrs);
		LocalSituationPerformer performer = new LocalSituationPerformer(config, mrs);

		LOG.info("Getting current situation....");
		Situation situation = creator.getCurrentLocalSituation();
		LOG.info("Current situation is: {}", situation);

		situation.getStatus().setCoreRunning(true);
		situation.getConfig().setInterval(1991);
		situation.getReport().setDescription("The testing one at " + System.currentTimeMillis());

		LOG.info("Updating to ... {}", situation);
		performer.changeToSituation(situation);
		LOG.info("Updated to: {}", situation);
	}
}
