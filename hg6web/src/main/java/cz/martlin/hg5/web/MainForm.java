package cz.martlin.hg5.web;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import cz.martlin.hg5.HomeGuardApp;
import cz.martlin.hg5.logic.config.Configuration;
import cz.martlin.hg5.logic.data.GuardingReport;

@ApplicationScoped
@ManagedBean(name = "mainForm")
public class MainForm implements Serializable {
	private static final long serialVersionUID = -5102412853399452864L;

	private final _Homeguard homeguard = new _Homeguard();
	
	public MainForm() {
	}

	public Configuration getConfig() {
		return homeguard.getConfig();
	}

	public GuardingReport getCurrentReport() {
		return homeguard.currentReport();
	}

	public GuardingReport getLastReport() {
		return homeguard.lastReport();
	}

	public boolean isRunning() {
		//TODO error handling
		return homeguard.getIsRunning();
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

	public void start() {
		homeguard.start();
	}

	public void stop() {
		homeguard.stop();
	}
}
