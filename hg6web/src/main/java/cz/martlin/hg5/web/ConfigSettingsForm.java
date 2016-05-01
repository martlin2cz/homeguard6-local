package cz.martlin.hg5.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg6.config.Configuration;
import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.hg6.config.Hg6ConfigException;
import cz.martlin.hg6.coreJRest.Hg6CoreConnException;
import cz.martlin.hg6.mrsConnJRest.Hg6MrsConnJRestException;

@RequestScoped
@ManagedBean(name = "configSettingsForm")
public class ConfigSettingsForm implements Serializable {
	private static final long serialVersionUID = 3501199313655052697L;
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final Hg6WebApp homeguard = new Hg6WebApp();

	private Configuration config = homeguard.getConfig();

	public ConfigSettingsForm() {
	}

	@PostConstruct
	public void init() {
		config.setTo(homeguard.getConfig());
		checkAndWarn();
	}

	private void checkAndWarn() {
		if (getConfig().getCriticalNoiseThreshold() < getConfig().getWarningNoiseThreshold()) {
			Utils.warn("Chybné prahy", "Pozor, varovný práh by měl být nižší, než kritický");
		}

		if (getConfig().getCriticalNoiseAmount() > getConfig().getWarningNoiseAmount()) {
			Utils.warn("Chybné množtví hluku", "Pozor, kritické množstí hluku by mělo být menší, než varovné");
		}
	}

	public Configuration getConfig() {
		return config;
	}

	public void setConfig(Configuration config) {
		this.config = config;
	}

	public String getRootDir() {
		return Hg6Config.createFile(".").getAbsolutePath();
	}

	public void save() {
		checkAndWarn();

		try {
			homeguard.setToAndSave(config);
			Utils.info("Uloženo", "Konfigurační soubor byl uložen");

		} catch (Hg6ConfigException e) {
			LOG.error("Cannot save config", e);
			Utils.error("Chyba", "Konfigurační soubor se nepodařilo uložit");

		} catch (Hg6CoreConnException e) {
			LOG.error("Config saved, but cannot inform core", e);
			Utils.error("Chyba", "Konfigurační soubor uložen, ale nepodařilo se o tom informovat core");
		} catch (Hg6MrsConnJRestException e) {
			LOG.error("Config saved, but cannot inform mrs", e);
			Utils.error("Chyba", "Konfigurační soubor uložen, ale nepodařilo se o tom informovat mrs-conn");
		}

	}

	public void reset() {
		homeguard.resetConfig(config);

		Utils.info("Obnoveno", "Změny byly vráceny (ale zatím neuloženy)");
	}

	public void reload() {
		try {
			homeguard.loadConfig();
			Utils.info("Načteno", "Konfigurační soubor byl načten znovu");

		} catch (Hg6ConfigException e) {
			LOG.error("Cannot load config", e);
			Utils.error("Chyba", "Konfigurační soubor se nepodařilo načíst");

		}
	}

	public void redefault() {
		homeguard.setConfigToDefault();

		Utils.info("Obnoveno", "Configurace nastavena na výchozí (ale zatím neuložena)");
	}

}
