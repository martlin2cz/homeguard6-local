package cz.martlin.hg6.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigTestingApp {
	private static final Logger LOG = LoggerFactory.getLogger(ConfigTestingApp.class);

	public static void main(String[] args) throws Hg6ConfigException {
		Hg6Config config = Hg6Config.get();
		LOG.info("I have config: {}", config.getConfig());
		
		//config.setToAndSave(config.createDefault());

	}

}
