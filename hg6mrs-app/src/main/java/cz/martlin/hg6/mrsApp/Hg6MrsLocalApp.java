package cz.martlin.hg6.mrsApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.hg6.mrs.Hg6MrsException;
import cz.martlin.hg6.mrsJRest.Hg6MrsCmdsProcessor;
import cz.martlin.hg6.mrsJRest.Hg6MrsConnectorServer;

public class Hg6MrsLocalApp {

	private static final Logger LOG = LoggerFactory.getLogger(Hg6MrsLocalApp.class);

	public static void main(String[] args) throws Hg6MrsException {
		Hg6Config config = Hg6Config.get();

		//Hg6MrsCmdsProcessor processor = new TestingHg6MrsConnCmdsProcImpl();

		 Hg6MrsCmdsProcessor processor = new Hg6MrsConnRealCmdProc(config);

		Hg6MrsConnectorServer server = new Hg6MrsConnectorServer(config, processor);
		server.startWaiter();

		LOG.info("MRS Conn local started");

	}

}
