package cz.martlin.mrs.hg6.comm;

import java.util.Calendar;

public class RequestToRemote {

	private final CommandToRemote command;
	private final SituationOnLocal situation;
	private final String message;

	public RequestToRemote(CommandToRemote command, SituationOnLocal situation, String message) {
		super();
		this.command = command;
		this.situation = situation;
		this.message = message;
	}

	public CommandToRemote getCommand() {
		return command;
	}

	public SituationOnLocal getSituation() {
		return situation;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((command == null) ? 0 : command.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((situation == null) ? 0 : situation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestToRemote other = (RequestToRemote) obj;
		if (command != other.command)
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (situation == null) {
			if (other.situation != null)
				return false;
		} else if (!situation.equals(other.situation))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RequestToRemote [command=" + command + ", situation=" + situation + ", message=" + message + "]";
	}

	public static RequestToRemote create(CommandToRemote command, Calendar startedAt, Calendar stoppedAt,
			Calendar lastWarnAt, Integer criticalCount, Integer warningsCount, Integer itemsCount, String description,
			Integer interval, String message) {

		SituationOnLocal situation = SituationOnLocal.create(startedAt, stoppedAt, lastWarnAt, criticalCount,
				warningsCount, itemsCount, description, interval);

		return new RequestToRemote(command, situation, message);
	}

}
