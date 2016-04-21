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

		trySetTo(startedAt, stoppedAt, lastWarnAt, warningsCount, criticalCount, itemsCount, description);
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

	public void trySetStarted(Calendar dateOrNull) {
		if (dateOrNull != null) {
			super.setStartedAt(dateOrNull);
		}
	}

	public void trySetStopped(Calendar dateOrNull) {
		if (dateOrNull != null) {
			super.setStoppedAt(dateOrNull);
		}
	}

	public void trySetDescription(String descriptionOrNull) {
		if (descriptionOrNull != null) {
			super.setDescription(descriptionOrNull);
		}
	}

	public void trySetCriticalCount(Integer criticalCountOrNull) {
		if (criticalCountOrNull != null) {
			this.criticalCount = criticalCountOrNull;
		}
	}

	public void trySetWarningsCount(Integer warningsCountOrNull) {
		if (warningsCountOrNull != null) {
			this.warningsCount = warningsCountOrNull;
		}

	}

	public void trySetItemsCount(Integer itemsCountOrNull) {
		if (itemsCountOrNull != null) {
			this.itemsCount = itemsCountOrNull;
		}
	}

	public void trySetLastWarnAt(Calendar lastWarnAtOrNull) {
		if (lastWarnAtOrNull != null) {
			this.lastWarningAt = lastWarnAtOrNull;
		}
	}

	public void trySetTo(Calendar startedAt, Calendar stoppedAt, Calendar lastWarnAt, Integer warningsCount,
			Integer criticalCount, Integer itemsCount, String description) {

		this.trySetStarted(startedAt);
		this.trySetStopped(stoppedAt);
		this.trySetLastWarnAt(lastWarnAt);
		this.trySetItemsCount(itemsCount);
		this.trySetWarningsCount(warningsCount);
		this.trySetCriticalCount(criticalCount);
		this.trySetDescription(description);
	}

	public void trySetTo(SimplifiedGuardReport report) {
		trySetTo(report.getStartedAt(), report.getStoppedAt(), report.getLastWarningAt(), report.getWarningsCount(),
				report.getCriticalCount(), report.getItemsCount(), report.getDescription());
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
				+ ", criticalCount=" + criticalCount + ", lastWarningAt=" + lastWarningAt + ", startedAt="
				+ (getStartedAt() != null ? getStartedAt().getTime() : "null") + ", stoppedAt="
				+ (getStoppedAt() != null ? getStoppedAt().getTime() : "null") + "]";
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		SimplifiedGuardReport report = new SimplifiedGuardReport();

		report.trySetTo(this);

		return report;
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
