package cz.martlin.hg5.logic.config;

import java.io.File;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg6.config.Configuration;
import cz.martlin.hg6.config.Hg6ConfigException;
import cz.martlin.jaxon.config.Config;
import cz.martlin.jaxon.jaxon.JaxonConverter;
import cz.martlin.jaxon.jaxon.JaxonException;

/**
 * Implements loading and saving of {@link Configuration}.
 * 
 * @author martin
 *
 */
public class ConfigLoader implements Serializable {
	private static final long serialVersionUID = 7148464013475790035L;

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final JaxonConverter jaxon;
	private final File file;

	public ConfigLoader(File file) {
		this.file = file;

		Config config = new Config();
		this.jaxon = new JaxonConverter(config);
	}

	/**
	 * Loads config file into given config instance.
	 * 
	 * @param config
	 * @throws Hg6ConfigException
	 * @throws JaxonException
	 */
	public void load(Configuration config) throws Hg6ConfigException {
		if (file == null) {
			throw new Hg6ConfigException("Config file not specified");
		}

		LOG.info("Loading config file from:  {}", file.getAbsolutePath());

		Configuration fromFile;
		try {
			fromFile = (Configuration) jaxon.objectFromFile(file);
		} catch (JaxonException e) {
			throw new Hg6ConfigException("Cannot load config file", e);
		}
		config.setTo(fromFile);

		LOG.info("Config file loaded");

	}

	/**
	 * Saves given config into file.
	 * 
	 * @param config
	 * @throws Hg6ConfigException
	 */
	public void save(Configuration config) throws Hg6ConfigException {
		if (file == null) {
			throw new Hg6ConfigException("Config file not specified");
		}

		LOG.info("Saving config into file:  {}", file.getAbsolutePath());

		try {
			jaxon.objectToFile(config, file);
		} catch (JaxonException e) {
			throw new Hg6ConfigException("Cannot save config file", e);
		}

		LOG.info("Config file saved");
	}

}
