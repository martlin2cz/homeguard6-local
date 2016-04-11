package cz.martlin.hg5.logic.guard;

import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg5.logic.config.Configuration;
import cz.martlin.hg5.logic.data.GuardingReport;
import cz.martlin.hg5.logic.data.ReportItem;
import cz.martlin.hg5.logic.data.SoundTrack;
import cz.martlin.hg5.logic.process.AbstractAudioProcessor;
import cz.martlin.hg5.logic.process.AbstractReporter;
import cz.martlin.hg5.logic.process.AudioRecorder;
import cz.martlin.hg5.logic.process.Interruptable;
import cz.martlin.hg5.logic.process.TrackToSamplesConverter;
import cz.martlin.hg5.logic.processV1.ImprovedTrackToSamplesConv;
import cz.martlin.hg5.logic.processV1.ImprovedAudioProcessor;
import cz.martlin.hg5.logic.processV1.fsman.FileSystemReportsManager;

/**
 * Represents one instance of guarding process. When is runned (method
 * {@link #run()} is invoked), initializes itself and then starts to record,
 * process and report audio records until is interrupted (method
 * {@link #interrupt()}).
 * 
 * @author martin
 *
 */
public class GuardingInstance implements Serializable, Interruptable {
	private static final long serialVersionUID = -2738667045527708561L;
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final AudioRecorder recorder;
	private final TrackToSamplesConverter converter;
	private final AbstractAudioProcessor processor;
	private final List<AbstractReporter> reporters;

	private final Configuration config;
	private final GuardingReport report;

	private boolean interrputed;

	public GuardingInstance(Configuration config, List<AbstractReporter> reporters) {
		this.config = config;

		this.recorder = new AudioRecorder(config);
		this.converter = new ImprovedTrackToSamplesConv(config);
		this.processor = new ImprovedAudioProcessor(config);
		this.reporters = new LinkedList<>(reporters);
		
		this.report = new GuardingReport(config.getDefaultDescription());
	}

	/**
	 * Adds given reporter to set of to-use reporters.
	 * 
	 * @param reporter
	 */
	public void addReporter(AbstractReporter reporter) {
		reporters.add(reporter);
	}

	/**
	 * Returns report of this instance.
	 * 
	 * @return
	 */
	public GuardingReport getReport() {
		return report;
	}

	@Override
	public void interrupt() {
		interrputed = true;
	}

	/**
	 * Initializes and then runs recording loop.
	 */
	public void run() {
		beforeStart();

		while (true) {
			doOneSample();

			if (interrputed) {
				break;
			}

			waitToNext();
			if (interrputed) {
				break;
			}
		}

		afterEnd();
	}

	/**
	 * Initializes report. In fact only sets the start date, reports and logs.
	 */
	private void beforeStart() {
		Calendar start = Calendar.getInstance();
		report.setStarted(start);

		reporters.forEach((AbstractReporter reporter) -> //
		reporter.reportChanged(report));

		reporters.forEach((AbstractReporter reporter) -> //
		reporter.reportStart(report));

		LOG.info("Recording started at {} with configuration {}", start.getTime(), config);
	}

	/**
	 * Proceses one sample record step. Via {@link AudioRecorder} records
	 * sample, converts with {@link ImprovedTrackToSamplesConv}, processes it
	 * with {@link ImprovedAudioProcessor} and then logs it and reports via
	 * {@link FileSystemReportsManager} and {@link AbstractReporter} .
	 */
	public void doOneSample() {
		LOG.debug("Recording and processing sample");
		Calendar recordedAt = Calendar.getInstance();

		SoundTrack track = recorder.record(config.getSampleLenght());
		double[] samples = converter.toSamples(track);
		ReportItem item = processor.analyzeSample(recordedAt, track, samples);

		reporters.forEach((AbstractReporter reporter) -> //
		reporter.reportItem(report, item, track, samples));

		report.addReportItem(item);
	}

	/**
	 * Waits until next record (wait time is given by {@link Configuration}).
	 */
	private void waitToNext() {
		long wait = config.getSamplesInterval() - config.getSampleLenght();
		long ms = 1000 * wait;

		LOG.debug("Now waiting {} seconds until next record", wait);
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Finishes work. In fact only sets end time, reports and logs.
	 */
	private void afterEnd() {
		Calendar stop = Calendar.getInstance();
		report.setStopped(stop);

		reporters.forEach((AbstractReporter reporter) -> //
		reporter.reportChanged(report));

		reporters.forEach((AbstractReporter reporter) -> //
		reporter.reportEnd(report));

		LOG.info("Recording stopped at {}", stop.getTime());
	}

}
