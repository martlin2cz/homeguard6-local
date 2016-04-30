package cz.martlin.hg5.logic.reporting;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import cz.martlin.hg5.logic.data.GuardingReport;

/**
 * Simple class for creating reports of reports.
 * @author martin
 *
 */
public class ReportsReporter {
	private static final DateFormat dateFormat = new SimpleDateFormat("d.MMM yyyy, H:mm:ss");

	/**
	 * Creates complete report.
	 * @param report
	 * @return
	 */
	public static String getCzechSummary(GuardingReport report) {
		StringBuilder stb = new StringBuilder();

		stb.append(getDatesSummary(report));
		stb.append(" ");
		stb.append(getItemsSummary(report));
		stb.append(" ");
		stb.append(getOtherMetasSummary(report));

		return stb.toString();
	}

	/**
	 * Creates report with startedAt and stoppedAt dates.
	 * @param report
	 * @return
	 */
	public static String getDatesSummary(GuardingReport report) {
		StringBuilder stb = new StringBuilder();

		stb.append("Zaznamenávání bylo spuštěno ");
		if (report.getStartedAt() != null) {
			stb.append(dateFormat.format(report.getStartedAt().getTime()));
		} else {
			stb.append("neznámo kdy");
		}

		stb.append(" a ");
		if (report.getStoppedAt() != null) {
			stb.append("zastaveno ");
			stb.append(dateFormat.format(report.getStoppedAt().getTime()));
		} else {
			stb.append("ještě beží");
		}

		stb.append(".");

		return stb.toString();
	}

	/**
	 * Creates report with report items counts and percentages.
	 * @param report
	 * @return
	 */
	public static String getItemsSummary(GuardingReport report) {
		StringBuilder stb = new StringBuilder();

		stb.append("Zaznamenáno bylo ");
		stb.append(String.format("%d", report.getItemsCount()));

		stb.append(" záznamů, z nichž je ");
		stb.append(String.format("%d (%d%%)", //
				report.getWarningsCount(), //
				(int) (report.getWarningsRatio() * 100)));

		stb.append(" varovných a ");
		stb.append(String.format("%d (%d%%)", //
				report.getCriticalCount(), //
				(int) (report.getCriticalsRatio() * 100)));

		stb.append(" dokonce kritických.");

		return stb.toString();
	}

	/**
	 * Creates report with the others metadatas (last warning and description).
	 * @param report
	 * @return
	 */
	public static String getOtherMetasSummary(GuardingReport report) {
		StringBuilder stb = new StringBuilder();

		if (report.getLastWarningAt() != null) {
			stb.append("Poslední varovný (nebo kritický) záznam nastal v ");
			stb.append(dateFormat.format(report.getLastWarningAt().getTime()));
			stb.append(".");
		} else {
			stb.append("Varovný záznam tedy není žádný.");
		}

		stb.append(" ");

		if (report.getDescription() != null) {
			stb.append("Popis: ");
			stb.append(report.getDescription());
		} else {
			stb.append("Popis nebyl uveden.");
		}

		return stb.toString();

	}
}
