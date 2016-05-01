package cz.martlin.hg5.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg6.config.Hg6Config;
import cz.martlin.hg6.mrsConnJRest.Hg6MrsConnClient;
import cz.martlin.hg6.mrsConnJRest.Hg6MrsConnJRestException;

@ManagedBean(name = "mrsForm")
@RequestScoped
public class MrsForm implements Serializable {
	private static final String MESSAGE_SUMMARY = "mrs conn";
	private static final long serialVersionUID = 8698084897456542056L;
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final Hg6MrsConnClient cli;

	private boolean mrsConnRunning;

	public MrsForm() {
		cli = new Hg6MrsConnClient(Hg6Config.get());
	}

	@PostConstruct
	public void init() {
		try {
			mrsConnRunning = cli.isLoopRunning();
		} catch (Hg6MrsConnJRestException e) {
			LOG.error("Cannot find out if loop is running", e);
			Utils.error(MESSAGE_SUMMARY, "Chyba při zjišťovaní stavu mrs conn");
		}

	}

	public boolean isMrsConnRunning() {
		return mrsConnRunning;
	}

	public void setMrsConnRunning(boolean mrsConnRunning) {
		this.mrsConnRunning = mrsConnRunning;
	}

	public String toggleRunning() {
		if (mrsConnRunning) {
			try {
				cli.startLoop();
				mrsConnRunning = true;
				Utils.info(MESSAGE_SUMMARY, "mrs conn sync běží");
			} catch (Hg6MrsConnJRestException e) {
				LOG.error("Cannot start mrs conn", e);
				Utils.error(MESSAGE_SUMMARY, "Spuštění se nepovedlo");
			}
		} else {
			try {
				cli.stopLoop();
				mrsConnRunning = false;
				Utils.info(MESSAGE_SUMMARY, "mrs conn sync zastaven");
			} catch (Hg6MrsConnJRestException e) {
				LOG.error("Cannot stop mrs conn", e);
				Utils.error(MESSAGE_SUMMARY, "Zastavení se nezdařilo");
			}
		}

		return null;
	}

	public String synchronize() {
		try {
			cli.synchronize();
			Utils.info(MESSAGE_SUMMARY, "Synchronizováno");
		} catch (Hg6MrsConnJRestException e) {
			LOG.error("Cannot synchronize", e);
			Utils.error(MESSAGE_SUMMARY, "Synchronizace selhala");
		}
		return null;
	}
}
