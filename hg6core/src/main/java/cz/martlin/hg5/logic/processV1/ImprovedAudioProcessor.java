package cz.martlin.hg5.logic.processV1;

import java.io.Serializable;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg5.logic.config.Configuration;
import cz.martlin.hg5.logic.data.ReportItem;
import cz.martlin.hg5.logic.data.SoundTrack;
import cz.martlin.hg5.logic.process.AbstractAudioProcessor;

/**
 * Implements Audio processor by counting "warning" an "error" samples.
 * 
 * @author martin
 *
 */
public class ImprovedAudioProcessor implements Serializable, AbstractAudioProcessor {
	private static final long serialVersionUID = -1156332853195061753L;
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final Configuration config;

	public ImprovedAudioProcessor(Configuration config) {
		this.config = config;
	}

	@Override
	public ReportItem analyzeSample(Calendar recordedAt, SoundTrack track, double[] samples) {
		LOG.info("Analyzing sound track {} recorded at {}", track, recordedAt.getTime());

		int warnings = countWithLeastThan(samples, config.getWarningNoiseThreshold());
		int criticals = countWithLeastThan(samples, config.getCriticalNoiseThreshold());

		ReportItem item = ReportItem.create(recordedAt, config.getSampleLenght(), samples.length, config, warnings,
				criticals);
		LOG.info("Sample record analized with result {}", item);

		return item;
	}

	/**
	 * Counts number of samples, which's levels are higher than threshold.
	 * 
	 * @param sample
	 * @param threshold
	 * @return
	 */
	private int countWithLeastThan(double samples[], double threshold) {
		int count = 0;

		for (double value : samples) {
			if (value >= threshold) {
				count++;
			}
		}

		return count;
	}
}
