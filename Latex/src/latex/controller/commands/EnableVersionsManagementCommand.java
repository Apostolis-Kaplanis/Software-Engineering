package latex.controller.commands;

import latex.model.Document;
import latex.model.VersionsManager;

public class EnableVersionsManagementCommand implements Command {
	public EnableVersionsManagementCommand() {

	}

	public Object execute(Object[] argumentsOfCommand){
		VersionsManager versionsManager = (VersionsManager) argumentsOfCommand[0];
		Document doc = (Document) argumentsOfCommand[1];

		versionsManager.setCurrentVersion(doc);

		return null;
	}
}
