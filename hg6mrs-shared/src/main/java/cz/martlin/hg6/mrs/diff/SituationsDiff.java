package cz.martlin.hg6.mrs.diff;

import java.io.Serializable;
import java.util.EnumSet;

public class SituationsDiff implements Serializable {
	private static final long serialVersionUID = -8609764042945763556L;

	private EnumSet<SituationDifference> diffs;

	public SituationsDiff() {
		diffs = EnumSet.of(SituationDifference.CHANGED_STATUS);
		diffs.remove(SituationDifference.CHANGED_STATUS); // TODO hehe ... hack
	}

	public void add(SituationDifference diff) {
		diffs.add(diff);
	}

	public boolean has(SituationDifference diff) {
		return diffs.contains(diff);
	}

	public boolean isHasNoChanges() {
		return diffs.isEmpty();
	}

	public Iterable<SituationDifference> getDifferences() {
		return diffs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((diffs == null) ? 0 : diffs.hashCode());
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
		SituationsDiff other = (SituationsDiff) obj;
		if (diffs == null) {
			if (other.diffs != null)
				return false;
		} else if (!diffs.equals(other.diffs))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SituationsDiff [diffs=" + diffs + "]";
	}

}
