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
		boolean succ = man.saveMetadataOfReport(report);
		if (!succ) {
			throw new Hg6DbException("Cannot save metadata of report " + report);
		}
	}

	public void saveItemOfReport(GuardingReport report, ReportItem item) throws Hg6DbException {
		boolean succ = man.saveItemOfReport(report, item);
		if (!succ) {
			throw new Hg6DbException("Cannot save item " + item + " of report");
		}
	}

	public void saveSoundTrack(Calendar recordedAt, SoundTrack track) throws Hg6DbException {
		boolean succ = man.saveSoundTrack(recordedAt, track);
		if (!succ) {
			throw new Hg6DbException("Cannot save sound track " + track);
		}
	}

	public SoundTrack loadSoundTrack(Calendar recordedAt) throws Hg6DbException {
		SoundTrack st = man.loadSoundTrack(recordedAt);
		if (st != null) {
			return st;
		} else {
			throw new Hg6DbException("Cannot load sound track recorded at " + recordedAt.getTime());
		}
	}

	public byte[] loadRawSoundTrackWAV(Calendar recordedAt) throws Hg6DbException {
		byte[] raw = man.loadRawSoundTrackWAV(recordedAt);
		if (raw != null) {
			return raw;
		} else {
			throw new Hg6DbException("Cannot load raw wav of record recorded at " + recordedAt.getTime());
		}
	}

	public GuardingReport loadReport(Calendar startedAt) throws Hg6DbException {
		GuardingReport report = man.loadReport(startedAt);
		if (report != null) {
			return report;
		} else {
			throw new Hg6DbException("Cannot load report started at " + startedAt.getTime());
		}
	}

	public TreeSet<GuardingReport> loadAllReports() throws Hg6DbException {
		TreeSet<GuardingReport> reports = man.loadAllReports();
		if (reports != null) {
			return reports;
		} else {
			throw new Hg6DbException("Cannot load all reports");
		}
	}

	public TreeSet<GuardingReport> loadReportsAtDay(Calendar day) throws Hg6DbException {
		TreeSet<GuardingReport> reports = man.loadReportsAtDay(day);
		if (reports != null) {
			return reports;
		} else {
			throw new Hg6DbException("Cannot load reports at day " + day.getTime());
		}
	}

	public GuardingReport loadLastReport() throws Hg6DbException {
		GuardingReport report = man.loadLastReport();
		if (report != null) {
			return report;
		} else {
			throw new Hg6DbException("Cannot load last report");
		}
	}

	public GuardingReport loadNewestReport() throws Hg6DbException {
		return loadLastReport(); // TODO like this?
	}

}
