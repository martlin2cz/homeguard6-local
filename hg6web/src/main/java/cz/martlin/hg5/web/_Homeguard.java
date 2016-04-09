package cz.martlin.hg5.web;

import java.util.Calendar;
import java.util.Set;

import cz.martlin.hg5.logic.config.Configuration;
import cz.martlin.hg5.logic.data.GuardingReport;
import cz.martlin.hg5.logic.data.SoundTrack;
import cz.martlin.hg6.webapp.Hg6ClientWrapper;
import cz.martlin.hg6.webapp.Hg6ConfigWrapper;
import cz.martlin.hg6.webapp.Hg6DatabaseWrapper;

public class _Homeguard {

	private final Hg6ClientWrapper client;
	private final Hg6DatabaseWrapper db;
	private final Hg6ConfigWrapper cfg;

	public _Homeguard() {
		this.client = new Hg6ClientWrapper();
		this.db = new Hg6DatabaseWrapper();
		this.cfg = new Hg6ConfigWrapper();
	}

	// TODO nonparametric constructor (with config ... load/default)
	// and withexplicit config

	public static _Homeguard get() {
		return new _Homeguard(); // TODO ...
	}

	// TODO WTF???
	public static _Homeguard tryToLoadConfigAndCreate() {
		return new _Homeguard(); // TODO
	}

	public void setConfigTo(Configuration config) {
		cfg.setConfigTo(config);
	}

	public boolean saveConfig() {
		return cfg.saveConfig();
	}

	public boolean loadConfig() {
		return cfg.loadConfig();
	}

	public Configuration getConfig() {
		return cfg.getConfig();
	}

	public Set<GuardingReport> reportsAt(Calendar day) {
		return db.reportsAt(day);
	}

	public GuardingReport currentReport() {
		return db.currentReport();
	}

	public GuardingReport lastReport() {
		return db.lastReport();
	}

	public boolean saveReportsMetadata(GuardingReport report) {
		return db.saveReportsMetadata(report);
	}

	public GuardingReport getReport(Calendar date) {
		return db.getReport(date);
	}

	public Boolean getIsRunning() {
		return client.getIsRunning();
	}

	public boolean start() {
		return client.start();
	}

	public boolean stop() {
		return client.stop();
	}

	public double[] getJustSimplySamplesOfTrack(SoundTrack track) {
		return db.getJustSimplySamplesOfTrack(track);
	}

	public byte[] loadRawWavBytesOfTrack(Calendar recordedAt) {
		return db.loadRawWawBytesOfTrack(recordedAt);
	}

	public SoundTrack getTrack(Calendar recordedAt) {
		return db.getTrack(recordedAt);
	}

}
