package cz.martlin.hg5.ws.services;

import java.util.Map;

import cz.martlin.hg5.web.Hg6WebApp;
import cz.martlin.hg5.ws.WebServiceProcessor;
import cz.martlin.hg6.coreJRest.Hg6CoreConnException;

/**
 * Processes simple commands (start, stop, status). Response is send back as
 * simple plain text sequence.
 * 
 * @author martin
 *
 */
public class CommandService implements WebServiceProcessor {

	private static final String MIME = "text/plain";

	private final Hg6WebApp homeguard = new Hg6WebApp();

	public CommandService() {
	}

	@Override
	public String getContentType() {
		return MIME;
	}

	@Override
	public byte[] process(Map<String, String[]> request) throws Exception {
		String message;
		if (request.get("start") != null) {
			message = processStart();
		} else if (request.get("stop") != null) {
			message = processStop();
		} else if (request.get("status") != null) {
			message = processStatus();
		} else {
			throw new IllegalArgumentException("Unknown command. Allowed are start, stop and status.");
		}

		return message.getBytes();
	}

	private String processStart() {
		try {
			homeguard.start();
			return "Homeguard started and running";
		} catch (Hg6CoreConnException e) {
			return "Start failed because: " + e;
		}

	}

	private String processStop() {
		try {
			homeguard.stop();
			return "Homeguard stopped and not running";
		} catch (Hg6CoreConnException e) {
			return "Start failed because: " + e;
		}

	}

	private String processStatus() {
		boolean is;
		try {
			is = homeguard.isRunning();
		} catch (Hg6CoreConnException e) {
			return "Check of running failed because " + e;
		}
		
		if (is) {
			return "Homeguard is started and running";
		} else {
			return "Homeguard is stopped and not running";
		}
	}

}
