package cz.martlin.hg6.mrsLib.sync;

import cz.martlin.hg6.mrs.situation.Situation;

public class SyncResult {

	private final Situation remote;
	private final Situation local;

	public SyncResult(Situation remote, Situation local) {
		super();
		this.remote = remote;
		this.local = local;
	}

	public Situation getRemote() {
		return remote;
	}

	public Situation getLocal() {
		return local;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((local == null) ? 0 : local.hashCode());
		result = prime * result + ((remote == null) ? 0 : remote.hashCode());
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
		SyncResult other = (SyncResult) obj;
		if (local != other.local)
			return false;
		if (remote == null) {
			if (other.remote != null)
				return false;
		} else if (!remote.equals(other.remote))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SyncResult [remote=" + remote + ", local=" + local + "]";
	}

}
