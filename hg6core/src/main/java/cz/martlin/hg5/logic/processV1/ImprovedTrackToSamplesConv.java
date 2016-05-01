package cz.martlin.hg5.logic.processV1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.sound.sampled.AudioFormat.Encoding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg5.logic.data.SoundTrack;
import cz.martlin.hg5.logic.process.TrackToSamplesConverter;
import cz.martlin.hg6.config.Configuration;

public class ImprovedTrackToSamplesConv implements TrackToSamplesConverter {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final Configuration config;

	public ImprovedTrackToSamplesConv(Configuration config) {
		this.config = config;
	}

	@Override
	public double[] toSamples(SoundTrack track) {
		return samplesOfTrack(track);
	}

	/**
	 * Converts bytes of track into array of concrete samples (doubles from 0.0
	 * to 1.0). Requires 8bits per sample track format.
	 * 
	 * @param track
	 * @return
	 */
	private double[] samplesOfTrack(SoundTrack track) {
		if (track.getFormat().getSampleSizeInBits() != 8) {
			LOG.warn("Trying to process track with sample size different than 8bits per sample.");
		}

		byte[] raw = track.getBytes();
		if (track.getFormat().getEncoding() == Encoding.PCM_SIGNED) {
			// okay, cool
		} else if (track.getFormat().getEncoding() == Encoding.PCM_UNSIGNED) {
			raw = toSigned(raw);
		} else {
			LOG.warn("Trying to process track with unsupported format:" + track.getFormat().getEncoding());
		}

		Queue<Byte> bytes = toQueueOfBytes(raw);

		List<Double> doubles = doConversion(bytes);

		return toArrayOfDoubles(doubles);
	}

	/**
	 * Does the conversion.
	 * 
	 * @param bytes
	 * @return
	 */
	private List<Double> doConversion(Queue<Byte> bytes) {
		List<Double> doubles = new ArrayList<>(bytes.size() / config.getSamplesGroup());

		while (!bytes.isEmpty()) {
			double sum = 0;
			int count = 0;

			for (int j = 0; j < config.getSamplesGroup(); j++) {
				if (bytes.isEmpty()) {
					break;
				}

				byte b = bytes.poll();
				int unsigned = Math.abs(b);
				double relative = ((double) unsigned / Byte.MAX_VALUE);

				sum += relative;
				count++;
			}

			doubles.add(sum / count);
		}
		return doubles;
	}

	protected byte[] toSigned(byte[] unsigneds) {
		byte[] signeds = new byte[unsigneds.length];
		byte HIGHEST_BIT = (byte) 0x80;

		for (int i = 0; i < unsigneds.length; i++) {
			byte ub = unsigneds[i];

			int si = HIGHEST_BIT ^ ub;
			byte sb = (byte) si;
			signeds[i] = sb;
		}

		return signeds;
	}

	/**
	 * Converts given bytes array to queue.
	 * 
	 * @param bytes
	 * @return
	 */
	private Queue<Byte> toQueueOfBytes(byte[] bytes) {
		Queue<Byte> queue = new LinkedList<Byte>();

		for (int i = 0; i < bytes.length; i++) {
			queue.add(bytes[i]);
		}

		return queue;
	}

	/**
	 * Converts given list to array of doubles.
	 * 
	 * @param list
	 * @return
	 */
	private double[] toArrayOfDoubles(List<Double> list) {
		double[] array = new double[list.size()];

		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}

		return array;
	}

}
