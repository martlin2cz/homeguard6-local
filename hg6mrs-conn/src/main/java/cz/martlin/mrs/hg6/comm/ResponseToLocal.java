package cz.martlin.mrs.hg6.comm;

public class ResponseToLocal {
	private static final String SEPARATOR = ": ";
	private final CommandToLocal command;
	private final String argument;

	public ResponseToLocal(CommandToLocal command, String argument) {
		super();
		this.command = command;
		this.argument = argument;
	}

	public ResponseToLocal(CommandToLocal command) {
		super();
		this.command = command;
		this.argument = null;
	}

	public CommandToLocal getCommand() {
		return command;
	}

	public String getArgument() {
		return argument;
	}

	@Override
	public String toString() {
		return "ResponseToLocal [command=" + command + ", argument=" + argument + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((argument == null) ? 0 : argument.hashCode());
		result = prime * result + ((command == null) ? 0 : command.hashCode());
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
		ResponseToLocal other = (ResponseToLocal) obj;
		if (argument == null) {
			if (other.argument != null)
				return false;
		} else if (!argument.equals(other.argument))
			return false;
		if (command != other.command)
			return false;
		return true;
	}

	// TODO test
	public static String serialize(ResponseToLocal resp) {
		String result = resp.command.name();

		if (resp.argument != null) {
			result = result + SEPARATOR + resp.argument;
		}

		return result;
	}

	// TODO test!
	public ResponseToLocal deserialize(String str) {
		try {
			String[] parts = str.split(SEPARATOR);
			String cmdStr = parts[0];
			String argStr = str.substring(cmdStr.length() + SEPARATOR.length());

			CommandToLocal cmd = CommandToLocal.valueOf(cmdStr);
			return new ResponseToLocal(cmd, argStr);
		} catch (Exception e) {
			throw new IllegalArgumentException("Cannot deserialize ReponseToLocal from " + str, e);
		}
	}

}
