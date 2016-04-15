package cz.martlin.hg6.mrsApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg5.logic.config.Configuration;
import cz.martlin.hg5.logic.config.Hg6Config;
import cz.martlin.hg6.mrsLib.Hg6MrsConnector;

public class MrsConnectorApp {

	private static final Logger LOG = LoggerFactory.getLogger(MrsConnectorApp.class);

	public static void main(String[] args) {
		Configuration config = Hg6Config.get().getConfig();
		Hg6MrsConnector conn = new Hg6MrsConnector(config);

		conn.start();

		LOG.info("MRS some started");

	}

}
