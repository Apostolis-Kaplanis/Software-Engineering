package latex.controller;

import java.util.HashMap;

import latex.controller.commands.Command;
import latex.controller.commands.CommandsFactory;

public class LatexEditorController {
	private CommandsFactory commandsFactory = new CommandsFactory();
	private HashMap<String, Command> commands = new HashMap<String, Command>();

	private static String CREATE = "Create";
	private static String ADD = "Add";
	private static String ROLLBACK = "Rollback";
	private static String EDIT = "Edit";
	private static String LOAD = "Load";
	private static String SAVE = "Save";
	private static String ENABLE = "Enable";
	private static String DISABLE = "Disable";
	private static String CHANGE_STRATEGY = "Change strategy";

	public LatexEditorController() {
		commands.put(CREATE,
					commandsFactory.createCommand("CreateCommand")
					);
		commands.put(ADD,
					commandsFactory.createCommand("AddLatexCommand")
					);
		commands.put(ROLLBACK,
					commandsFactory.createCommand("RollbackToPreviousVersionCommand")
					);
		commands.put(EDIT,
					commandsFactory.createCommand("EditCommand")
					);
		commands.put(LOAD,
					commandsFactory.createCommand("LoadCommand")
					);
		commands.put(SAVE,
					commandsFactory.createCommand("SaveCommand")
					);
		commands.put(ENABLE,
					commandsFactory.createCommand("EnableVersionsManagementCommand")
					);
		commands.put(DISABLE,
					commandsFactory.createCommand("DisableVersionsManagementCommand")
					);
		commands.put(CHANGE_STRATEGY,
					commandsFactory.createCommand("ChangeVersionsStrategyCommand")
					);
	}

	public Object enact(String commandKey, Object[] argumentsOfCommand) {
		return commands.get(commandKey).execute(argumentsOfCommand);
	}
}
