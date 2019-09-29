package latex.controller.commands;

import latex.model.VersionsManager;

public class DisableVersionsManagementCommand implements Command {
	public DisableVersionsManagementCommand() {

	}

	public Object execute(Object[] argumentsOfCommand){
		VersionsManager versionsManager = (VersionsManager) argumentsOfCommand[0];

		versionsManager.disable();

		return null;
	}
}
