package cz.martlin.hg5.logic.processV1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg5.logic.data.GuardingReport;
import cz.martlin.hg5.logic.data.ReportItem;
import cz.martlin.hg5.logic.data.SoundTrack;
import cz.martlin.hg5.logic.process.AbstractReporter;
import cz.martlin.hg5.logic.processV1.fsman.FileSystemReportsManager;
import cz.martlin.hg6.config.Configuration;
import cz.martlin.hg6.db.Hg6Database;
import cz.martlin.hg6.db.Hg6DbException;

/**
 * Implements records reporter via {@link FileSystemReportsManager}. Each entry
 * logs into log file and warning entries additionally saves into WAV files.
 * 
 * @author martin
 *
 */
public class FileSystemReporter implements AbstractReporter {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final Hg6Database db;

	public FileSystemReporter(Configuration config) {
		this.db = new Hg6Database(config);
	}

	@Override
	public void reportStart(GuardingReport report) {
		saveMetadataOfReport(report);
	}

	@Override
	public void reportEnd(GuardingReport report) {
		saveMetadataOfReport(report);
	}

	@Override
	public void reportItem(GuardingReport report, ReportItem item, SoundTrack track, double[] samples) {
		try {
			db.saveItemOfReport(report, item);
		} catch (Hg6DbException e) {
			LOG.error("Cannot save item of report", e);
		}

		if (item.isWarning()) {
			try {
				db.saveSoundTrack(item.getRecordedAt(), track);
			} catch (Hg6DbException e) {
				LOG.error("Cannot save sound track", e);
			}
		}
	}

	@Override
	public void reportChanged(GuardingReport report) {
		saveMetadataOfReport(report);
	}

	private void saveMetadataOfReport(GuardingReport report) {
		try {
			db.saveMetadataOfReport(report);
		} catch (Hg6DbException e) {
			LOG.error("Cannot save mmetadata of report", e);
		}
	}

}
