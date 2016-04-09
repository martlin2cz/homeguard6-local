package cz.martlin.hg5.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.chart.LineChartModel;

import cz.martlin.hg5.logic.data.GuardingReport;
import cz.martlin.hg5.web.charts.GuardingReportChart;

@ViewScoped
@ManagedBean(name = "guardingReportPanel")
public class GuardingReportPanel implements Serializable {
	private static final long serialVersionUID = 1329899742372452384L;
	private static final GuardingReportChart REPORT_CHARTS = new GuardingReportChart();

	private final _Homeguard homeguard = new _Homeguard();
	
	private List<GuardingReport> reportsAtDay = null;
	private GuardingReport report;

	public GuardingReportPanel() {
	}

	@PostConstruct
	public void init() {
		Calendar today = Calendar.getInstance();
		Set<GuardingReport> reports = homeguard.reportsAt(today);
		reportsAtDay = new ArrayList<>(reports);
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
		GuardingReport current = homeguard.currentReport();
		this.report = current;
	}

	public void showLastReport() {
		GuardingReport last = homeguard.lastReport();
		this.report = last;
	}

	public void loadReportsAtDay(SelectEvent event) {
		Calendar day = Calendar.getInstance();
		day.setTime((Date) event.getObject());

		Set<GuardingReport> reports = homeguard.reportsAt(day);
		this.reportsAtDay = new ArrayList<>(reports);
	}

	public void hideReport() {
		this.report = null;
	}

	public void showReport(Calendar date) {
		GuardingReport report = homeguard.getReport(date);
		this.report = report;
	}

	public void saveDescription() {
		boolean success = homeguard.saveReportsMetadata(report);
		if (success) {
			Utils.info("Uloženo", "Změna uložena");
		} else {
			Utils.info("Chyba", "Nepodařilo se uložit změny");
		}
	}

	public LineChartModel getModelForReport(GuardingReport report) {
		return REPORT_CHARTS.getModel(report);
	}

}
