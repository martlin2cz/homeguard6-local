package cz.martlin.hg5.web;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.sound.sampled.AudioFormat;

import cz.martlin.hg5.logic.config.Configuration;
import cz.martlin.hg5.logic.data.GuardingReport;
import cz.martlin.hg5.logic.data.SoundTrack;

public class _Homeguard {

	public _Homeguard() {
		// TODO Auto-generated constructor stub
	}

	public static _Homeguard get() {
		return new _Homeguard(); // TODO ...
	}

	public void setConfigTo(Configuration config) {
		// TODO Auto-generated method stub
		
	}

	public boolean saveConfig() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean loadConfig() {
		// TODO Auto-generated method stub
		return false;
	}

	public Configuration getConfig() {
		// TODO Auto-generated method stub
		return new Configuration();
	}

	public Set<GuardingReport> reportsAt(Calendar today) {
		// TODO Auto-generated method stub
		return new HashSet<>();
	}

	public GuardingReport currentReport() {
		// TODO Auto-generated method stub
		return new GuardingReport("yea, current");
	}

	public GuardingReport lastReport() {
		// TODO Auto-generated method stub
		return new GuardingReport("yea, last report");
	}

	public boolean saveReportsMetadata(GuardingReport report) {
		// TODO Auto-generated method stub
		return false;
	}

	public GuardingReport getReport(Calendar date) {
		// TODO Auto-generated method stub
		return new GuardingReport("faaaou");
	}

	public static _Homeguard tryToLoadConfigAndCreate() {
		return new _Homeguard();	//TODO 
	}

	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	public void start() {
		// TODO Auto-generated method stub
		
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}

	public double[] getJustSimplySamplesOfTrack(SoundTrack track) {
		// TODO Auto-generated method stub
		return new double[10];
	}

	public byte[] loadRawWavBytesOfTrack(Calendar recordedAt) {
		// TODO Auto-generated method stub
		return new byte[10];
	}

	public SoundTrack getTrack(Calendar recordedAt) {
		// TODO Auto-generated method stub
		return new SoundTrack(new byte[10], new AudioFormat((float) 10.0,10,10,true,true));
	}

}
