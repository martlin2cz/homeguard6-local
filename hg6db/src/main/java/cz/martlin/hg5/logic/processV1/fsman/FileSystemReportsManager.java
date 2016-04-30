package cz.martlin.hg5.logic.processV1.fsman;

import java.io.File;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg5.logic.config.Configuration;
import cz.martlin.hg5.logic.data.GuardingReport;
import cz.martlin.hg5.logic.data.ReportItem;
import cz.martlin.hg5.logic.data.SoundTrack;
import cz.martlin.hg6.db.Hg6DbException;

/**
 * Implements Reports manager such that reports are stored in log and wav files
 * on the file system.
 * 
 * @author martin
 *
 */
public class FileSystemReportsManager implements Serializable {
	private static final long serialVersionUID = 1577018812775367330L;
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final FileSystemManTools tools;

	public FileSystemReportsManager(Configuration config) {
		this.tools = new FileSystemManTools(config);
	}

	/**
	 * Saves metadata (startedAt, endedAt and description) of given report.
	 * 
	 * @param report
	 * @throws Hg6DbException
	 */
	public void saveMetadataOfReport(GuardingReport report) throws Hg6DbException {
		LOG.info("Saving report's metadata: {}", report);
		try {
			File log = tools.logFile(report);
			String content = tools.serializeMetadata(report);
			tools.appendLine(log, content);
		} catch (Hg6DbException e) {
			throw new Hg6DbException("Cannot save Metadata of report", e);
		}
	}

	/**
	 * Saves (given) report item of given report.
	 * 
	 * @param report
	 * @param item
	 * @throws Hg6DbException
	 */
	public void saveItemOfReport(GuardingReport report, ReportItem item) throws Hg6DbException {
		LOG.info("Saving report's new item: {}", item);
		try {
			File log = tools.logFile(report);
			String content = tools.serializeReportItem(item);
			tools.appendLine(log, content);
		} catch (Hg6DbException e) {
			throw new Hg6DbException("Cannot save item of report", e);
		}
	}

	/**
	 * Saves given soundrack.
	 * 
	 * @param track
	 * @throws Hg6DbException
	 */
	public void saveSoundTrack(Calendar recordedAt, SoundTrack track) throws Hg6DbException {
		LOG.info("Saving soundtrack into WAV file: {}", track);
		try {
			File wav = tools.wavFile(recordedAt);
			tools.saveSoundTrack(wav, track);
		} catch (Hg6DbException e) {
			throw new Hg6DbException("Cannot save sound track", e);
		}
	}

	/**
	 * Loads soundtrack given by datetime of record.
	 * 
	 * @param recordedAt
	 * @return
	 * @throws Hg6DbException
	 */
	public SoundTrack loadSoundTrack(Calendar recordedAt) throws Hg6DbException {
		LOG.info("Loading record as WAV recorded at {}", recordedAt.getTime());
		try {
			File wav = tools.wavFile(recordedAt);
			return tools.loadSoundTrack(wav);
		} catch (Hg6DbException e) {
			throw new Hg6DbException("Cannot load sound track", e);
		}
	}

	/**
	 * Loads soundtrack as WAV.
	 * 
	 * @param recordedAt
	 * @return
	 * @throws Hg6DbException
	 */
	public byte[] loadRawSoundTrackWAV(Calendar recordedAt) throws Hg6DbException {
		try {
			File file = tools.wavFile(recordedAt);
			return tools.loadRawSoundTrackWavBytes(file);
		} catch (Hg6DbException e) {
			throw new Hg6DbException("Cannot load (raw, as bytes) sound track", e);
		}
	}

	/**
	 * Loads whole report by given start datetime.
	 * 
	 * @param startedAt
	 * @return
	 * @throws Hg6DbException
	 */
	public GuardingReport loadReport(Calendar startedAt) throws Hg6DbException {
		LOG.debug("Loading report started at: " + startedAt.getTime());
		try {
			
			File file = tools.logFile(startedAt);
			GuardingReport report = tools.parseLogFile(file);
			return report;
		} catch (Hg6DbException e) {
			throw new Hg6DbException("Cannot load report started at " + startedAt.getTime(), e);
		}
	}

	/**
	 * Loads all reports.
	 * 
	 * @return
	 * @throws Hg6DbException
	 */
	public TreeSet<GuardingReport> loadAllReports() throws Hg6DbException {
		LOG.debug("Loading all reports");
		try {
			Set<File> logs = tools.logsFiles(null);
			if (logs == null) {
				return null;
			}
			TreeSet<GuardingReport> reports = parseLogs(logs);

			return reports;
		} catch (Hg6DbException e) {
			throw new Hg6DbException("Cannot load all reports", e);
		}
	}

	/**
	 * Loads reports started at given day.
	 * 
	 * @param day
	 * @return
	 * @throws Hg6DbException
	 */
	public TreeSet<GuardingReport> loadReportsAtDay(Calendar day) throws Hg6DbException {
		LOG.debug("Loading reports at day: {}", day.getTime());
		try {

			Set<File> logs = tools.logsFiles(day);
			if (logs == null) {
				return null;
			}

			TreeSet<GuardingReport> reports = parseLogs(logs);

			return reports;
		} catch (Hg6DbException e) {
			throw new Hg6DbException("Cannot load reports at " + day.getTime(), e);
		}
	}

	/**
	 * For given set of logs files parses logs for each of them.
	 * 
	 * @param logs
	 * @return
	 * @throws Hg6DbException
	 */
	private TreeSet<GuardingReport> parseLogs(Set<File> logs) throws Hg6DbException {
		TreeSet<GuardingReport> reports = new TreeSet<>();

		try {
			logs.forEach((File logFile) -> {
				try {
					reports.add(tools.parseLogFile(logFile));
				} catch (Hg6DbException e) {
					throw new RuntimeException(e);
				}
			});
		} catch (RuntimeException e) {
			throw (Hg6DbException) e.getCause();
		}

		return reports;
	}

	/**
	 * Loads last recorded report. In fact returns report of the last recording
	 * started.
	 * 
	 * @return
	 * @throws Hg6DbException
	 */
	public GuardingReport loadLastReport() throws Hg6DbException {
		LOG.debug("Loading last report");
		try {
			TreeSet<GuardingReport> reports = loadAllReports();

			if (reports == null) {
				return null;
			}

			return reports.last();
		} catch (Hg6DbException e) {
			throw new Hg6DbException("Cannot load last report", e);
		}
		/// TODO FIXME nešlo by to vylepšit?
	}

}
