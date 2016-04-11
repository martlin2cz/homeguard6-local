package cz.martlin.hg5.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.chart.LineChartModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg5.logic.data.GuardingReport;
import cz.martlin.hg5.web.charts.GuardingReportChart;
import cz.martlin.hg6.coreJRest.Hg6CoreConnException;
import cz.martlin.hg6.db.Hg6DbException;

@ViewScoped
@ManagedBean(name = "guardingReportPanel")
public class GuardingReportPanel implements Serializable {
	private static final long serialVersionUID = 1329899742372452384L;

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	private static final GuardingReportChart REPORT_CHARTS = new GuardingReportChart();

	private final Hg6WebApp homeguard = new Hg6WebApp();

	private List<GuardingReport> reportsAtDay = null;
	private GuardingReport report;

	public GuardingReportPanel() {
	}

	@PostConstruct
	public void init() {
		Set<GuardingReport> reports = todaysReports();
		reportsAtDay = new ArrayList<>(reports);
	}

	private Set<GuardingReport> todaysReports() {
		Calendar today = Calendar.getInstance();
		Set<GuardingReport> reports;
		try {
			reports = homeguard.reportsAt(today);
		} catch (Hg6DbException e) {
			LOG.error("todaysReports failed", e);
			Utils.error("Chyba", "Nepodařilo se načíst záznamy");
			reports = Collections.emptySet();
		}
		return reports;
	}

	public List<GuardingReport> getReportsAtDay() {
		return reportsAtDay;
	}

	public GuardingReport getReport() {
		return report;
	}

	public boolean isReportShown() {
		return report != null;
	}

	public void showCurrentReport() {
		GuardingReport current;
		try {
			current = homeguard.currentReport();
		} catch (Hg6DbException | Hg6CoreConnException e) {
			LOG.error("showCurrentReport failed", e);
			Utils.error("Chyba", "Nepodařilo se načíst aktuální záznam");
			current = null;
		}
		this.report = current;
	}

	public void showLastReport() {
		GuardingReport last;
		try {
			last = homeguard.lastReport();
		} catch (Hg6DbException e) {
			LOG.error("showLasReport failed", e);
			Utils.error("Chyba", "Nepodařilo se načíst záznam");
			last = null;
		}
		this.report = last;
	}

	public void loadReportsAtDay(SelectEvent event) {
		Calendar day = Calendar.getInstance();
		day.setTime((Date) event.getObject());

		Set<GuardingReport> reports;
		try {
			reports = homeguard.reportsAt(day);
		} catch (Hg6DbException e) {
			LOG.error("loadReportsAtDay failed", e);
			Utils.error("Chyba", "Nepodařilo se načíst záznamy");
			reports = null;
		}
		this.reportsAtDay = new ArrayList<>(reports);
	}

	public void hideReport() {
		this.report = null;
	}

	public void showReport(Calendar date) {
		GuardingReport report;
		try {
			report = homeguard.getReport(date);
		} catch (Hg6DbException e) {
			LOG.error("showReport failed", e);
			Utils.error("Chyba", "Nepodařilo se načíst záznam");
			report = null;
		}
		this.report = report;
	}

	public void saveDescription() {
		try {
			homeguard.saveReportsMetadata(report);
			Utils.info("Uloženo", "Změna uložena");

		} catch (Hg6DbException e) {
			LOG.error("saveDescription failed", e);
			Utils.error("Chyba", "Nepodařilo se uložit změny");
		}
	}

	public LineChartModel getModelForReport(GuardingReport report) {
		return REPORT_CHARTS.getModel(report);
	}

}
