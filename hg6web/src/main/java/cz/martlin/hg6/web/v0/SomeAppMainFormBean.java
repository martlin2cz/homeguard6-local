package cz.martlin.hg6.web.v0;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import cz.martlin.hg6.coreJRest.v0.SomeAppClient;

@SessionScoped
@ManagedBean(name = "main")
public class SomeAppMainFormBean {

	private final SomeAppClient cli = new SomeAppClient();

	private String status = "unknown";

	public SomeAppMainFormBean() {
	}

	public String getStatus() {
		return status;
	}

	public void start() {
		boolean success = cli.start();
		if (success) {
			status = "Running";
		} else {
			status = "failed";
		}
	}

	public void stop() {
		boolean success = cli.stop();
		if (success) {
			status = "Stopped";
		} else {
			status = "failed";
		}
	}

}
