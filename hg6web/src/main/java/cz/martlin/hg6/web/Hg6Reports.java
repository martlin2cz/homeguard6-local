package cz.martlin.hg6.web;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.chart.LineChartModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg5.logic.data.GuardingReport;
import cz.martlin.hg5.web.Hg6WebApp;
import cz.martlin.hg5.web.Utils;
import cz.martlin.hg5.web.charts.GuardingReportChart;
import cz.martlin.hg6.coreJRest.Hg6CoreConnException;
import cz.martlin.hg6.db.Hg6DbException;

@ManagedBean(name = "hg6reports")
@SessionScoped
public class Hg6Reports implements Serializable {
	static final long serialVersionUID = -7919476457127853811L;
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private static final String ERROR_SUMMARY = "chyba hg6reports";
	private static final GuardingReportChart REPORT_CHARTS = new GuardingReportChart();

	private final Hg6WebApp hg;

	private List<GuardingReport> reportsAtDay = null;
	private GuardingReport displayed = null;

	public Hg6Reports() {
		hg = new Hg6WebApp();
	}

	@PostConstruct
	public void init() {
		reportsAtDay = todaysReports();
	}

	private List<GuardingReport> todaysReports() {
		Calendar today = Calendar.getInstance();
		List<GuardingReport> reports;
		try {
			reports = hg.reportsAt(today);
		} catch (Hg6DbException e) {
			LOG.error("Loading of today's reports failed", e);
			Utils.error(ERROR_SUMMARY, "Nepodařilo se načíst záznamy");
			reports = Collections.emptyList();
		}
		return reports;
	}

	public GuardingReport getDisplayedReport() {
		return displayed;
	}

	public List<GuardingReport> getReportsAtDay() {
		return reportsAtDay;
	}

	public boolean isReportDisplayed() {
		return displayed != null;
	}

	public String showCurrentReport() {
		try {
			displayed = hg.currentReport();
		} catch (Hg6DbException | Hg6CoreConnException e) {
			LOG.error("Loading of current report failed", e);
			Utils.error(ERROR_SUMMARY, "Nepodařilo se načíst záznam");
		}
		return null;
	}

	public String showLastReport() {
		try {
			displayed = hg.lastReport();
		} catch (Hg6DbException e) {
			LOG.error("Loading of last report failed", e);
			Utils.error(ERROR_SUMMARY, "Nepodařilo se načíst záznam");
		}
		return null;
	}

	public String loadReportsAtDay(SelectEvent event) {
		Calendar day = Calendar.getInstance();
		day.setTime((Date) event.getObject());

		try {
			reportsAtDay = hg.reportsAt(day);
		} catch (Hg6DbException e) {
			LOG.error("Loading of reports at day " + day.getTime() + " failed", e);
			Utils.error(ERROR_SUMMARY, "Nepodařilo se načíst záznamy");
		}
		return null;
	}

	public String showReport(Calendar date) {
		try {
			displayed = hg.getReport(date);
		} catch (Hg6DbException e) {
			LOG.error("Loading of report " + date.getTime() + " failed", e);
			Utils.error(ERROR_SUMMARY, "Nepodařilo se načíst záznam");
		}
		return null;
	}

	public String hideReport() {
		this.displayed = null;
		return null;
	}

	public String saveDescription() {
		try {
			hg.saveReportsMetadata(displayed);
			Utils.info("Uloženo", "Změna uložena");

		} catch (Hg6DbException e) {
			LOG.error("Saving of changes failed", e);
			Utils.error(ERROR_SUMMARY, "Nepodařilo se uložit změny");
		}

		return null;
	}

	public LineChartModel getModelForReport(GuardingReport report) {
		return REPORT_CHARTS.getModel(report);
	}

}
