package cz.martlin.hg6.mrs.situation;

import java.io.Serializable;

import cz.martlin.jaxon.jaxon.JaxonSerializable;

public class Status implements Serializable, JaxonSerializable, Cloneable {

	private static final long serialVersionUID = 8712784030742488493L;

	private Boolean coreRunning;
	private Boolean mrsConnRunning;

	public Status() {
	}

	public Boolean getCoreRunning() {
		return coreRunning;
	}

	public void setCoreRunning(Boolean coreRunning) {
		this.coreRunning = coreRunning;
	}

	public Boolean getMrsConnRunning() {
		return mrsConnRunning;
	}

	public void setMrsConnRunning(Boolean mrsConnRunning) {
		this.mrsConnRunning = mrsConnRunning;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coreRunning == null) ? 0 : coreRunning.hashCode());
		result = prime * result + ((mrsConnRunning == null) ? 0 : mrsConnRunning.hashCode());
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
		Status other = (Status) obj;
		if (coreRunning == null) {
			if (other.coreRunning != null)
				return false;
		} else if (!coreRunning.equals(other.coreRunning))
			return false;
		if (mrsConnRunning == null) {
			if (other.mrsConnRunning != null)
				return false;
		} else if (!mrsConnRunning.equals(other.mrsConnRunning))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Status [coreRunning=" + coreRunning + ", mrsConnRunning=" + mrsConnRunning + "]";
	}

	@Override
	public String jaxonDescription() {
		return "An object representing status of local situation of hg6";
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Status clone = new Status();

		clone.setCoreRunning(coreRunning);
		clone.setMrsConnRunning(mrsConnRunning);

		return clone;
	}

}
