package cz.martlin.hg5.web;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import cz.martlin.hg5.logic.config.Configuration;
import cz.martlin.hg5.logic.config.Hg6Config;
import cz.martlin.hg5.logic.data.GuardingReport;
import cz.martlin.hg6.coreJRest.Hg6CoreClient;
import cz.martlin.hg6.coreJRest.Hg6CoreConnException;
import cz.martlin.hg6.db.Hg6Database;
import cz.martlin.hg6.db.Hg6DbException;

public class Hg6WebApp implements Serializable {

	private static final long serialVersionUID = -3732393632535747550L;

	private final Hg6CoreClient client;
	private final Hg6Database db;
	private final Hg6Config cfg;

	public Hg6WebApp() {
		this(Hg6Config.get());
	}

	public Hg6WebApp(Hg6Config cfg) {

		this.client = new Hg6CoreClient();
		this.db = new Hg6Database(cfg.getConfig());
		this.cfg = cfg;
	}

	public void setConfigTo(Configuration config) {
		cfg.setTo(config);
	}

	public boolean saveConfig() {
		return cfg.save();
	}

	public boolean loadConfig() {
		return cfg.load();
	}

	public Configuration getConfig() {
		return cfg.getConfig();
	}

	public boolean setToAndSave(Configuration config) {
		boolean succ = cfg.setToAndSave(config);
		try {
			client.configChanged();
		} catch (Hg6CoreConnException e) {
			//FIXME HACK
			Utils.warn("Pozor", "Konfigurace byla uložena, ale nepodařilo se informovat o změně homeguard.");
		}
		return succ;
	}

	public void setConfigToDefault() {
		cfg.setTo(cfg.createDefault());
	}

	public void resetConfig(Configuration config) {
		config.setTo(cfg.getConfig());

	}

	/////////////////////////////////////////////////////////////////////////////////////

	public Set<GuardingReport> reportsAt(Calendar day) throws Hg6DbException {
		return db.loadReportsAtDay(day);
	}

	public GuardingReport currentReport() throws Hg6DbException, Hg6CoreConnException {
		Calendar startedAt = client.getCurrentStartedAt();
		return db.loadReport(startedAt);
	}

	public GuardingReport lastReport() throws Hg6DbException {
		return db.loadLastReport();
	}

	public void saveReportsMetadata(GuardingReport report) throws Hg6DbException {
		db.saveMetadataOfReport(report);
	}

	public GuardingReport getReport(Calendar date) throws Hg6DbException {
		return db.loadReport(date);
	}

	public boolean isRunning() throws Hg6CoreConnException {
		return client.isRunning();
	}

	public void start() throws Hg6CoreConnException {
		client.start();
	}

	public void stop() throws Hg6CoreConnException {
		client.stop();
	}



}