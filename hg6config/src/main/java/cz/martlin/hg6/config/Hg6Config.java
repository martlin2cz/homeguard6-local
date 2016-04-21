package cz.martlin.hg6.config;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg5.logic.config.ConfigLoader;
import cz.martlin.hg5.logic.config.Configuration;

public class Hg6Config {
	private static final Logger LOG = LoggerFactory.getLogger(Hg6Config.class);

	private static final String HOME_PROP_NAME = "HOMEGUARD6_HOME";
	private static final File CONFIG_FILE = createFile("config.xml");

	private static Hg6Config instance = null;

	private final ConfigLoader loader = new ConfigLoader(CONFIG_FILE);
	private final Configuration config;

	private Hg6Config() {
		this.config = new Configuration();
	}

	public static Hg6Config get() {
		if (instance == null) {
			instance = new Hg6Config();

			try {
				instance.load();
			} catch (Hg6ConfigException e) {
				LOG.warn("Config file load failed. Will be used default instead.", e);
			}
		}

		return instance;
	}

	public static File createFile(String name) {
		String dir = System.getenv(HOME_PROP_NAME);
		if (dir == null) {
			LOG.warn("Hg6 home dir property {} not set, using current directory instead", HOME_PROP_NAME);

			return new File(name);
		} else {
			LOG.info("Using file or dir {} in {}. But exists?", name, dir);

			File dirFile = new File(dir);
			return new File(dirFile, name);
		}
	}

	public Configuration getConfig() {
		return config;
	}

	public void load() throws Hg6ConfigException {
		loader.load(config);
	}

	public void save() throws Hg6ConfigException {
		loader.save(config);
	}

	public void setTo(Configuration other) {
		config.setTo(other);
	}

	public void setToAndSave(Configuration other) throws Hg6ConfigException {
		setTo(other);
		save();
	}

	public Configuration makeCopy() {
		Configuration other = new Configuration();
		other.setTo(config);
		return other;
	}

	public Configuration createDefault() {
		return new Configuration();
	}

}
