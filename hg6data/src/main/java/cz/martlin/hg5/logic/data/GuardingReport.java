package cz.martlin.hg5.logic.data;

import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import cz.martlin.hg5.logic.reporting.ReportsReporter;

/**
 * Represents report of guarding instance. Contains list of audio samples
 * reports items and some other metadata (start and end datetime and some
 * description).
 * 
 * @author martin
 *
 */
public class GuardingReport implements Serializable, Comparable<GuardingReport> {
	private static final long serialVersionUID = -1282172162201310754L;

	private String description;
	private Calendar startedAt;
	private Calendar stoppedAt;
	private final List<ReportItem> items = new LinkedList<>();

	public GuardingReport() {
	}

	public GuardingReport(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Calendar getStartedAt() {
		return startedAt;
	}

	public Calendar getStoppedAt() {
		return stoppedAt;
	}

	public void setStartedAt(Calendar date) {
		this.startedAt = date;
	}

	public void setStoppedAt(Calendar date) {
		this.stoppedAt = date;
	}

	public List<ReportItem> getItems() {
		return items;
	}

	public boolean isHasSomeItems() {
		return !items.isEmpty();
	}

	public int getItemsCount() {
		return items.size();
	}

	public void addReportItem(ReportItem item) {
		items.add(item);
	}

	public int getWarningsCount() {
		int count = 0;

		for (ReportItem item : items) {
			if (item.isWarning()) {
				count++;
			}
		}

		return count;
	}

	public int getCriticalCount() {
		int count = 0;

		for (ReportItem item : items) {
			if (item.isCritical()) {
				count++;
			}
		}

		return count;
	}

	public double getWarningsRatio() {
		return (double) getWarningsCount() / getItemsCount();
	}

	public double getCriticalsRatio() {
		return (double) getCriticalCount() / getItemsCount();
	}

	public ReportItem getLastWarning() {
		ListIterator<ReportItem> iter = items.listIterator();

		while (iter.hasPrevious()) {
			ReportItem item = iter.previous();
			if (item.isWarning()) {
				return item;
			}
		}

		return null;
	}

	public Calendar getLastWarningAt() {
		ReportItem lastWarning = getLastWarning();

		if (lastWarning != null) {
			return lastWarning.getRecordedAt();
		} else {
			return null;
		}
	}

	public String getDatesSummary() {
		return ReportsReporter.getDatesSummary(this);
	}

	public String getItemsSummary() {
		return ReportsReporter.getItemsSummary(this);
	}

	public String getOthersMetasSummary() {
		return ReportsReporter.getOtherMetasSummary(this);
	}

	public String getCzechSummary() {
		return ReportsReporter.getCzechSummary(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((startedAt == null) ? 0 : startedAt.hashCode());
		result = prime * result + ((stoppedAt == null) ? 0 : stoppedAt.hashCode());
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
		GuardingReport other = (GuardingReport) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (startedAt == null) {
			if (other.startedAt != null)
				return false;
		} else if (!startedAt.equals(other.startedAt))
			return false;
		if (stoppedAt == null) {
			if (other.stoppedAt != null)
				return false;
		} else if (!stoppedAt.equals(other.stoppedAt))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GuardingReport [startedAt=" + (startedAt == null ? null : startedAt.getTime()) + ", stoppedAt="
				+ (stoppedAt == null ? null : stoppedAt.getTime()) + ", description=" + description + ", items=" + items
				+ "]";
	}

	@Override
	public int compareTo(GuardingReport o) {
		if (this.startedAt != null && o.startedAt != null) {
			int cmp = this.startedAt.compareTo(o.startedAt);
			if (cmp != 0) {
				return cmp;
			}
		}

		if (this.stoppedAt != null && o.stoppedAt != null) {
			int cmp = this.stoppedAt.compareTo(o.stoppedAt);
			if (cmp != 0) {
				return cmp;
			}
		}

		return Integer.compare(this.items.size(), o.items.size());
	}

}
