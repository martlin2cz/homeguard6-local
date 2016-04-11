package cz.martlin.hg5.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import cz.martlin.hg5.logic.config.Configuration;

@RequestScoped
@ManagedBean(name = "configSettingsForm")
public class ConfigSettingsForm implements Serializable {
	private static final long serialVersionUID = 3501199313655052697L;

	private final Hg6WebApp homeguard = new Hg6WebApp();

	private Configuration config = homeguard.getConfig(); // TODO like that?

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

	public void save() {
		checkAndWarn();

		boolean success = homeguard.setToAndSave(config);
		if (success) {
			Utils.info("Uloženo", "Konfigurační soubor byl uložen");
		} else {
			Utils.error("Chyba", "Konfigurační soubor se nepodařilo uložit");
		}
	}

	public void reset() {
		homeguard.resetConfig(config);

		Utils.info("Obnoveno", "Změny byly vráceny (ale zatím neuloženy)");
	}

	public void reload() {

		boolean success = homeguard.loadConfig();
		if (success) {
			Utils.info("Načteno", "Konfigurační soubor byl načten znovu");
		} else {
			Utils.error("Chyba", "Konfigurační soubor se nepodařilo načíst");
		}
	}

	public void redefault() {
		homeguard.setConfigToDefault();

		Utils.info("Obnoveno", "Configurace nastavena na výchozí (ale zatím neuložena)");
	}

}
