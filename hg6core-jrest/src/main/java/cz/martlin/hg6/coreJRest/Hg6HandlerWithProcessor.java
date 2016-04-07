package cz.martlin.hg6.coreJRest;

import cz.martlin.jrest.protocol.reqresp.JRestRequest;
import cz.martlin.jrest.protocol.reqresp.JRestResponse;
import cz.martlin.jrest.waiter.JRestWaiter;
import cz.martlin.jrest.waiter.RequestHandler;

public class Hg6HandlerWithProcessor implements RequestHandler {

	private final Hg6CommandsProcessor processor;

	public Hg6HandlerWithProcessor(Hg6CommandsProcessor processor) {
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
			return JRestResponse.ok("started");
		}

		if (Protocol.STOP_COMMAND.equals(req.getCommand())) {
			processor.stop();
			return JRestResponse.ok("stopped");
		}
		
		if (Protocol.IS_RUNNING_COMMAND.equals(req.getCommand())) {
			String is = processor.getIsRunning();
			return JRestResponse.ok(is);
		}
		
		if (Protocol.SIMPLE_INFO_COMMAND.equals(req.getCommand())) {
			String info = processor.getSimpleInfo();
			return JRestResponse.ok(info);
		}
		return null;
	}

}
