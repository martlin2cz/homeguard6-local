package cz.martlin.hg6.db;

import java.util.Calendar;
import java.util.TreeSet;

import cz.martlin.hg5.logic.config.Configuration;
import cz.martlin.hg5.logic.data.GuardingReport;
import cz.martlin.hg5.logic.data.ReportItem;
import cz.martlin.hg5.logic.data.SoundTrack;
import cz.martlin.hg5.logic.processV1.fsman.FileSystemReportsManager;

public class Hg6Database {

	private final FileSystemReportsManager man;

	public Hg6Database(Configuration config) {
		this.man = new FileSystemReportsManager(config);
	}

	public void saveMetadataOfReport(GuardingReport report) throws Hg6DbException {
		man.saveMetadataOfReport(report);
	}

	public void saveItemOfReport(GuardingReport report, ReportItem item) throws Hg6DbException {
		man.saveItemOfReport(report, item);
	}

	public void saveSoundTrack(Calendar recordedAt, SoundTrack track) throws Hg6DbException {
		man.saveSoundTrack(recordedAt, track);
	}

	public SoundTrack loadSoundTrack(Calendar recordedAt) throws Hg6DbException {
		SoundTrack st = man.loadSoundTrack(recordedAt);
		return st;
	}

	public byte[] loadRawSoundTrackWAV(Calendar recordedAt) throws Hg6DbException {
		byte[] raw = man.loadRawSoundTrackWAV(recordedAt);
		return raw;
	}

	public GuardingReport loadReport(Calendar startedAt) throws Hg6DbException {
		GuardingReport report = man.loadReport(startedAt);
		return report;
	}

	public TreeSet<GuardingReport> loadAllReports() throws Hg6DbException {
		TreeSet<GuardingReport> reports = man.loadAllReports();
		return reports;
	}

	public TreeSet<GuardingReport> loadReportsAtDay(Calendar day) throws Hg6DbException {
		TreeSet<GuardingReport> reports = man.loadReportsAtDay(day);
		return reports;
	}

	public GuardingReport loadLastReport() throws Hg6DbException {
		GuardingReport report = man.loadLastReport();
		return report;
	}

	public GuardingReport loadNewestReport() throws Hg6DbException {
		return loadLastReport();
	}

}
