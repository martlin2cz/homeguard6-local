package cz.martlin.hg5.web;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import cz.martlin.hg5.HomeGuardApp;
import cz.martlin.hg5.logic.config.Configuration;
import cz.martlin.hg5.logic.data.GuardingReport;
import cz.martlin.hg6.coreJRest.Hg6CoreConnException;
import cz.martlin.hg6.db.Hg6DbException;

@ApplicationScoped
@ManagedBean(name = "mainForm")
public class MainForm implements Serializable {
	private static final long serialVersionUID = -5102412853399452864L;

	private final Hg6WebApp homeguard = new Hg6WebApp();

	public MainForm() {
	}

	@Deprecated
	public Configuration getXXX_Config() {
		return homeguard.getConfig();
	}

	@Deprecated
	public GuardingReport getXXX_CurrentReport() {
		try {
			return homeguard.currentReport();
		} catch (Hg6DbException | Hg6CoreConnException e) {
			Utils.error("Cannot load current report", e.getMessage());
			return null;
		}
	}

	@Deprecated
	public GuardingReport getXXX_LastReport() {
		try {
			return homeguard.lastReport();
		} catch (Hg6DbException e) {
			Utils.error("Cannot load last report", e.getMessage());
			return null;
		}
	}

	@Deprecated
	public boolean isXXX_Running() {
		try {
			return homeguard.isRunning();
		} catch (Hg6CoreConnException e) {
			Utils.error("Cannot find running/not running. Assuming not", e.getMessage());
			return false;
		}
	}

	public String getAppName() {
		return HomeGuardApp.getAppName();
	}

	public String getVersion() {
		return HomeGuardApp.getVersion();
	}

	public String getAuthor() {
		return HomeGuardApp.getAuthor();
	}

	/////////////////////////////////////////////////////////////////////////////

	@Deprecated
	public void xxx_start() {
		try {
			homeguard.start();
		} catch (Hg6CoreConnException e) {
			Utils.error("Start failed", e.getMessage());
		}
	}

	@Deprecated
	public void xxx_stop() {
		try {
			homeguard.stop();
		} catch (Hg6CoreConnException e) {
			Utils.error("Stop failed", e.getMessage());
		}
	}
}
