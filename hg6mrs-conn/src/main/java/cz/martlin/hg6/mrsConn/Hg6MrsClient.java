package cz.martlin.hg6.mrsConn;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

public class Hg6MrsClient {

	public static final String UPDATE_STATUS_PATH = "/update-status";
	public static final String CURRENT_STATUS_PATH = "/current-status";
	public static final String STATUS_PARAM_NAME = "status";

	private final String url;

	public Hg6MrsClient(String url) {
		this.url = url;
	}

	public Hg6CommandToLocal sendStarted() {
		return sendUpdateStatus(Hg6LocalStatus.RUNNING);
	}

	public Hg6CommandToLocal sendStopped() {
		return sendUpdateStatus(Hg6LocalStatus.STOPPED);
	}

	public Hg6CommandToLocal sendStartFailed() {
		return sendUpdateStatus(Hg6LocalStatus.RUN_FAILED);
	}

	public Hg6CommandToLocal sendStopFailed() {
		return sendUpdateStatus(Hg6LocalStatus.STOP_FAILED);
	}

	public Hg6CommandToLocal sendClientDown() {
		return sendUpdateStatus(Hg6LocalStatus.LOCAL_CLIENT_DOWN);
	}

	private Hg6CommandToLocal sendUpdateStatus(Hg6LocalStatus status) {

		URL url = constructUpdateStatusURL(status);
		String response = connectAndReadResp(url);

		return Hg6CommandToLocal.valueOf(response); // TODO handle error
	}

	private String connectAndReadResp(URL url) {
		InputStream ins = null;
		try {
			URLConnection conn = url.openConnection();
			ins = conn.getInputStream();
			return IOUtils.toString(ins);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			IOUtils.closeQuietly(ins);
		}
	}

	private URL constructUpdateStatusURL(Hg6LocalStatus status) {
		String url = this.url + UPDATE_STATUS_PATH + "?" + STATUS_PARAM_NAME + "=" + status.name();

		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null; // TODO handle error
		}
	}
}
