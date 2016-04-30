package cz.martlin.hg6.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg5.web.Hg6WebApp;
import cz.martlin.hg5.web.Utils;
import cz.martlin.hg6.coreJRest.Hg6CoreConnException;

@ManagedBean(name = "hg6status")
@RequestScoped
public class Hg6Status implements Serializable {
	private static final long serialVersionUID = -7078557887374571774L;
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private static final String ERROR_SUMMARY = "hg6core status error";

	private final Hg6WebApp hg;

	private boolean running;
	private boolean success;

	public Hg6Status() {
		this.hg = new Hg6WebApp();
	}

	@PostConstruct
	public void init() {
		examineStatus();
	}

	private void examineStatus() {
		try {
			running = hg.isRunning();
			success = true;
		} catch (Hg6CoreConnException e) {
			LOG.error("Cannot detect status", e);
			Utils.error(ERROR_SUMMARY, "Cannot detect status of hg6core. Assuming stopped");
			running = false;
			success = false;
		}
	}

	public boolean isRunning() {
		return running;
	}

	public boolean isSuccess() {
		return success;
	}

	public String start() {
		try {
			hg.start();
			running = true;
		} catch (Hg6CoreConnException e) {
			LOG.error("Start invocation failed", e);
			Utils.error(ERROR_SUMMARY, "Start failed");
		}

		return null;
	}

	public String stop() {
		try {
			hg.stop();
			running = false;
		} catch (Hg6CoreConnException e) {
			LOG.error("Stop invocation failed", e);
			Utils.error(ERROR_SUMMARY, "Stop failed");
		}

		return null;
	}

}
