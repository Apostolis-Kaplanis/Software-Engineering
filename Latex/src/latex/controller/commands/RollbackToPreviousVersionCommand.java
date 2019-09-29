package latex.controller.commands;

import latex.model.Document;
import latex.model.VersionsManager;

public class RollbackToPreviousVersionCommand implements Command {
	public RollbackToPreviousVersionCommand() {
	}

	public Object execute(Object[] argumentsOfCommand) {
		VersionsManager versionsManager = (VersionsManager) argumentsOfCommand[1];
		Document doc = (Document) argumentsOfCommand[2];

		versionsManager.rollbackToPreviousVersion();
		doc = versionsManager.getPreviousVersion();

		return doc;
	}
}
