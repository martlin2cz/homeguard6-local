package cz.martlin.hg6.coreJRest;

import java.util.Calendar;

import cz.martlin.jrest.protocol.reqresp.JRestRequest;
import cz.martlin.jrest.protocol.reqresp.JRestResponse;
import cz.martlin.jrest.protocol.reqresp.ResponseStatus;
import cz.martlin.jrest.waiter.JRestWaiter;
import cz.martlin.jrest.waiter.RequestHandler;

public class Hg6CoreHandlerWithProcessor implements RequestHandler {

	private final Hg6CommandsProcessor processor;

	public Hg6CoreHandlerWithProcessor(Hg6CommandsProcessor processor) {
		this.processor = processor;
	}

	@Override
	public void finish(JRestWaiter arg0) throws Exception {
	}

	@Override
	public void initialize(JRestWaiter arg0) throws Exception {
	}

	@Override
	public JRestResponse handle(JRestRequest req) throws Exception {
		if (Protocol.START_COMMAND.equals(req.getCommand())) {
			processor.start();
			return JRestResponse.ok(Protocol.STARTED_TEXT);
		}

		if (Protocol.STOP_COMMAND.equals(req.getCommand())) {
			processor.stop();
			return JRestResponse.ok(Protocol.STOPPED_TEXT);
		}

		if (Protocol.IS_RUNNING_COMMAND.equals(req.getCommand())) {
			boolean is = processor.getIsRunning();
			String string = Boolean.toString(is);
			return JRestResponse.ok(string);
		}

		if (Protocol.SIMPLE_INFO_COMMAND.equals(req.getCommand())) {
			String info = processor.getSimpleInfo();
			return JRestResponse.ok(info);
		}

		if (Protocol.CURRENT_STARTED_COMMAND.equals(req.getCommand())) {
			Calendar startedAt = processor.getCurrentReportStartedAt();
			if (startedAt != null) {
				String string = Protocol.DATE_FORMAT.format(startedAt.getTime());
				return JRestResponse.ok(string);
			} else {
				return JRestResponse.warn(" ", Protocol.STOPPED_TEXT);
			}
		}

		if (Protocol.CONFIG_CHANGED_COMMAND.equals(req.getCommand())) {
			boolean success = processor.configChanged();
			if (success) {
				return new JRestResponse(ResponseStatus.OK, " ", Protocol.UPDATED_TEXT);
			} else {
				return JRestResponse.error(" ", Protocol.FAILED_TEXT);
			}
		}

		return null;
	}

}
