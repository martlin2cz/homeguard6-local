package cz.martlin.hg6.webapp;

import java.util.Calendar;
import java.util.Set;

import cz.martlin.hg5.logic.data.GuardingReport;
import cz.martlin.hg5.logic.data.SoundTrack;

public class Hg6DatabaseWrapper {

	public Set<GuardingReport> reportsAt(Calendar day) {
		// TODO Auto-generated method stub
		return null;
	}

	public GuardingReport currentReport() {
		// TODO Auto-generated method stub
		return null;
	}

	public GuardingReport lastReport() {
		// TODO Auto-generated method stub
		return null;
	}

	public GuardingReport getReport(Calendar date) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean saveReportsMetadata(GuardingReport report) {
		// TODO Auto-generated method stub
		return false;
	}

	public double[] getJustSimplySamplesOfTrack(SoundTrack track) {
		// TODO Auto-generated method stub
		return null;
	}

	public byte[] loadRawWawBytesOfTrack(Calendar recordedAt) {
		// TODO Auto-generated method stub
		return null;
	}

	public SoundTrack getTrack(Calendar recordedAt) {
		// TODO Auto-generated method stub
		return null;
	}

}
