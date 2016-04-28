package cz.martlin.hg6.mrs.situation;

import java.io.Serializable;

import cz.martlin.jaxon.jaxon.JaxonSerializable;

public class Situation implements Serializable, JaxonSerializable, Cloneable {
	private static final long serialVersionUID = 4730612357725321414L;

	private static final String DESC = "Hg6 local/remote situation";

	private GuardingStatus status;
	private SimplifiedGuardReport report;
	private SimplifiedConfig config;

	public Situation() {
		super();
		this.status = GuardingStatus.UNKNOWN;
		// TODO will cause everything to fail god damn lol
		// this.report = new SimplifiedGuardReport();
		// this.config = new SimplifiedConfig();
	}

	public Situation(GuardingStatus status, SimplifiedGuardReport report, SimplifiedConfig config) {
		super();
		this.status = status;
		this.report = report;
		this.config = config;
	}

	public GuardingStatus getStatus() {
		return status;
	}

	public void setStatus(GuardingStatus status) {
		this.status = status;
	}

	public SimplifiedGuardReport getReport() {
		return report;
	}

	public void setReport(SimplifiedGuardReport report) {
		this.report = report;
	}

	public SimplifiedConfig getConfig() {
		return config;
	}

	public void setConfig(SimplifiedConfig config) {
		this.config = config;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((config == null) ? 0 : config.hashCode());
		result = prime * result + ((report == null) ? 0 : report.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Situation other = (Situation) obj;
		if (config == null) {
			if (other.config != null)
				return false;
		} else if (!config.equals(other.config))
			return false;
		if (report == null) {
			if (other.report != null)
				return false;
		} else if (!report.equals(other.report))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Situation [status=" + status + ", report=" + report + ", config=" + config + "]";
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Situation clone = new Situation();

		if (config != null) {
			clone.setConfig((SimplifiedConfig) config.clone());
		}
		if (report != null) {
			clone.setReport((SimplifiedGuardReport) report.clone());
		}

		clone.setStatus(status);

		return clone;
	}

	public Situation duplicate() {
		try {
			return (Situation) clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	@Override
	public String jaxonDescription() {
		return DESC;
	}

}
