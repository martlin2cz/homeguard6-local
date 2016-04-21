package cz.martlin.hg6.mrs.situation;

import java.io.Serializable;

import cz.martlin.hg5.logic.config.Configuration;
import cz.martlin.jaxon.jaxon.JaxonSerializable;

public class SimplifiedConfig implements Serializable, JaxonSerializable, Cloneable {
	private static final long serialVersionUID = 4407399558674525100L;

	private static final String DESC = "Simplified Hg6 configuration";

	private Integer interval;

	public SimplifiedConfig() {
	}

	public SimplifiedConfig(Integer interval) {
		this.interval = interval;
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((interval == null) ? 0 : interval.hashCode());
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
		SimplifiedConfig other = (SimplifiedConfig) obj;
		if (interval == null) {
			if (other.interval != null)
				return false;
		} else if (!interval.equals(other.interval))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "SimplifiedConfig [interval=" + interval + "]";
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		SimplifiedConfig config = new SimplifiedConfig();

		config.setInterval(getInterval());

		return config;

	}

	@Override
	public String jaxonDescription() {
		return DESC;
	}

	public static SimplifiedConfig create(Configuration config) {
		SimplifiedConfig simpl = new SimplifiedConfig();
		
		simpl.setInterval(config.getMrsInterval());
		
		return simpl;
	}

}