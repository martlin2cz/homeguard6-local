package cz.martlin.hg6.mrsConnApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.hg6.mrs.misc.Hg6MrsException;
import cz.martlin.hg6.mrsConnJRest.Hg6MrsConnCmdsProcessor;
import cz.martlin.hg6.mrsConnJRest.Hg6MrsConnServer;

public class Hg6MrsConnApp {

	private static final Logger LOG = LoggerFactory.getLogger(Hg6MrsConnApp.class);

	public static void main(String[] args) throws Hg6MrsException {
		Hg6Config config = Hg6Config.get();

		//Hg6MrsCmdsProcessor processor = new TestingHg6MrsConnCmdsProcImpl();

		 Hg6MrsConnCmdsProcessor processor = new Hg6MrsConnRealCmdProc(config);

		Hg6MrsConnServer server = new Hg6MrsConnServer(config, processor);
		server.startWaiter();

		LOG.info("MRS Conn local started");

	}

}
