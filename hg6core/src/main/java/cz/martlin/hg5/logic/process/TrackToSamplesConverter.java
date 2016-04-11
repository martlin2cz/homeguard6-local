package cz.martlin.hg5.logic.process;

import cz.martlin.hg5.logic.data.SoundTrack;

public interface TrackToSamplesConverter {
	public double[] toSamples(SoundTrack track);
}
