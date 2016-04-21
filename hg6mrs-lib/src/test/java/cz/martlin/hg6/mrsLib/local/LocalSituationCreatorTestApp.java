package cz.martlin.hg6.mrsLib.local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.hg6.mrs.Hg6MrsException;
import cz.martlin.hg6.mrs.situation.GuardingStatus;
import cz.martlin.hg6.mrs.situation.Situation;
import cz.martlin.hg6.mrsLib.local.LocalSituationCreator;

public class LocalSituationCreatorTestApp {
	private final static Logger LOG = LoggerFactory.getLogger(LocalSituationCreatorTestApp.class);

	public static void main(String[] args) throws Hg6MrsException {
		Hg6Config config = Hg6Config.get();
		LocalSituationCreator creator = new LocalSituationCreator(config);

		Situation situation = creator.getCurrentLocalSituation();
		LOG.info("Current situation is: {}", situation);

		situation.setStatus(GuardingStatus.STOPPED);
		situation.getConfig().setInterval(20);
		situation.getReport().setDescription("The testing one at " + System.currentTimeMillis());
		
		
		creator.changeToSituation(situation);
		LOG.info("Updated to: {}", situation);
	}
}
