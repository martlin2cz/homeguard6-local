package cz.martlin.hg6.mrsConn;

import java.util.Calendar;

import cz.martlin.hg5.logic.data.GuardingReport;

public class RemoteGuardReport extends GuardingReport {

	private static final long serialVersionUID = 1532919114288578607L;

	private int itemsCount;
	private int warningsCount;
	private int criticalCount;

	private Calendar lastWarnAt;

	public RemoteGuardReport() {
	}

	public RemoteGuardReport(Calendar startedAt, Calendar stoppedAt, Calendar lastWarnAt, Integer warningsCount,
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
		return lastWarnAt;
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

	public void trySetStarted(Calendar dateOrNull) {
		if (dateOrNull != null) {
			super.setStarted(dateOrNull);
		}
	}

	public void trySetStopped(Calendar dateOrNull) {
		if (dateOrNull != null) {
			super.setStopped(dateOrNull);
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
			this.lastWarnAt = lastWarnAtOrNull;
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

	public void trySetTo(RemoteGuardReport report) {
		trySetTo(report.getStartedAt(), report.getStoppedAt(), report.getLastWarningAt(), report.getItemsCount(),
				report.getWarningsCount(), report.getCriticalCount(), report.getDescription());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + criticalCount;
		result = prime * result + itemsCount;
		result = prime * result + ((lastWarnAt == null) ? 0 : lastWarnAt.hashCode());
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
		RemoteGuardReport other = (RemoteGuardReport) obj;
		if (criticalCount != other.criticalCount)
			return false;
		if (itemsCount != other.itemsCount)
			return false;
		if (lastWarnAt == null) {
			if (other.lastWarnAt != null)
				return false;
		} else if (!lastWarnAt.equals(other.lastWarnAt))
			return false;
		if (warningsCount != other.warningsCount)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RemoteGuardReport [itemsCount=" + itemsCount + ", warningsCount=" + warningsCount + ", criticalCount="
				+ criticalCount + ", lastWarnAt=" + lastWarnAt + "]";
	}

}
