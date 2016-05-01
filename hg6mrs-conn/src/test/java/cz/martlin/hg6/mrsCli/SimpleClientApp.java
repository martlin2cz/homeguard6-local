package cz.martlin.hg6.mrsCli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg6.mrs.misc.Hg6MrsException;
import cz.martlin.hg6.mrs.situation.Situation;
import cz.martlin.hg6.mrs.test.TestingSituationsCreator;
import cz.martlin.hg6.mrsCli.Hg6MrsClient;

public class SimpleClientApp {
	private static final Logger LOG = LoggerFactory.getLogger(SimpleClientApp.class);

	private final static TestingSituationsCreator creator = new TestingSituationsCreator();

	public static void main(String[] args) throws Hg6MrsException {

		final String url = "http://localhost:9080/mrs/ws";
		Hg6MrsClient cli = new Hg6MrsClient(url);

		Situation s1 = cli.getRemoteSituation();
		LOG.info("The remote situation is currently: " + s1);

		Situation s2 = creator.createEmpty();
		cli.submitLocalSituation(s2);
		LOG.info("Submitted this situation to remote:" + s2);

		Situation s3 = cli.getRemoteSituation();
		LOG.info("The remote situation is currently:" + s3);

		Situation s4 = creator.createBasic();
		cli.submitLocalSituation(s4);
		LOG.info("Submitted this situation to remote:" + s4);

		Situation s5 = cli.getRemoteSituation();
		LOG.info("The remote situation is currently:" + s5);
	}

}
