package cz.martlin.hg6.coreJRest;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import cz.martlin.jrest.guest.JRestGuest;
import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.reqresp.JRestRequest;
import cz.martlin.jrest.protocol.reqresp.JRestResponse;
import cz.martlin.jrest.protocol.reqresp.ResponseStatus;

public class Hg6CoreClient {
	//private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final JRestGuest guest;

	public Hg6CoreClient() {
		Protocol protocol = new Protocol();
		guest = protocol.getGuest();
	}

	public Hg6CoreClient(String host) {
		Protocol protocol = new Protocol();
		guest = protocol.getGuest(host);
	}

	public void start() throws Hg6CoreConnException {
		JRestRequest request = new JRestRequest(Protocol.START_COMMAND);
		try {
			JRestResponse response = guest.sendRequest(request);

			boolean isOK = response.is(ResponseStatus.OK);
			if (!isOK) {
				throw new JRestException("Got no OK response: " + response);
			}
		} catch (JRestException e) {
			throw new Hg6CoreConnException("Cannot send start command", e);
		}
	}

	public void stop() throws Hg6CoreConnException {
		JRestRequest request = new JRestRequest(Protocol.STOP_COMMAND);
		try {
			JRestResponse response = guest.sendRequest(request);

			boolean isOK = response.is(ResponseStatus.OK);
			if (!isOK) {
				throw new JRestException("Got no OK response: " + response);
			}
		} catch (JRestException e) {
			throw new Hg6CoreConnException("Cannot send stop command", e);
		}
	}

	public Boolean isRunning() throws Hg6CoreConnException {
		JRestRequest request = new JRestRequest(Protocol.IS_RUNNING_COMMAND);
		try {
			JRestResponse response = guest.sendRequest(request);

			boolean isOK = response.is(ResponseStatus.OK);
			if (!isOK) {
				throw new JRestException("Got no OK response: " + response);
			}
			return Boolean.parseBoolean(response.getData());
		} catch (JRestException e) {
			throw new Hg6CoreConnException("Cannot send isRunning command", e);
		}
	}

	public String simpleInfo() throws Hg6CoreConnException {
		JRestRequest request = new JRestRequest(Protocol.SIMPLE_INFO_COMMAND);
		try {
			JRestResponse response = guest.sendRequest(request);

			boolean isOK = response.is(ResponseStatus.OK);
			if (!isOK) {
				throw new JRestException("Got no OK response: " + response);
			}
			return response.getData();
		} catch (JRestException e) {
			throw new Hg6CoreConnException("Cannot send simpleInfo command", e);
		}
	}

	public Calendar getCurrentStartedAt() throws Hg6CoreConnException {
		JRestRequest request = new JRestRequest(Protocol.CURRENT_STARTED_COMMAND);
		try {
			JRestResponse response = guest.sendRequest(request);

			boolean isOK = response.is(ResponseStatus.OK);
			if (!isOK) {
				throw new JRestException("Got no OK response: " + response);
			}

			return responseDataToCalendar(response);

		} catch (JRestException | ParseException e) {
			throw new Hg6CoreConnException("Cannot send currentStatedAt command", e);
		}
	}

	public void configChanged() throws Hg6CoreConnException {
		JRestRequest request = new JRestRequest(Protocol.CONFIG_CHANGED_COMMAND);
		try {
			JRestResponse response = guest.sendRequest(request);

			boolean isOK = response.is(ResponseStatus.OK);
			if (!isOK) {
				throw new JRestException("Got no OK response: " + response);
			}
		} catch (JRestException e) {
			throw new Hg6CoreConnException("Cannot config changed command", e);
		}
	}

	
	private Calendar responseDataToCalendar(JRestResponse response) throws ParseException {
		String data = response.getData();

		Calendar cal = Calendar.getInstance();

		Date parsed = Protocol.DATE_FORMAT.parse(data);
		cal.setTime(parsed);

		return cal;
	}


}
