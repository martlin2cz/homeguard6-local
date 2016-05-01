package cz.martlin.hg6.mrs.situation;

import java.io.Serializable;
import java.util.Calendar;

import cz.martlin.hg5.logic.data.GuardingReport;
import cz.martlin.jaxon.jaxon.JaxonSerializable;

public class SimplifiedGuardReport extends GuardingReport implements Serializable, JaxonSerializable, Cloneable {

	private static final long serialVersionUID = 1532919114288578607L;

	private static final String DESC = "Simplified version of guard report";

	private int itemsCount;
	private int warningsCount;
	private int criticalCount;

	private Calendar lastWarningAt;

	public SimplifiedGuardReport() {
	}

	public SimplifiedGuardReport(Calendar startedAt, Calendar stoppedAt, Calendar lastWarnAt, Integer warningsCount,
			Integer criticalCount, Integer itemsCount, String description) {

		setTo(startedAt, stoppedAt, lastWarnAt, warningsCount, criticalCount, itemsCount, description);
	}

	@Override
	public int getCriticalCount() {
		return criticalCount;
	}

	@Override
	public int getWarningsCount() {
		return warningsCount;
	}

	@Override
	public int getItemsCount() {
		return itemsCount;
	}

	@Override
	public Calendar getLastWarningAt() {
		return lastWarningAt;
	}

	public void setCriticalCount(int criticalCount) {
		this.criticalCount = criticalCount;
	}

	public void setWarningsCount(int warningsCount) {
		this.warningsCount = warningsCount;
	}

	public void setItemsCount(int itemsCount) {
		this.itemsCount = itemsCount;
	}

	public void setLastWarningAt(Calendar lastWarningAt) {
		this.lastWarningAt = lastWarningAt;
	}

	public void setTo(Calendar startedAt, Calendar stoppedAt, Calendar lastWarnAt, int warningsCount, int criticalCount,
			int itemsCount, String description) {

		this.setStartedAt(startedAt);
		this.setStoppedAt(stoppedAt);
		this.setLastWarningAt(lastWarnAt);
		this.setItemsCount(itemsCount);
		this.setWarningsCount(warningsCount);
		this.setCriticalCount(criticalCount);
		this.setDescription(description);
	}

	public void setTo(SimplifiedGuardReport report) {
		setTo(report.getStartedAt(), report.getStoppedAt(), report.getLastWarningAt(), report.getWarningsCount(),
				report.getCriticalCount(), report.getItemsCount(), report.getDescription());
	}

	public void setItemsTo(SimplifiedGuardReport report) {
		this.setItemsCount(report.getItemsCount());
		this.setWarningsCount(report.getWarningsCount());
		this.setCriticalCount(report.getCriticalCount());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + criticalCount;
		result = prime * result + itemsCount;
		result = prime * result + ((lastWarningAt == null) ? 0 : lastWarningAt.hashCode());
		result = prime * result + warningsCount;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimplifiedGuardReport other = (SimplifiedGuardReport) obj;
		if (criticalCount != other.criticalCount)
			return false;
		if (itemsCount != other.itemsCount)
			return false;
		if (lastWarningAt == null) {
			if (other.lastWarningAt != null)
				return false;
		} else if (!lastWarningAt.equals(other.lastWarningAt))
			return false;
		if (warningsCount != other.warningsCount)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SimplifiedGuardReport [itemsCount=" + itemsCount + ", warningsCount=" + warningsCount
				+ ", criticalCount=" + criticalCount + ", lastWarningAt="
				+ (lastWarningAt != null ? lastWarningAt.getTime() : "null") + ", startedAt="
				+ (getStartedAt() != null ? getStartedAt().getTime() : "null") + ", stoppedAt="
				+ (getStoppedAt() != null ? getStoppedAt().getTime() : "null") + "]";
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		SimplifiedGuardReport report = new SimplifiedGuardReport();

		report.setTo(this);

		return report;
	}

	public SimplifiedGuardReport cloneQuietly() {
		try {
			return (SimplifiedGuardReport) clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	@Override
	public String jaxonDescription() {
		return DESC;
	}

	public static SimplifiedGuardReport create(GuardingReport report) {
		return new SimplifiedGuardReport(report.getStartedAt(), report.getStoppedAt(), report.getLastWarningAt(),
				report.getWarningsCount(), report.getCriticalCount(), report.getItemsCount(), report.getDescription());
	}

}
