package telnet.commandlist;


public enum CommandPriority {
	CRITICAL(0), HIGHT(1), MEDIUM(2), LOW(3);

	private int priority;

	private CommandPriority(int priority) {
		this.priority = priority;
	}

	public int getPriorityValue() {
		return this.priority;
	}
}

